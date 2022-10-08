package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.CrudUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginFormController  {
    public AnchorPane loginContext;
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPassword;
    public JFXButton btnLogin;
    static String userId;
    static String userName;
    public Circle c1;
    public Circle c2;

    public void initialize(){
      setRotate(c1,true,360,10);
        setRotate(c2,false,360,10);
  }

    public void loginOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {

       // btnLogin.setDisable(true);
        boolean yes=false;
        ResultSet result = CrudUtil.execute("SELECT * FROM Admin ");

        while (result.next()) {

            if (txtUserName.getText().equals(result.getString(2)) && pwdPassword.getText().equals(result.getString(3))) {
                  userId=result.getString(1);
                  userName=result.getString(2);
                btnLogin.setDisable(false);
                yes=true;
                new Alert(Alert.AlertType.CONFIRMATION, "Your Logging is Successful!..").show();
                setUi("DashBoardForm");


                // return;
            }



        }
        if (!yes){
            new Alert(Alert.AlertType.WARNING, "Your UserName Or Password Is Not Matched..Try Again!..").show();
            txtUserName.clear();
            pwdPassword.clear();
        }

    }



//    public void signUpOnMouseClicked(MouseEvent mouseEvent) throws IOException {
//        setUi("SignUpForm");
//    }
    private void setUi(String location) throws IOException {
        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../view/"+location+".fxml"))));
        stage.centerOnScreen();
    }
    public void setRotate(Circle c,boolean reverse,int angle,int duration){
        RotateTransition rt = new RotateTransition(Duration.seconds(duration),c);

        rt.setAutoReverse(reverse);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }
}
