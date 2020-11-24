package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import model.City;
import model.Customer;
import model.StageList;

import javax.swing.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddNewCustomerFormController extends StageList implements Initializable {
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtCity;
    public TextField txtContact;
    public TextField txtID;
    public int index=0;
    public JFXButton btnAddCustomer;
    public JFXComboBox cmbChoseCity;

    public void btnAddCustomerOnAction(ActionEvent actionEvent) {
        Customer cd = new Customer(txtID.getText(),txtName.getText(),txtAddress.getText(),txtCity.getText(),txtContact.getText());
        try {
            boolean isAdded = CustomerController.addCustomer(cd);
            if (isAdded){
                JOptionPane.showMessageDialog(null,"Customer was successfully added to the system","",JOptionPane.INFORMATION_MESSAGE);
                setCID();
                textFieldReset();
                cmbChoseCity.getItems().clear();
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            JOptionPane.showMessageDialog(null,"Fields cannot be empty!","",JOptionPane.WARNING_MESSAGE);
        }
    }

    public void addItemsToCmbChoseCity(){
        String[]city = {
                "None", "Akkaraipattu", "Aluthgama", "Ambalangoda", "Ambalanthota", "Ampara", "Anuradhapura", "Badulla", "Balangoda", "Bandaragama",
                "Bandarawela", "Batticaloa", "Beruwala", "Boralesgamuwa", "Chavakachcheri", "Chilaw", "Colombo", "Dambulla", "Dehiwala-Mount Lavinia",
                "Embilipitiya", "Eravur", "Galle", "Gampaha", "Gampola", "Hambantota", "Haputale", "Hatton-Dickoya", "Hikkaduwa", "Horana", "Ja-Ela",
                "Jaffna", "Kadugannawa", "Kaduwela (Battaramulla)", "Kalmunai (Sainthamarathu)", "Kalutara", "Kandy", "Kattankudy (Kathankudi)",
                "Katunayake (Seeduwa)", "Kegalle", "Kesbewa", "Kilinochchi", "Kinniya", "Kolonnawa", "Kuliyapitiya", "Kurunegala", "Maharagama",
                "Mannar", "Matale", "Matara", "Mathugama", "Minuwangoda", "Moneragala", "Moratuwa", "Mullaitivu", "Nawalapitiya", "Negombo", "Nuwara Eliya",
                "Panadura", "Peliyagoda", "Point Pedro", "Polonnaruwa", "Puttalam", "Ratnapura", "Seethawakapura (Avissawella)", "Sri Jayawardenepura (Kotte)",
                "Tangalle","Thalawakele-Lindula", "Trincomalee", "Valvettithurai", "Vavuniya", "Wattala-Mabole", "Wattegama", "Weligama"};

        ObservableList<City>cityList = FXCollections.observableArrayList();
        for (int i=0;i<city.length;i++){
            cityList.add(new City(city[i]));
            cmbChoseCity.getItems().add(cityList.get(i).toString());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setCID();
        textFieldReset();
        addItemsToCmbChoseCity();
    }

    public void setCID(){
        try {
            index = CustomerController.getCustomerCount();
            String id="";
            if (index<10){
                id="C00"+index;
                txtID.setText(id);
            }
            if (index>=10 && index<100){
                id="C0"+index;
                txtID.setText(id);
            }
            if (index>=100){
                id="C"+index;
                txtID.setText(id);
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void textFieldReset(){
        txtName.setText(null);
        txtAddress.setText(null);
        txtCity.setText(null);
        txtContact.setText(null);
    }

    public void textFieldSetEditable(){
        txtName.setEditable(true);
        txtAddress.setEditable(true);
        txtCity.setEditable(true);
    }

    public void contactKeyPressed(KeyEvent keyEvent) {
        findCustomer();
    }

    public void contactKeyReleased(KeyEvent keyEvent) {
        findCustomer();
    }

    public void contactKeyTyped(KeyEvent keyEvent) {
        findCustomer();
    }

    public void findCustomer(){
        try {
            Customer cd = CustomerController.searchCustomer(txtContact.getText());
            if (cd!=null){
                txtID.setText(cd.getCid());
                txtID.setEditable(false);
                txtName.setText(cd.getName());
                txtName.setEditable(false);
                txtAddress.setText(cd.getAddress());
                txtAddress.setEditable(false);
                txtCity.setText(cd.getCity());
                txtCity.setEditable(false);
                btnAddCustomer.setDisable(true);
            }else {
                setCID();
                txtName.setText(null);
                txtName.setEditable(true);
                txtAddress.setText(null);
                txtCity.setText(null);
                textFieldSetEditable();
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void cmbChoseCityOnAction(ActionEvent actionEvent) {
        if (cmbChoseCity.getSelectionModel().getSelectedItem().toString().equalsIgnoreCase("None")){
            txtCity.setText(null);
            return;
        }
        txtCity.setText(cmbChoseCity.getSelectionModel().getSelectedItem().toString());
    }
}
