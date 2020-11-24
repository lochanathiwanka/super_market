package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Item {
    private SimpleStringProperty itemcode;
    private SimpleStringProperty description;
    private SimpleStringProperty packsize;
    private SimpleDoubleProperty unitprice;
    private SimpleIntegerProperty qty;
    private SimpleStringProperty date;
    private SimpleStringProperty time;
    private SimpleDoubleProperty discount;
    private SimpleDoubleProperty total;
    private SimpleStringProperty movable;

    public Item() {
    }

    public Item(String itemcode, String description, String packsize,String movable) {
        this.itemcode = new SimpleStringProperty(itemcode);
        this.description = new SimpleStringProperty(description);
        this.packsize = new SimpleStringProperty(packsize);
        this.movable = new SimpleStringProperty(movable);
    }

    public Item(String itemcode, String description, double unitprice, int qty, double discount, double total) {
        this.itemcode = new SimpleStringProperty(itemcode);
        this.description = new SimpleStringProperty(description);
        this.unitprice = new SimpleDoubleProperty(unitprice);
        this.qty = new SimpleIntegerProperty(qty);
        this.discount = new SimpleDoubleProperty(discount);
        this.total = new SimpleDoubleProperty(total);
    }

    public Item(String itemcode, String description, String packsize, double unitprice, int qty, String date, String time) {
        this.itemcode = new SimpleStringProperty(itemcode);
        this.description = new SimpleStringProperty(description);
        this.packsize = new SimpleStringProperty(packsize);
        this.unitprice = new SimpleDoubleProperty(unitprice);
        this.qty = new SimpleIntegerProperty(qty);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }

    public String getItemcode() {
        return itemcode.get();
    }

    public SimpleStringProperty itemcodeProperty() {
        return itemcode;
    }

    public void setItemcode(String itemcode) {
        this.itemcode.set(itemcode);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public String getPacksize() {
        return packsize.get();
    }

    public SimpleStringProperty packsizeProperty() {
        return packsize;
    }

    public void setPacksize(String packsize) {
        this.packsize.set(packsize);
    }

    public double getUnitprice() {
        return unitprice.get();
    }

    public SimpleDoubleProperty unitpriceProperty() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice.set(unitprice);
    }

    public int getQty() {
        return qty.get();
    }

    public SimpleIntegerProperty qtyProperty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty.set(qty);
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public double getTotal() {
        return total.get();
    }

    public SimpleDoubleProperty totalProperty() {
        return total;
    }

    public void setTotal(double total) {
        this.total.set(total);
    }

    public double getDiscount() {
        return discount.get();
    }

    public SimpleDoubleProperty discountProperty() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount.set(discount);
    }

    public String getMovable() {
        return movable.get();
    }

    public SimpleStringProperty movableProperty() {
        return movable;
    }

    public void setMovable(String movable) {
        this.movable.set(movable);
    }
}
