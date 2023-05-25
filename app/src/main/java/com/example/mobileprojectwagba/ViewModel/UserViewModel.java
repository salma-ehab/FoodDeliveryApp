package com.example.mobileprojectwagba.ViewModel;


import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.mobileprojectwagba.Models.UserTable;
import com.example.mobileprojectwagba.Repository.UserDataRepo;

public class UserViewModel extends ViewModel {

    private UserDataRepo userDataRepo;

    public void init(Context context)
    {
        userDataRepo= new UserDataRepo(context);
    }

    public void insertUser (UserTable user)
    {
        userDataRepo.insertUser(user);
    }

    public void updateUser (UserTable user)
    {
        userDataRepo.updateUser(user);
    }

    public LiveData<UserTable> getUserData (String email)
    {
        return userDataRepo.getUserData(email);
    }

    public String getUserID (String id)
    {
        return userDataRepo.getUserID(id);
    }
}
