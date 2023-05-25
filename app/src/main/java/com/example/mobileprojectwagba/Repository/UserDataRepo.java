package com.example.mobileprojectwagba.Repository;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.room.OnConflictStrategy;

import com.example.mobileprojectwagba.Database.UserDatabase;
import com.example.mobileprojectwagba.Models.UserTable;

public class UserDataRepo {

    private UserDatabase userDatabase;

    public UserDataRepo(Context context)
    {
        userDatabase = UserDatabase.getInstance(context);
    }

    public void insertUser(UserTable userTable)
    {
        userDatabase.userDao().insertUserData(userTable);
    }

    public void updateUser(UserTable userTable)
    {
        userDatabase.userDao().updateUserData(userTable);
    }

    public LiveData<UserTable> getUserData(String email)
    {
       return userDatabase.userDao().getUserData(email);
    }

    public String getUserID (String id)
    {
        return userDatabase.userDao().getUserID(id);
    }


}
