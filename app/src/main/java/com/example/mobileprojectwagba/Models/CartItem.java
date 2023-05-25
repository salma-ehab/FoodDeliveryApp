package com.example.mobileprojectwagba.Models;

public class CartItem {

    private String imageLink;
    private long price;
    private String name;
    private String restaurantName;
    private long quantity;

    public CartItem(String imageLink, long price, String name,String restaurantName,long quantity) {
        this.imageLink = imageLink;
        this.price = price;
        this.name = name;
        this.restaurantName = restaurantName;
        this.quantity = quantity;

    }

    public String getImageLink() {
        return imageLink;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
