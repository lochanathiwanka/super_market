package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerController {
    public static boolean addCustomer(Customer cd) throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO customer(cid,name,address,city,contact) VALUES (?,?,?,?,?)");
        stm.setObject(1,cd.getCid());
        stm.setObject(2,cd.getName());
        stm.setObject(3,cd.getAddress());
        stm.setObject(4,cd.getCity());
        stm.setObject(5,cd.getContact());
        return stm.executeUpdate()>0;
    }

    public static int getCustomerCount() throws ClassNotFoundException,SQLException{
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet rst = stm.executeQuery("Select count(*) from customer");
        int index=0;
        if (rst.next()){
            index=(rst.getInt(1)+1);
        }
        return index;
    }

    public static ObservableList<Customer> getCustomerID() throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Select * from customer");
        ResultSet rst = stm.executeQuery();
        ObservableList<Customer>cID = FXCollections.observableArrayList();
        while (rst.next()){
            cID.add(new Customer(rst.getString("cid"),rst.getString("name"),
                    rst.getString("address"),rst.getString("city"),rst.getString("contact")));
        }
        return cID;
    }

    public static Customer searchCustomer(String contact) throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Select * from customer where contact =?");
        stm.setObject(1,contact);
        ResultSet rst = stm.executeQuery();
        Customer cd =null;
        if (rst.next()){
            cd = new Customer(rst.getString("cid"),rst.getString("name"),
                    rst.getString("address"),rst.getString("city"),rst.getString("contact"));
        }
        return cd;
    }
}
