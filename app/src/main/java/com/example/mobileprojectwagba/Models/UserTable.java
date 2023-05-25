package com.example.mobileprojectwagba.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "users")
public class UserTable
{
    @PrimaryKey
    @NonNull
    private String ID;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;

    public UserTable(@NonNull String ID, String name, String email, String password, String phoneNumber) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getID() {
        return ID;
    }
}
