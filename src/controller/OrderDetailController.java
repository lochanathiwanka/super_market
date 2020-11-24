package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.OrderDetail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OrderDetailController {

    public static boolean addOrderDetail(ObservableList<OrderDetail> orderDetailList) throws ClassNotFoundException, SQLException {
        for (OrderDetail od : orderDetailList){
            boolean isAddedtoOrderDetail = addOrderDetail(od);
            if (!isAddedtoOrderDetail){
                return false;
            }
        }
        return true;
    }

    public static boolean addOrderDetail(OrderDetail orderDetail) throws ClassNotFoundException, SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO OrderDetail (orderID,itemCode,qtyOnHand,discount,totPrice) VALUES (?,?,?,?,?)");
        stm.setObject(1,orderDetail.getOrderId());
        stm.setObject(2,orderDetail.getItemCode());
        stm.setObject(3,orderDetail.getQtyOnHand());
        stm.setObject(4,orderDetail.getDiscount());
        stm.setObject(5,orderDetail.getTotPrice());
        return stm.executeUpdate()>0;
    }

    public static String getMostMovableItem() throws ClassNotFoundException, SQLException {
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet rst = stm.executeQuery("Select description,packSize,od.itemcode, count(orderid) from Item i,OrderDetail od where i.itemCode=od.itemCode group by itemcode order by 4 desc limit 10");
        String itemDescription = " ";
        String packSize = " ";
        if (rst.next()){
            itemDescription=rst.getString("description");
            packSize=rst.getString("packSize");
        }
        return itemDescription+" "+packSize;
    }

    public static String getLeastMovableItem() throws ClassNotFoundException, SQLException {
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet rst = stm.executeQuery("Select description,packSize,od.itemcode, count(orderid) from Item i,OrderDetail od where i.itemCode=od.itemCode group by itemcode order by 4 limit 10");
        String itemDescription = " ";
        String packSize = " ";
        if (rst.next()){
            itemDescription=rst.getString("description");
            packSize=rst.getString("packSize");
        }
        return itemDescription+" "+packSize;
    }

    public static ObservableList<Item> getItemDetailsByMovableDesc() throws ClassNotFoundException, SQLException {
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet rst = stm.executeQuery("Select od.itemcode,description,packSize, count(orderid) as movable from Item i,OrderDetail od where i.itemCode=od.itemCode group by itemcode order by 4 desc");
        ObservableList<Item>itemList = FXCollections.observableArrayList();
        while (rst.next()){
            itemList.add(new Item(rst.getString("itemCode"),rst.getString("description"),rst.getString("packSize"),rst.getString("movable")));
        }
        return itemList;
    }

    public static String getIncome(String date) throws ClassNotFoundException, SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Select sum(totPrice-discount) as Income From OrderDetail od,Orders o where od.orderId=o.orderID && orderDate like ?");
        stm.setObject(1,"%"+date+"%");
        ResultSet rst = stm.executeQuery();
        String income = "00.00";
        if (rst.next()){
            income=rst.getString("Income");
        }
        return income;
    }

    public static ObservableList<Item> getOrderDetails(Object orderID) throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("select i.itemCode,description,unitPrice,od.qtyOnHand,discount,totPrice from Item i,Orders o,OrderDetail od where (i.itemCode=od.itemCode && o.orderID=od.orderId) && od.orderId =?");
        stm.setObject(1,orderID);
        ResultSet rst = stm.executeQuery();
        ObservableList<Item>rows = FXCollections.observableArrayList();
        while (rst.next()){
            rows.add(new Item(rst.getString("itemCode"),rst.getString("description"),
                    rst.getDouble("unitPrice"),rst.getInt("qtyOnHand"),
                    rst.getDouble("discount"),rst.getDouble("totPrice")));
        }
        return rows;
    }
}
