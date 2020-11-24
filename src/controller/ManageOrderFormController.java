package controller;

import com.jfoenix.controls.JFXAutoCompletePopup;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import model.*;
import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Logger;
public class ManageOrderFormController extends StageList implements Initializable {
    public JFXButton btnConfirmOrder;
    public JFXButton btnRemoveItem;
    public JFXButton btnConfirmOrderEdit;
    public JFXTextField txtQTYonHand2;
    public JFXTextField txtDescription;
    public JFXTextField txtItemCode;
    public JFXTextField txtPackSize;
    public JFXTextField txtUnitPrice;
    public TextField txtOrderID;
    public TextField txtTotal;
    public JFXComboBox cmbOrderID;
    public JFXTextField txtName;
    public int index=0;
    public TextField txtDate;
    public TextField txtTime;
    public JFXTextField txtContact;
    public JFXTextField txtcID;
    public String currentTime;
    public JFXTextField txtDiscount;
    public TextField txtSearchItem;
    public JFXTextField lblItems;
    public static String orderID;
    public static String customerID;

    private Logger logger = Logger.getLogger("ConfirmController");

    @FXML
    private JFXTextField txtQTYonHand;

    @FXML
    private JFXButton btnAddtoCart;

    @FXML
    public void save(ActionEvent evt) {
        logger.info("confirmed " + txtQTYonHand.getText());
        hide(evt);
    }

    @FXML
    public void cancel(ActionEvent evt) {
        hide(evt);
    }

    private void hide(ActionEvent evt) {
        ((Node)evt.getSource()).getScene().getWindow().hide();
    }


    //Item Tabel
    @FXML
    private TableView<Item> tblItems;

    @FXML
    private TableColumn<Item, String> tblItemClmItemCode;

    @FXML
    private TableColumn<Item, String> tblItemClmDescription;

    @FXML
    private TableColumn<Item, String> tblItemClmPackSize;

    @FXML
    private TableColumn<Item, String> tblItemClmUnitPrice;

    @FXML
    private TableColumn<Item, String> tblItemClmQTY;

    ObservableList<Item> rows;


    //Cart Tabel
    @FXML
    private TableView<Item> tblCart;

    @FXML
    private TableColumn<Item, String> tblCartClmItemCode;

    @FXML
    private TableColumn<Item, String> tblCartClmDescription;

    @FXML
    private TableColumn<Item, String> tblCartClmUnitPrice;

    @FXML
    private TableColumn<Item, String> tblCartClmQTY;

    @FXML
    private TableColumn<Item, String> tblCartClmDiscount;

    @FXML
    private TableColumn<Item, String> tblCartClmTotal;

    public void generateOrderDateTime(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        String orderDate = sdf.format(date);
        txtDate.setText(orderDate);

        Timeline timeline =new Timeline(new KeyFrame(Duration.ZERO,e-> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
            txtTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm: a");
        currentTime=dtf.format(LocalTime.now());
    }

    public int getItemQTY(String itemCode){
        try {
            int qty = ItemController.getItemQTY(itemCode);
            if (qty!=-1){
                return qty;
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return -1;
    }

    public void setFinalTot(){
        double finalTot =0;
        double discount=0;
        for (int i=0;i<tblCart.getItems().size();i++){
            finalTot+=tblCart.getItems().get(i).getTotal();
            discount+=tblCart.getItems().get(i).getDiscount();
        }
        txtTotal.setText(String.format("%.2f",(finalTot-discount)));
    }

    public int isExist(String itemCode){
        for (int i=0;i<tblCart.getItems().size();i++){
            String code = tblCart.getItems().get(i).getItemcode();

            if (code.equalsIgnoreCase(txtItemCode.getText())){
                return i;
            }
        }
        return -1;
    }

    public void textFieldReset(){
        txtItemCode.setText(null);
        txtDescription.setText(null);
        txtPackSize.setText(null);
        txtUnitPrice.setText(null);
        txtQTYonHand.setText(null);
    }

    public void addToCart(){
        int row = isExist(txtItemCode.getText());
        int qty = 0;
        double unitPrice = 0;
        double totalClm = 0;
        cmbOrderID.getItems().clear();
        setTblCartColumns();

        try {
            if (row == -1) {
                qty = Integer.parseInt(txtQTYonHand.getText());
                unitPrice = Double.parseDouble(txtUnitPrice.getText());
                totalClm = qty * unitPrice;

                if (txtDiscount.getText().length() == 0) {
                    txtDiscount.setText("00.00");

                }
                tblCart.getItems().add(new Item(txtItemCode.getText(), txtDescription.getText(),
                        Double.parseDouble(txtUnitPrice.getText()), Integer.parseInt(txtQTYonHand.getText()),
                        Double.parseDouble(txtDiscount.getText()), totalClm));

            } else {
                qty = tblCart.getItems().get(row).getQty();
                qty += Integer.parseInt(txtQTYonHand.getText());
                tblCart.getItems().get(row).setQty(qty);
                unitPrice = tblCart.getItems().get(row).getUnitprice();
                totalClm = qty * unitPrice;
                tblCart.getItems().get(row).setTotal(totalClm);

                txtQTYonHand.setDisable(true);
                txtDiscount.setDisable(true);
                txtDiscount.setText(null);
            }
            btnConfirmOrder.setDisable(false);
            txtDiscount.setText(null);
            textFieldReset();
            setFinalTot();
            lblItems.requestFocus();
        }catch (NumberFormatException ex){
            tblItemClicked();
            txtDiscount.setText("00.00");
            txtQTYonHand.requestFocus();
        }
    }

    public void btnAddtoCartOnAction(ActionEvent actionEvent) {
        addToCart();
    }

    public ObservableList<Integer> getNewQTYfromTblCart(){
        ObservableList<Integer>newItemQTYlist = FXCollections.observableArrayList();
        int newQTY=0;
        for (int i=0;i<tblCart.getItems().size();i++){
            int oldQTY = getItemQTY(tblCart.getItems().get(i).getItemcode());
            int qtyOnTblCart = tblCart.getItems().get(i).getQty();
            newQTY = oldQTY-qtyOnTblCart;
            newItemQTYlist.add(newQTY);
        }
        return newItemQTYlist;
    }

    public ObservableList<OrderDetail> getValuesFromTblCart(){
        String orderId = txtOrderID.getText();
        ObservableList<OrderDetail>orderDetailList = FXCollections.observableArrayList();
        for (int i=0;i<tblCart.getItems().size();i++){
            String itemCode = tblCart.getItems().get(i).getItemcode();
            int qty = tblCart.getItems().get(i).getQty();
            double discount = tblCart.getItems().get(i).getDiscount();
            double total = tblCart.getItems().get(i).getTotal();
            orderDetailList.add(new OrderDetail(orderId,itemCode,qty,discount,total));
        }
        return orderDetailList;
    }

    public ObservableList<String> getAllItemsCodeOnOneOrder(){
        ObservableList<String>itemCodeList = FXCollections.observableArrayList();
        for (int i=0;i<tblCart.getItems().size();i++){
            itemCodeList.add(tblCart.getItems().get(i).getItemcode());
        }
        return itemCodeList;
    }

    public void btnConfirmOrderOnAction(ActionEvent actionEvent) throws Exception {
        generate();
        Orders orders = new Orders(txtOrderID.getText(),txtcID.getText(),txtDate.getText(),currentTime,getValuesFromTblCart());
        try {
            boolean isPlacedOrder = OrderController.placeOrder(orders,getNewQTYfromTblCart(),getAllItemsCodeOnOneOrder());
            if (isPlacedOrder){
                JOptionPane.showMessageDialog(null,"Order Placed","",JOptionPane.INFORMATION_MESSAGE);
                generate();
                txtQTYonHand.setDisable(true);
                txtDiscount.setDisable(true);
                customerID=txtcID.getText()+"";

                billDetailStage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("../view/BillReportForm.fxml"))));
                billDetailStage.setResizable(false);
                billDetailStage.show();
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }

        tblCart.getItems().clear();
        txtQTYonHand2.setText(null);
        txtTotal.setText(null);
        txtName.setText(null);
        btnConfirmOrder.setDisable(true);
        addOrderIDtoComboBox();
        insertValuestoTblItem();
    }

    public void btnCancelOrderOnAction(ActionEvent actionEvent) {
        tblCart.getItems().clear();
        txtQTYonHand2.setDisable(true);
        btnConfirmOrderEdit.setDisable(true);
        btnRemoveItem.setDisable(true);
        btnConfirmOrder.setDisable(true);
        textFieldReset();
        txtTotal.setText(null);
        txtQTYonHand2.setText(null);
    }

    public void btnRemoveItemOnAction(ActionEvent actionEvent) {
        tblCart.setEditable(true);
        int selectedRow = tblCart.getSelectionModel().getSelectedIndex();
        if (selectedRow>=0){
            tblCart.getItems().remove(selectedRow);
            txtQTYonHand2.setDisable(true);
            btnConfirmOrderEdit.setDisable(true);
            btnRemoveItem.setDisable(true);
        }
        setFinalTot();
        txtQTYonHand2.setText(null);

        if (tblCart.getItems().size()==0){
            btnConfirmOrder.setDisable(true);
        }
        insertValuestoTblItem();
        lblItems.requestFocus();
    }

    public void confirmOrderEdit(){
        try {
            int qty = Integer.parseInt(txtQTYonHand2.getText());
            int selectedRow = tblCart.getSelectionModel().getSelectedIndex();
            if (selectedRow >= 0) {
                tblCart.getItems().get(selectedRow).setQty(Integer.parseInt(txtQTYonHand2.getText()));
                double unitPrice = tblCart.getSelectionModel().getSelectedItem().getUnitprice();
                double totalClm = qty * unitPrice;
                tblCart.getItems().get(selectedRow).setTotal(totalClm);
            }
        }catch (RuntimeException ex){ }
        setFinalTot();
    }

    public void btnConfirmOrderEditOnAction(ActionEvent actionEvent) {
        confirmOrderEdit();
    }

    public void insertValuestoTblItem(){
        try {
            rows = ItemController.getItems();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        setTblItemsColumns();
        tblItems.getItems().clear();
        tblItems.setItems(rows);
    }

    public void setTblItemsColumns(){
        tblItemClmItemCode.setCellValueFactory(new PropertyValueFactory<Item,String>("itemcode"));
        tblItemClmDescription.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
        tblItemClmPackSize.setCellValueFactory(new PropertyValueFactory<Item,String>("packsize"));
        tblItemClmUnitPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("unitprice"));
        tblItemClmQTY.setCellValueFactory(new PropertyValueFactory<Item,String>("qty"));
    }

    public void addOrderIDtoComboBox(){
        cmbOrderID.getItems().clear();
        try {
            ObservableList<String> orderIDList = OrderController.getOrderID(txtcID.getText());
            for (int i=0;i<orderIDList.size();i++){
                cmbOrderID.getItems().add(orderIDList.get(i));
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void getOrderCount(){
        try {
            index = OrderController.getOrderCount();
            if (index<10){
                txtOrderID.setText("D00"+index);
            }
            if (index>=10 && index<100){
                txtOrderID.setText("D0"+index);
            }
            if (index>=100){
                txtOrderID.setText("D"+index);
            }

        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void generate(){
        generateOrderDateTime();
        insertValuestoTblItem();
        getOrderCount();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        generate();
        orderID=txtOrderID.getText();
        txtcID.setText(null);
        txtQTYonHand.setDisable(true);
        txtDiscount.setDisable(true);
        txtQTYonHand2.setDisable(true);
        btnAddtoCart.setDisable(true);
        btnConfirmOrderEdit.setDisable(true);
        btnRemoveItem.setDisable(true);
        cmbOrderID.setVisible(false);

        btnAddtoCart.disableProperty().bind(
                txtQTYonHand.textProperty().isEqualTo(txtQTYonHand.textProperty()).not()
                        .or(
                                txtQTYonHand.textProperty().isEmpty()
                        )
        );
        btnConfirmOrder.setDisable(true);
        ////////////////////////////////////////////////////////////////////////////////////////
        JFXAutoCompletePopup<String> autoCompletePopup = new JFXAutoCompletePopup<>();
        for (int i=0;i<rows.size();i++){
            autoCompletePopup.getSuggestions().addAll(rows.get(i).getDescription());
        }

        autoCompletePopup.setSelectionHandler(event -> {
            txtSearchItem.setText(event.getObject());
        });

        txtSearchItem.textProperty().addListener(observable -> {
            autoCompletePopup.filter(String -> String.toLowerCase().contains(txtSearchItem.getText().toLowerCase()));
            if (autoCompletePopup.getFilteredSuggestions().isEmpty() || txtSearchItem.getText().isEmpty()){
                autoCompletePopup.hide();
            }else{
                autoCompletePopup.show(txtSearchItem);
            }
        });
    }

    public void tblItemClicked(){
        try {
            txtItemCode.setText(tblItems.getSelectionModel().getSelectedItem().getItemcode());
            txtDescription.setText(tblItems.getSelectionModel().getSelectedItem().getDescription());
            txtPackSize.setText(tblItems.getSelectionModel().getSelectedItem().getPacksize());
            txtUnitPrice.setText(Double.toString(tblItems.getSelectionModel().getSelectedItem().getUnitprice()));
            txtQTYonHand.setText(Integer.toString(tblItems.getSelectionModel().getSelectedItem().getQty()));
            txtQTYonHand.setDisable(false);
            txtDiscount.setText("00.00");
            txtDiscount.setDisable(false);
            btnAddtoCart.setDisable(false);
        }catch (RuntimeException ex){ }
        txtQTYonHand.requestFocus();
    }

    public void itemTableRowClickedOnActon(MouseEvent mouseEvent) throws NullPointerException {
        tblItemClicked();
    }

    public void tblCartClicked(){
        try{
            txtQTYonHand2.setText(Integer.toString(tblCart.getSelectionModel().getSelectedItem().getQty()));
            txtQTYonHand2.setDisable(false);
            btnConfirmOrderEdit.setDisable(false);
            btnRemoveItem.setDisable(false);
            btnConfirmOrder.setDisable(false);
        }catch (RuntimeException ex){ }
        txtQTYonHand2.requestFocus();
    }

    public void cartTableRowClickedOnActon(MouseEvent mouseEvent) {
        tblCartClicked();
    }

    public void cmbOrderIDOnAction(ActionEvent actionEvent) {
        txtQTYonHand2.setText(null);
        try {
            ObservableList<Item> rowsForCart = OrderDetailController.getOrderDetails(cmbOrderID.getSelectionModel().getSelectedItem());
            setTblCartColumns();
            tblCart.getItems().clear();
            tblCart.setItems(rowsForCart);
            setFinalTot();
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void setTblCartColumns(){
        tblCartClmItemCode.setCellValueFactory(new PropertyValueFactory<Item,String>("itemcode"));
        tblCartClmDescription.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
        tblCartClmUnitPrice.setCellValueFactory(new PropertyValueFactory<Item,String>("unitprice"));
        tblCartClmQTY.setCellValueFactory(new PropertyValueFactory<Item,String>("qty"));
        tblCartClmDiscount.setCellValueFactory(new PropertyValueFactory<Item,String>("discount"));
        tblCartClmTotal.setCellValueFactory(new PropertyValueFactory<Item,String>("total"));
    }

    public void txtContactKeyPressed(KeyEvent keyEvent) {
        findCustomer();
    }

    public void txtContactKeyReleased(KeyEvent keyEvent) {
        findCustomer();
    }

    public void txtContactKeyTyped(KeyEvent keyEvent) {
        findCustomer();
    }

    public void findCustomer(){
        try {
            Customer cd = CustomerController.searchCustomer(txtContact.getText());
            if (cd!=null){
                txtcID.setText(cd.getCid());
                txtName.setText(cd.getName());
                addOrderIDtoComboBox();
                cmbOrderID.setVisible(true);
            }else {
                txtcID.setText(null);
                txtName.setText(null);
                cmbOrderID.getItems().clear();
                cmbOrderID.setVisible(false);
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void qtyFieldOnEnter(ActionEvent actionEvent) {
        addToCart();
    }

    public void qty2FieldOnEnter(ActionEvent actionEvent) {
        confirmOrderEdit();
    }

    public void txtDiscountOnEnter(ActionEvent actionEvent) {
        addToCart();
    }

    public void SearchItemKeyPressed(KeyEvent keyEvent) {
        findItemDetailsFromSearchField();
    }

    public void SearchItemKeyReleased(KeyEvent keyEvent) {
        findItemDetailsFromSearchField();
    }

    public void SearchItemKeyTyped(KeyEvent keyEvent) {
        findItemDetailsFromSearchField();
    }

    public void findItemDetailsFromSearchField(){
        try {
            Item item = ItemController.viewItemDetailsfromSearchField(txtSearchItem.getText());
            if (item !=null){
                txtItemCode.setText(item.getItemcode());
                txtDescription.setText(item.getDescription());
                txtPackSize.setText(item.getPacksize());
                txtUnitPrice.setText(Double.toString(item.getUnitprice()));
                txtQTYonHand.setText(Integer.toString(item.getQty()));
                txtQTYonHand.setDisable(false);
                txtDiscount.setText("00.00");
                txtDiscount.setDisable(false);
            }else{
                textFieldReset();
                txtQTYonHand.setDisable(true);
                txtDiscount.setDisable(true);
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    /*public void addTextLimiter(){
        txtContact.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (txtContact.getText().length()>10){
                    String s = txtContact.getText().substring(0,10);
                    txtContact.setText(s);
                }
            }
        });
    }*/
}
