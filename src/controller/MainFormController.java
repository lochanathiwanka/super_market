package controller;

import com.jfoenix.controls.JFXProgressBar;
import com.sun.javafx.applet.Splash;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    public AnchorPane childPane;

    public void setAdminLoginPane() throws Exception{
        childPane.getChildren().clear();
        AnchorPane adminLoginPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/AdminLoginForm.fxml"));
        childPane.getChildren().setAll(adminLoginPane.getChildren());
    }

    public void btnAdministratorOnAction(ActionEvent actionEvent) throws Exception {
       setAdminLoginPane();
    }

    public void btnUserOnAction(ActionEvent actionEvent) throws Exception {
        childPane.getChildren().clear();
        AnchorPane adminLoginPane = (AnchorPane) FXMLLoader.load(this.getClass().getResource("../view/UserLoginForm.fxml"));
        childPane.getChildren().setAll(adminLoginPane.getChildren());
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setAdminLoginPane();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
