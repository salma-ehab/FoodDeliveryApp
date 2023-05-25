package com.example.mobileprojectwagba.Models;

public class Restaurant {

    private String name;
    private String imageLink;

    public Restaurant(String name, String imageLink) {
        this.name = name;
        this.imageLink = imageLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getName() {
        return name;
    }

}

