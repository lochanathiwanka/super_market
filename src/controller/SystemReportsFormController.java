package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.StringConverter;
import model.Item;
import model.Months;
import model.Years;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;

public class SystemReportsFormController implements Initializable {
    public JFXTextField txtMostMovableItem;
    public JFXTextField txtLeastMovableItem;
    public JFXDatePicker datePicker;
    public Label txtMonthlyIncome;
    public Label txtYearlyIncome;
    public Label txtDailyIncome;
    public TextField txtDate;
    public AnchorPane dailyIncomePane;
    public Label txtIncomeOnSpecificDate;
    public Label lblMonthlyIncomeOnSpecificDate;
    public Label lblYearlyncomeOnSpecificDate;
    public JFXComboBox cmbMonth;
    public JFXComboBox cmbYear;
    public Label txtDayOnPane;
    public Label txtMonthOnPane;
    public Label txtYearOnPane;

    @FXML
    private TableView<Item> tblItems;

    @FXML
    private TableColumn<Item, String> clmItemCode;

    @FXML
    private TableColumn<Item, String> clmDescription;

    @FXML
    private TableColumn<Item, String> clmPackSize;

    @FXML
    private TableColumn<Item, String> clmMovable;

    ObservableList<Item>rows;

    public void convertDatePicker(){
        datePicker.setConverter(new StringConverter<LocalDate>() {
            final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd");
            @Override
            public String toString(LocalDate date) {
                return (date!=null) ? dateFormatter.format(date) : "";
            }

            @Override
            public LocalDate fromString(String string) {
                return (string !=null && !string.isEmpty()) ? LocalDate.parse(string,dateFormatter) : null;
            }
        });
    }

    public void setValuesToCmbMonth(){
        String[]month = {"Select Month", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        ObservableList<Months>monthList = FXCollections.observableArrayList();
        for (int i=0;i<month.length;i++){
            monthList.add(new Months(month[i]));
            cmbMonth.getItems().add(monthList.get(i).toString());
        }
    }

    public void setValuesToCmbYear(){
        String[]year = {"Select Year", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"};
        ObservableList<Years>yearList = FXCollections.observableArrayList();
        for (int i=0;i<year.length;i++){
            yearList.add(new Years(year[i]));
            cmbYear.getItems().add(yearList.get(i).toString());
        }
    }

    public void getMostMovableItem(){
        try {
            String item = OrderDetailController.getMostMovableItem();
            if (item!=null) {
                txtMostMovableItem.setText(item);
            }
        }catch (ClassNotFoundException ex){ }
        catch (SQLException ex){}
    }

    public void getLeastMovableItem(){
        try {
            String item = OrderDetailController.getLeastMovableItem();
            if (item!=null) {
                txtLeastMovableItem.setText(item);
            }
        }catch (ClassNotFoundException ex){ }
        catch (SQLException ex){}
    }

    public void getAllItemsByMovableDESC(){
        try {
            rows = OrderDetailController.getItemDetailsByMovableDesc();

            clmItemCode.setCellValueFactory(new PropertyValueFactory<Item,String>("itemcode"));
            clmDescription.setCellValueFactory(new PropertyValueFactory<Item,String>("description"));
            clmPackSize.setCellValueFactory(new PropertyValueFactory<Item,String>("packsize"));
            clmMovable.setCellValueFactory(new PropertyValueFactory<Item,String>("movable"));
            tblItems.getItems().clear();
            tblItems.setItems(rows);

        }catch (ClassNotFoundException | SQLException ex){ }
    }

    public String[] getDate(){
        Date date = new Date();
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MMM-dd");
        String currentDate = d.format(date);
        txtDate.setText(d.format(date));

        SimpleDateFormat sdf = new SimpleDateFormat("dd");
        txtDayOnPane.setText(sdf.format(date));

        SimpleDateFormat m = new SimpleDateFormat("MMM");
        String currentMonth = m.format(date);
        txtMonthOnPane.setText(m.format(date));

        SimpleDateFormat y = new SimpleDateFormat("yyyy");
        String currentYear = y.format(date);
        txtYearOnPane.setText(y.format(date));

        String[]dt = {currentDate,currentMonth,currentYear};
        return dt;
    }

    public void getDailyIncomeOnCurrentDate(){
        try {
            String income = OrderDetailController.getIncome(getDate()[0]);
            if (income!=null) {
                txtDailyIncome.setText(income);
            }
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }

    public void getMonthlyIncomeOnCurrentMonth(){
        try {
            String income = OrderDetailController.getIncome(getDate()[1]);
            if (income!=null) {
                txtMonthlyIncome.setText(income);
            }
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }

    public void getYearlyIncomeOnCurrentYear(){
        try {
            String income = OrderDetailController.getIncome(getDate()[2]);
            if (income!=null) {
                txtYearlyIncome.setText(income);
            }
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        convertDatePicker();
        getMostMovableItem();
        getLeastMovableItem();
        getAllItemsByMovableDESC();
        getDailyIncomeOnCurrentDate();
        getMonthlyIncomeOnCurrentMonth();
        getYearlyIncomeOnCurrentYear();
        dailyIncomePane.setVisible(false);
        txtIncomeOnSpecificDate.setVisible(false);
        lblMonthlyIncomeOnSpecificDate.setVisible(false);
        lblYearlyncomeOnSpecificDate.setVisible(false);
        setValuesToCmbMonth();
        setValuesToCmbYear();
        setLayoutForCmbMonthandYear();
    }

    public void setLayoutForCmbMonthandYear(){
        cmbMonth.setLayoutY(505);
        cmbMonth.setLayoutX(72);
        lblMonthlyIncomeOnSpecificDate.setLayoutY(505);
        lblMonthlyIncomeOnSpecificDate.setLayoutX(253);

        cmbYear.setLayoutY(580);
        cmbYear.setLayoutX(72);
        lblYearlyncomeOnSpecificDate.setLayoutY(580);
        lblYearlyncomeOnSpecificDate.setLayoutX(253);
    }

    public void resetLayoutInCmbMonthandYear(){
        cmbMonth.setLayoutY(612);
        cmbMonth.setLayoutX(72);
        lblMonthlyIncomeOnSpecificDate.setLayoutY(612);
        lblMonthlyIncomeOnSpecificDate.setLayoutX(253);

        cmbYear.setLayoutY(678);
        cmbYear.setLayoutX(72);
        lblYearlyncomeOnSpecificDate.setLayoutY(678);
        lblYearlyncomeOnSpecificDate.setLayoutX(253);
    }

    public void getIncomeOnSpecificDate(){
        try {
            String date = datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MMM-dd"));
            String income = OrderDetailController.getIncome(date);
            if (income!=null) {
                txtIncomeOnSpecificDate.setText(income);
                dailyIncomePane.setVisible(true);
                txtIncomeOnSpecificDate.setVisible(true);
                resetLayoutInCmbMonthandYear();
            }else {
                txtIncomeOnSpecificDate.setText("00.00");
                dailyIncomePane.setVisible(false);
                txtIncomeOnSpecificDate.setVisible(false);
                setLayoutForCmbMonthandYear();
            }
        }catch (ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }catch (NullPointerException ex){}
    }

    public void pickDateKeyPressed(KeyEvent keyEvent) {
        getIncomeOnSpecificDate();
    }

    public void pickDateKeyReleased(KeyEvent keyEvent) {
        getIncomeOnSpecificDate();
    }

    public void pickDateKeyTyped(KeyEvent keyEvent) {
        getIncomeOnSpecificDate();
    }

    public void datePickerOnAction(ActionEvent actionEvent) {
        getIncomeOnSpecificDate();
    }

    public void cmbMonthOnAction(ActionEvent actionEvent) {
        lblMonthlyIncomeOnSpecificDate.setText(null);
        try {
            String month = cmbMonth.getSelectionModel().getSelectedItem().toString();
            String income = OrderDetailController.getIncome(month.substring(0,3));
            if (!income.equalsIgnoreCase("00.00")) {
                lblMonthlyIncomeOnSpecificDate.setText(income);
                lblMonthlyIncomeOnSpecificDate.setVisible(true);
            }else {
                lblMonthlyIncomeOnSpecificDate.setVisible(false);
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }catch (NullPointerException ex){}
    }

    public void cmbYearOnAction(ActionEvent actionEvent) {
        lblYearlyncomeOnSpecificDate.setText(null);
        try {
            String year = cmbYear.getSelectionModel().getSelectedItem().toString();
            String income = OrderDetailController.getIncome(year);
            if (!income.equalsIgnoreCase("00.00")) {
                lblYearlyncomeOnSpecificDate.setText(income);
                lblYearlyncomeOnSpecificDate.setVisible(true);
            }else {
                lblYearlyncomeOnSpecificDate.setVisible(false);
            }
        }catch (ClassNotFoundException ex){
            ex.printStackTrace();
        }catch (SQLException ex){
            ex.printStackTrace();
        }catch (NullPointerException ex){}
    }
}
 