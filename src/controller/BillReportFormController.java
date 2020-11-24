package controller;

import com.jfoenix.controls.JFXTextField;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Item;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class BillReportFormController implements Initializable {
    public JFXTextField txtDate;
    public TextField txtTotal;
    public JFXTextField txtOrderID;
    public TextField txtCustomerID;

    @FXML
    private TableView<Item> tblBillDetail;

    @FXML
    private TableColumn<Item, String> clmItemCode;

    @FXML
    private TableColumn<Item, String> clmDescription;

    @FXML
    private TableColumn<Item, String> clmUnitPrice;

    @FXML
    private TableColumn<Item, String> clmQTY;

    @FXML
    private TableColumn<Item, String> clmDiscount;

    @FXML
    private TableColumn<Item, String> clmTotal;

    public void generateOrderDateTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        String orderDate = sdf.format(date);
        txtDate.setText(orderDate);
    }

    public void setCustomerID(){
        if (ManageOrderFormController.customerID.length()==0) {
            txtCustomerID.setText(" ");
            return;
        }
        txtCustomerID.setText(ManageOrderFormController.customerID);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generateOrderDateTime();
        txtOrderID.setText(ManageOrderFormController.orderID);
        setCustomerID();
        getBillDetails();
        setFinalTot();
        tblBillDetail.requestFocus();
    }

    public void getBillDetails(){
        try {
            ObservableList<Item> rowsForCart = OrderDetailController.getOrderDetails(ManageOrderFormController.orderID);
            setTblCartColumns();
            tblBillDetail.getItems().clear();
            tblBillDetail.setItems(rowsForCart);
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setTblCartColumns(){
        clmItemCode.setCellValueFactory(new PropertyValueFactory<Item,String>("itemcode"));
        clmDescription.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
        clmUnitPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("unitprice"));
        clmQTY.setCellValueFactory(new PropertyValueFactory<Item,String>("qty"));
        clmDiscount.setCellValueFactory(new PropertyValueFactory<Item,String>("discount"));
        clmTotal.setCellValueFactory(new PropertyValueFactory<Item,String>("total"));
    }

    public void setFinalTot(){
        double finalTot =0;
        double discount=0;
        for (int i=0;i<tblBillDetail.getItems().size();i++){
            finalTot+=tblBillDetail.getItems().get(i).getTotal();
            discount+=tblBillDetail.getItems().get(i).getDiscount();
        }
        txtTotal.setText(String.format("%.2f",(finalTot-discount)));
    }
}
