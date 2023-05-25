package com.example.mobileprojectwagba.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mobileprojectwagba.Models.UserTable;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserData(UserTable userTable);

    @Update
    void updateUserData(UserTable userTable);

    @Query("SELECT * FROM users WHERE email=:email")
    LiveData<UserTable> getUserData(String email);

    @Query("SELECT ID FROM users WHERE ID=:id")
    String getUserID(String id);

}
