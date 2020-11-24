package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import model.Orders;

import java.sql.*;

public class OrderController {
    public static int getOrderCount() throws ClassNotFoundException, SQLException {
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT count(*) FROM Orders");
        int index=0;
        if (rst.next()){
            index=(rst.getInt(1)+1);
        }
        return index;
    }

    public static boolean placeOrder(Orders orders,ObservableList<Integer> qty,ObservableList<String> itemCode) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("INSERT INTO Orders (orderID,cid,orderDate,orderTime) VALUES (?,?,?,?)");
            stm.setObject(1, orders.getOrderID());
            stm.setObject(2, orders.getCid());
            stm.setObject(3, orders.getOrderDate());
            stm.setObject(4, orders.getOrderTime());
            boolean isAddedOrder = stm.executeUpdate() > 0;
            if (isAddedOrder) {
                boolean isAddedOrderDetail = OrderDetailController.addOrderDetail(orders.getOrderDetailList());
                if (isAddedOrderDetail) {
                    boolean isUpdatedItemQTY = ItemController.updateItemQTY(qty, itemCode);
                    if (isUpdatedItemQTY) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }
    }

    public static ObservableList<String> getOrderID(String cid)throws ClassNotFoundException, SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT orderID from Orders where cid = ?");
        stm.setObject(1,cid);
        ResultSet rst = stm.executeQuery();
        ObservableList<String>orderIDlist = FXCollections.observableArrayList();
        while (rst.next()){
            orderIDlist.add(rst.getString("orderID"));
        }
        return orderIDlist;
    }
}
