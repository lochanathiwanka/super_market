package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.StageList;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class UserLoginFormController extends StageList implements Initializable {
    public JFXTextField txtUserName;
    public JFXPasswordField pwdPasswordField;
    public JFXButton btnLogin;


    public void btnLoginOnAction(ActionEvent actionEvent) throws Exception {
        try {
            if (txtUserName.getText().length() != 0 && pwdPasswordField.getText().length() != 0) {
                if (txtUserName.getText().equalsIgnoreCase("u") && pwdPasswordField.getText().equalsIgnoreCase("u")) {
                    userStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/UserForm.fxml"))));
                    userStage.setResizable(false);
                    userStage.show();
                    mainStage.close();
                    txtUserName.setText(null);
                    pwdPasswordField.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "User Name or Password is wrong!", "", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "User Name & Password cannot be Empty!", "", JOptionPane.ERROR_MESSAGE);
            }
        }catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null, "User Name & Password cannot be Empty!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtUserName.setText(null);
        pwdPasswordField.setText(null);
    }
}
