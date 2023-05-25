package com.example.mobileprojectwagba.Models;

public class Dish {

    private String name;
    private String imageLink;
    private String description;
    private long price;
    private String availability;
    private String restaurantName;

    public Dish(String name, String imageLink, String description, long price, String availability, String restaurantName) {
        this.name = name;
        this.imageLink = imageLink;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.restaurantName = restaurantName;
    }

    public String getName() {
        return name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getDescription() {
        return description;
    }

    public long getPrice() {
        return price;
    }

    public String getAvailability() {
        return availability;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
