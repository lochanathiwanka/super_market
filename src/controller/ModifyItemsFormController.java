package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Item;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyItemsFormController implements Initializable {
    public JFXTextField txtItemCode;
    public JFXTextField txtDescription;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public JFXTextField txtQTYonHand;
    public JFXButton btnModifyItems;

    @FXML
    private TableView<Item> tblItems;

    @FXML
    private TableColumn<Item, String> clmItemCode;

    @FXML
    private TableColumn<Item, String> clmDescription;

    @FXML
    private TableColumn<Item, String> clmPackSize;

    @FXML
    private TableColumn<Item, String> clmUnitPrice;

    @FXML
    private TableColumn<Item, String> clmQtyonHand;

    ObservableList<Item> rows;

    public void btnModifyItemsOnAction(ActionEvent actionEvent) {
        try {
            boolean isModified = ItemController.modifyItemDetails(txtItemCode.getText(),txtDescription.getText(),txtPackSize.getText(),txtUnitPrice.getText(),txtQTYonHand.getText());
            if (isModified){
                JOptionPane.showMessageDialog(null,"Item was Modified","",JOptionPane.INFORMATION_MESSAGE);
                insertValuestoTable();
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void insertValuestoTable(){
        try {
            rows = ItemController.getItems();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        clmItemCode.setCellValueFactory(new PropertyValueFactory<Item,String>("itemcode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
        clmPackSize.setCellValueFactory(new PropertyValueFactory<Item,String>("packsize"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("unitprice"));
        clmQtyonHand.setCellValueFactory(new PropertyValueFactory<Item,String>("qty"));
        tblItems.getItems().clear();
        tblItems.setItems(rows);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        insertValuestoTable();
    }

    public void rowClickedOnActon(MouseEvent mouseEvent) {
        try {
            txtItemCode.setText(tblItems.getSelectionModel().getSelectedItem().getItemcode());
            txtDescription.setText(tblItems.getSelectionModel().getSelectedItem().getDescription());
            txtPackSize.setText(tblItems.getSelectionModel().getSelectedItem().getPacksize());
            txtUnitPrice.setText(Double.toString(tblItems.getSelectionModel().getSelectedItem().getUnitprice()));
            txtQTYonHand.setText(Integer.toString(tblItems.getSelectionModel().getSelectedItem().getQty()));
        }catch (RuntimeException ex){ }
    }
}
