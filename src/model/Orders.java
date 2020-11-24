package model;

import javafx.collections.ObservableList;

public class Orders {
    private String orderID;
    private String cid;
    private String orderDate;
    private String orderTime;
    private ObservableList<OrderDetail>orderDetailList;

    public Orders() {
    }

    public Orders(String orderID, String cid, String orderDate, String orderTime, ObservableList<OrderDetail> orderDetailList) {
        this.orderID = orderID;
        this.cid = cid;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.orderDetailList = orderDetailList;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public ObservableList<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(ObservableList<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}
