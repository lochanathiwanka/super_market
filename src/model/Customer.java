package model;

public class Customer {
    private String cid;
    private String name;
    private String address;
    private String city;
    private String contact;

    public Customer() {
    }

    public Customer(String cid, String name, String address, String city, String contact) {
        this.cid = cid;
        this.name = name;
        this.address = address;
        this.city = city;
        this.contact = contact;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
}
