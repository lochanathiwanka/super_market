package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Item;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController {
    public static boolean addItems(String itemCode,String description,String packSize,String unitPrice,String qty,String date,String time) throws ClassNotFoundException, SQLException {
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Insert into Item (itemCode, description, packSize, unitPrice, qtyOnHand,stockedDate,stockedTime) values (?,?,?,?,?,?,?)");
        stm.setObject(1,itemCode);
        stm.setObject(2,description);
        stm.setObject(3,packSize);
        stm.setObject(4,unitPrice);
        stm.setObject(5,qty);
        stm.setObject(6,date);
        stm.setObject(7,time);
        return stm.executeUpdate()>0;
    }

    public static int getItemsCount() throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Select count(*) from Item where status = ?");
        stm.setObject(1,"InStock");
        ResultSet rst = stm.executeQuery();
        int index=-1;
        if (rst.next()){
            index=(rst.getInt(1));
        }
        return index;
    }

    public static ObservableList<Item> getItems() throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Select itemCode, description, packSize, unitPrice, qtyOnHand,stockedDate,stockedTime from Item where status = ?");
        stm.setObject(1,"InStock");
        ResultSet rst = stm.executeQuery();
        ObservableList<Item>rows = FXCollections.observableArrayList();
        while (rst.next()){
            rows.add(new Item(rst.getString("itemCode"),rst.getString("description"),
                    rst.getString("packSize"),rst.getDouble("unitPrice"),
                    rst.getInt("qtyOnHand"),rst.getString("stockedDate"),
                    rst.getString("stockedTime")));
        }
        return rows;
    }

    public static int getRemovedItemsCount() throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Select count(*) from Item where status = ?");
        stm.setObject(1,"Removed");
        ResultSet rst = stm.executeQuery();
        int index=0;
        if (rst.next()){
            index=rst.getInt(1);
        }
        return index;
    }

    public static boolean removeItem(String itemCode) throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Update Item set itemCode = ?, status = ? where itemCode = ?");
        int count = getRemovedItemsCount()+1;
        String index=Integer.toString(count);
        stm.setObject(1,"00"+index);
        stm.setObject(2,"Removed");
        stm.setObject(3 ,itemCode);
        return stm.executeUpdate()>0;
    }

    public static boolean modifyItemDetails(String itemCode,String description,String pakSize,String unitPrice,String qtyOnHand)throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Update Item set description=? , packSize=?, unitPrice=?, qtyOnHand=? where itemCode = ?");
        stm.setObject(1,description);
        stm.setObject(2,pakSize);
        stm.setObject(3 ,unitPrice);
        stm.setObject(4 ,qtyOnHand);
        stm.setObject(5 ,itemCode);
        return stm.executeUpdate()>0;
    }

    public static int getItemQTY(String itemCode) throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("SELECT qtyOnHand FROM Item WHERE itemCode=?");
        stm.setObject(1,itemCode);
        ResultSet rst=stm.executeQuery();
        int qty=-1;
        if (rst.next()){
            qty=rst.getInt("qtyOnHand");
        }
        return qty;
    }

    public static Item viewItemDetailsfromSearchField(String value) throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Select itemCode, description, packSize, unitPrice, qtyOnHand,stockedDate,stockedTime from Item where itemCode = ? || description = ?");
        stm.setObject(1,value);
        stm.setObject(2,value);
        ResultSet rst=stm.executeQuery();
        Item item = null;
        if (rst.next()){
            item = new Item(rst.getString("itemCode"),rst.getString("description"),
                    rst.getString("packSize"),rst.getDouble("unitPrice"),
                    rst.getInt("qtyOnHand"),rst.getString("stockedDate"),
                    rst.getString("stockedTime"));
        }
        return item;
    }

    public static boolean updateItemQTY(ObservableList<Integer> qty,ObservableList<String> itemCode) throws ClassNotFoundException,SQLException{
        for (int i=0;i<itemCode.size();i++){
            boolean isUpdated = updateItemQTY(qty.get(i),itemCode.get(i));
            if (!isUpdated){
                return false;
            }
        }
        return true;
    }

    public static boolean updateItemQTY(int qty,String itemCode) throws ClassNotFoundException,SQLException{
        PreparedStatement stm = DBConnection.getInstance().getConnection().prepareStatement("Update Item SET qtyOnHand=? WHERE itemCode = ?");
        stm.setObject(1,qty);
        stm.setObject(2,itemCode);
        return stm.executeUpdate()>0;
    }
}
