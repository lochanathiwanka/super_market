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
import model.Item;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DeleteItemsFormController implements Initializable {
    public JFXButton btnRemove;
    public JFXTextField txtTotalItems;
    public int count=0;

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
    private TableColumn<Item, String> clmQTYonHand;

    @FXML
    private TableColumn<Item, String> clmDate;

    @FXML
    private TableColumn<Item, String> clmTime;

    ObservableList<Item>rows;

    public void btnRemoveOnAction(ActionEvent actionEvent) {
        try{
            boolean isRemoved = ItemController.removeItem(tblItems.getSelectionModel().getSelectedItem().getItemcode());
            if (isRemoved){
                JOptionPane.showMessageDialog(null,"Item was successfully removed from the stock","",JOptionPane.INFORMATION_MESSAGE);
                insertValuestoTable();
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }catch (NullPointerException ex){}
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
        clmQTYonHand.setCellValueFactory(new PropertyValueFactory<Item,String>("qty"));
        clmDate.setCellValueFactory(new PropertyValueFactory<Item,String>("date"));
        clmTime.setCellValueFactory(new PropertyValueFactory<Item,String>("time"));
        tblItems.getItems().clear();
        tblItems.setItems(rows);
    }

    public void findRowCount(){
        try {
            int index = ItemController.getItemsCount();
            txtTotalItems.setText(Integer.toString(index));

            count = ItemController.getRemovedItemsCount();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        findRowCount();
        insertValuestoTable();
    }
}
