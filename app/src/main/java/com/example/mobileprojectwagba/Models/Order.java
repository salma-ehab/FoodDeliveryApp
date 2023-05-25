package com.example.mobileprojectwagba.Models;

public class Order {

    private String ID;
    private String user;
    private String date;
    private long price;
    private String status;

    public Order(String ID,String user, String date, long price, String status) {
        this.ID = ID;
        this.user = user;
        this.date = date;
        this.price = price;
        this.status = status;
    }

    public String getID() {
        return ID;
    }

    public String getUser() {
        return user;
    }

    public String getDate() {
        return date;
    }

    public long getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }


}
