package controller;

import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;

public class DashboardFormController {
    public AnchorPane mainContext;
    public AnchorPane bigContext;
    public Label lblTime;
    public Label lblDate;
    public Label lblUserName;
    public Circle c2;
    public Circle c1;

    public void initialize() throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DashboardMainForm.fxml"));
        mainContext.getChildren().add(parent);
        loadDateAndTime();
        String userName=LoginFormController.userName;
        lblUserName.setText(String.valueOf(userName));
        setRotate(c1,true,360,10);
        setRotate(c2,false,360,10);
    }


    private void loadDateAndTime() {
        Calendar clndr = Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("aa");



        Timeline clock = new Timeline(new KeyFrame(Duration.INDEFINITE.ZERO, e -> {
            LocalTime currenttime = LocalTime.now();
            LocalDate currentdate = LocalDate.now();
            lblTime.setText(currenttime.getHour() + ":" + currenttime.getMinute() + ":" + currenttime.getSecond()+"  "+format1.format(clndr.getTime()));
            lblDate.setText(currentdate.getDayOfWeek()+","+ currentdate.getMonth() +" " + currentdate.getDayOfMonth() + "," + currentdate.getYear());

        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    public void dashBoardOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DashboardMainForm.fxml"));
        mainContext.getChildren().add(parent);

    }

    public void customerOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/CustomerForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void employeeOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/EmployeeForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void supplierOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/SupplierForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void orderOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/OrderForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void diliverOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/DeliverForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void foodItemOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/FoodItemForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void ingridientsOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/IngredientForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void paymentOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/PaymentForm.fxml"));
        mainContext.getChildren().add(parent);
    }

    public void logOutOnAction(ActionEvent actionEvent) throws IOException {
        setUi("LoginForm");

        }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) bigContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }

    public void reportOnAction(ActionEvent actionEvent) throws IOException {

        try {
            JasperReport compileReport =(JasperReport) JRLoader.loadObject(this.getClass().getResource("/view/report/qtyReport_A4.jasper"));

            Connection connection = DBConnection.getInstance().getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(compileReport, null, connection);
            JasperViewer.viewReport(jasperPrint,false);
        } catch (JRException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void userOnAction(ActionEvent actionEvent) throws IOException {
        mainContext.getChildren().clear();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/UserForm.fxml"));
        mainContext.getChildren().add(parent);
    }
    public void setRotate(Circle c, boolean reverse, int angle, int duration){
        RotateTransition rt = new RotateTransition(Duration.seconds(duration),c);

        rt.setAutoReverse(reverse);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(100);
        rt.play();
    }
}


