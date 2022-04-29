package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Slider sld1;

    @FXML
    private Slider zoom1;

    @FXML
    private Slider sld2;

    @FXML
    private Canvas mainCanvas;

    @FXML
    private TextField widthTxt;

    @FXML
    private TextField heightTxt;

    @FXML
    private Button button;
    @FXML
    private Text resultField;
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        GraphicsContext ctx = mainCanvas.getGraphicsContext2D();
        ctx.setFill(Color.WHITE);
        ctx.fillRect(0,0,mainCanvas.getWidth(), mainCanvas.getHeight());
        sld1.valueProperty().addListener((observable, oldValue, newValue) -> {
            ctx.fillRect(0,0,mainCanvas.getWidth(), mainCanvas.getHeight());
            draw(Double.parseDouble(widthTxt.getText()),
            Double.parseDouble(heightTxt.getText()),
            sld1.getValue(),
            sld2.getValue(),
            ctx);
        });
        sld2.valueProperty().addListener((observable, oldValue, newValue) -> {
            ctx.fillRect(0,0,mainCanvas.getWidth(), mainCanvas.getHeight());
            draw(Double.parseDouble(widthTxt.getText()),
                    Double.parseDouble(heightTxt.getText()),
                    sld1.getValue(),
                    sld2.getValue(),
                    ctx);
        });

        button.setOnAction(event -> {
            if(isDouble(widthTxt.getText()) && isDouble(heightTxt.getText())&& inRangeHeight(heightTxt.getText())&& inRangeWidth(widthTxt.getText()))
            {
                ctx.fillRect(0,0,mainCanvas.getWidth(), mainCanvas.getHeight());
                area(Double.parseDouble(widthTxt.getText()),
                        Double.parseDouble(heightTxt.getText()),
                        sld1.getValue(),
                        sld2.getValue());
                draw(Double.parseDouble(widthTxt.getText()),
                        Double.parseDouble(heightTxt.getText()),
                        sld1.getValue(),
                        sld2.getValue(),
                        ctx);
                zoom1.setMax(400);
                zoom1.setMin(50);
                zoom1.setValue(zoom1.getMax());
            }
            else  if (!isDouble(widthTxt.getText())||!isDouble(heightTxt.getText())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Ошибка ввода");
                alert.setHeaderText("Вы ввели строку вместо числа или ничего не ввели!");
                alert.showAndWait();
                widthTxt.clear();
                heightTxt.clear();
            }
        });
        zoom1.valueProperty().addListener((observable, oldValue, newValue) ->{
            ctx.fillRect(0,0,mainCanvas.getWidth(), mainCanvas.getHeight());
            mainCanvas.setHeight(zoom1.getValue());
            mainCanvas.setWidth(zoom1.getValue());
            ctx.fillRect(0,0,mainCanvas.getWidth(), mainCanvas.getHeight());
            double k = 360 / zoom1.getValue();
            draw(Double.parseDouble(widthTxt.getText())/k,
                    Double.parseDouble(heightTxt.getText())/k,
                    sld1.getValue()/k,
                    sld2.getValue()/k,
                    ctx);
            area(Double.parseDouble(widthTxt.getText())/k,
                    Double.parseDouble(heightTxt.getText())/k,
                    sld1.getValue()/k,
                    sld2.getValue()/k);
        });
    }

    private void draw(Double h,Double w,Double rB,Double rS, GraphicsContext ctx ) {
        double zoom = 50;
        ctx.save(); //Сохранили состояние матрицы
        //Матрица масштабирования
        Affine transform = ctx.getTransform();
        //Выравниваем центр координат по центру экрана
        transform.appendTranslation(mainCanvas.getWidth() / 2, mainCanvas.getHeight() / 2);
        transform.appendScale(zoom, zoom);
        double k = 360 / zoom1.getValue();

        //Применяем масштабирование
        ctx.setTransform(transform);

        ctx.setLineWidth(1. / zoom);
        ctx.strokePolygon(
                new double[]{w / 2, w / 2, -w / 2, -w / 2},
                new double[]{h / 2, -h / 2, -h / 2, h / 2},
                4
        );
        ctx.strokeArc(
                w / 2 - rS,
                -h / 2 - rS,
                2 * rS,
                2 * rS,
                -90,
                -90,
                ArcType.ROUND
        );
        ctx.setFill(Color.web("#590d73"));
        ctx.fillArc(
                w / 2 - rS,
                -h / 2 - rS,
                2 * rS,
                2 * rS,
                -90,
                -90,
                ArcType.ROUND
        );
        ctx.strokeArc(
                -w / 2 - rS,
                h / 2 - rS,
                2 * rS,
                2 * rS,
                90,
                -90,
                ArcType.ROUND
        );
        ctx.setFill(Color.web("#590d73"));
        ctx.fillArc(
                -w / 2 - rS,
                h / 2 - rS,
                2 * rS,
                2 * rS,
                90,
                -90,
                ArcType.ROUND
        );
        ctx.strokeArc(
                -w / 2 - rS,
                -h / 2 - rS,
                2 * rS,
                2 * rS,
                -90,
                90,
                ArcType.ROUND
        );
        ctx.setFill(Color.web("#590d73"));
        ctx.fillArc(
                -w / 2 - rS,
                -h / 2 - rS,
                2 * rS,
                2 * rS,
                -90,
                90,
                ArcType.ROUND
        );
        ctx.strokeArc(
                w / 2 - rS,
                h / 2 - rS,
                2 * rS,
                2 * rS,
                90,
                90,
                ArcType.ROUND
        );
        ctx.setFill(Color.web("#590d73"));
        ctx.fillArc(
                w / 2 - rS,
                h / 2 - rS,
                2 * rS,
                2 * rS,
                90,
                90,
                ArcType.ROUND
        );
         double[] coord  = new double[2];
         cross(coord,w/2,h/2,-w/2,-h/2,w/2,-h/2,-w/2,h/2);
        ctx.strokeOval(
                (coord[0])-rB,
                (coord[1] )-rB,
                2 * rB,
                2 * rB
        );

        ctx.setFill(Color.web("#e0a3f5"));
        ctx.fillOval((coord[0])-rB,
                (coord[1] )-rB,
                2 * rB,
                2 * rB);
        ctx.restore();
        if(rB>h/2)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Вы вышли за пределы диапазона!");
            alert.showAndWait();
            sld1.setValue(h/2-1);
        }
        else if(rB>w/2)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ошибка ввода");
            alert.setHeaderText("Вы вышли за пределы диапазона!");
            alert.showAndWait();
            sld1.setValue(w/2-1);
        }



    }
    boolean isDouble (String str){
        boolean result;
        try {
            Double.parseDouble(str);
            result = true;
        } catch (Exception e){
            result = false;
        }
        return result;
    }
    boolean inRangeWidth(String str){
        boolean result;
        if (Double.parseDouble(str) > 9.1 ||  Double.parseDouble(str) < 1){
            result = false;
        } else{
            result = true;
        }
        return result;
    }
    boolean inRangeHeight(String str){
        boolean result;
        if (Double.parseDouble(str) > 8.49 ||  Double.parseDouble(str) < 1){
            result = false;
        } else{
            result = true;
        }
        return result;
    }
    boolean cross(double intersectionPoints[], double x1, double y1, double x2, double y2, double x3, double y3, double x4, double y4) {
        double n;
        if (y2 - y1 != 0) {  // a(y)
            double q = (x2 - x1) / (y1 - y2);
            double sn = (x3 - x4) + (y3 - y4) * q;
            if (sn == 0) {
                return false;
            }  // c(x) + c(y)q
            double fn = (x3 - x1) + (y3 - y1) *q;   // b(x) + b(y)q
            n = fn / sn;
        } else {
            if ((y3 - y4) == 0) {
                return false;
            }  // b(y)
            n = (y3 - y1) / (y3 - y4);   // c(y)/b(y)
        }
        intersectionPoints[0] = x3 + (x4 - x3) *n;  // x3 + (-b(x))n
        intersectionPoints[1] = y3 + (y4 - y3) *n;  // y3 +(-b(y))*n
        return true;
    }
    void area(double rB, double rS, Double h, Double w)
    {
// Находим площадь первой окружности
        double s1 = Math.PI * (rS * rS);
        // Находим площадь второй окружности
        double s2 = Math.PI * (rB * rB);
        double sr = h*w-s1-s2;
        resultField.setText(String.format("%.3f",sr));


    }
}
