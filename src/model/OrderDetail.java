package model;

public class OrderDetail {
    private String orderId;
    private String itemCode;
    private int qtyOnHand;
    private double discount;
    private double totPrice;

    public OrderDetail() {
    }

    public OrderDetail(String orderId, String itemCode, int qtyOnHand, double discount, double totPrice) {
        this.orderId = orderId;
        this.itemCode = itemCode;
        this.qtyOnHand = qtyOnHand;
        this.discount = discount;
        this.totPrice = totPrice;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotPrice() {
        return totPrice;
    }

    public void setTotPrice(double totPrice) {
        this.totPrice = totPrice;
    }
}
