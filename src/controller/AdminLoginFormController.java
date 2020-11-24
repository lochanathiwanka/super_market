package controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import model.StageList;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminLoginFormController extends StageList implements Initializable {

    public JFXTextField txtUserName;
    public JFXPasswordField pwdPasswordField;


    public void btnLoginOnAction(ActionEvent actionEvent) throws Exception {
        String userName = txtUserName.getText();
        String password = pwdPasswordField.getText();
        try {
            if (!userName.equalsIgnoreCase(null) && !password.equalsIgnoreCase(null)) {
                if (txtUserName.getText().equalsIgnoreCase("a") && pwdPasswordField.getText().equalsIgnoreCase("a")) {
                    adminStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/AdminForm.fxml"))));
                    adminStage.setResizable(false);
                    adminStage.show();
                    mainStage.close();
                    txtUserName.setText(null);
                    pwdPasswordField.setText(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Admin Name or Password is wrong!", "", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Admin Name & Password cannot be Empty!", "", JOptionPane.ERROR_MESSAGE);
            }
        }catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null, "Admin Name & Password cannot be Empty!", "", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtUserName.setText(null);
        pwdPasswordField.setText(null);
    }
}
