package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class AddItemsFormController implements Initializable {
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQTYonHand;
    public JFXTextField txtTotalItems;
    public JFXButton btnAddItems;
    public TextField txtDate;
    public TextField txtTime;
    public String currentTime;

    public void btnAddItemsOnAction(ActionEvent actionEvent) {
        try {
            boolean isAdded = ItemController.addItems(txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),txtUnitPrice.getText(),txtQTYonHand.getText(),txtDate.getText(),currentTime);
            if (isAdded){
                JOptionPane.showMessageDialog(null,"Item was successfully added to the store","",JOptionPane.INFORMATION_MESSAGE);
                textFieldsReset();
                getItemCount();
                generateDateTime();
                int index = ItemController.getItemsCount();
                getItemCount();
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Item details are empty!","",JOptionPane.WARNING_MESSAGE);
        }
    }

    public void textFieldsReset(){
        txtDescription.setText(null);
        txtPackSize.setText(null);
        txtUnitPrice.setText(null);
        txtQTYonHand.setText(null);
    }

    public void generateDateTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        String orderDate = sdf.format(date);
        txtDate.setText(orderDate);

        Timeline timeline =new Timeline(new KeyFrame(Duration.ZERO, e-> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            txtTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm: a");
        currentTime= dtf.format(LocalTime.now());
    }

    public void getItemCount(){
        try {
            int index = ItemController.getItemsCount();
            txtTotalItems.setText(Integer.toString(index));
            if (index<10){
                txtItemCode.setText("P00"+(index+1));
            }
            if (index>=10 && index<100){
                txtItemCode.setText("P0"+(index+1));
            }
            if (index>=100){
                txtItemCode.setText("P"+(index+1));
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateDateTime();
        getItemCount();
    }
}
