package com.example.mobileprojectwagba.ViewModel;


import android.content.Context;
import android.content.Intent;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mobileprojectwagba.Repository.SignUpLogInRepo;
import com.google.firebase.auth.FirebaseUser;


public class SignUpLogInViewModel extends ViewModel {

    private SignUpLogInRepo repo;
    private MutableLiveData<FirebaseUser> fireBaseUser;

    public void init()
    {
        repo = new SignUpLogInRepo();
        fireBaseUser = repo.getUserMutableLiveData();
    }


    public void signUp(String email, String password, Context context, Intent intent)
    {
        repo.signUp(email,password,context,intent);

    }

    public void LogIn(String email, String password, Context context, Intent intent)
    {
        repo.login(email,password,context,intent);

    }

    public MutableLiveData<FirebaseUser> getFireBaseUser()
    {
        return fireBaseUser;
    }
}
