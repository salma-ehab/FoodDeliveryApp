package com.example.mobileprojectwagba.Repository;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.mobileprojectwagba.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class SignUpLogInRepo {

    private MutableLiveData<FirebaseUser> userMutableLiveData;
    private FirebaseAuth auth;

    public SignUpLogInRepo()
    {
        auth = FirebaseAuth.getInstance();
        userMutableLiveData = new MutableLiveData<>();
    }

    public void signUp(String email, String password, Context context,Intent intent)
    {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userMutableLiveData.postValue(auth.getCurrentUser());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else
                        {
                            String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                            switch (errorCode)
                            {
                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    Toast.makeText(context, context.getResources().getString(R.string.failedSignUpEmailInUse), Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    Toast.makeText(context, context.getResources().getString(R.string.failedSignUpInvalidEmail),Toast.LENGTH_LONG).show();
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    Toast.makeText(context, context.getResources().getString(R.string.failedSignUpShortPassword),Toast.LENGTH_LONG).show();
                                    break;

                                default:
                                    Toast.makeText(context, context.getResources().getString(R.string.failedSignUpDefault),Toast.LENGTH_LONG).show();

                            }}}});}

    public void login(String email, String password, Context context,Intent intent)
    {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            userMutableLiveData.postValue(auth.getCurrentUser());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(context,context.getResources().getString(R.string.failedLogin),Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }

    public MutableLiveData<FirebaseUser> getUserMutableLiveData()
    {
        return userMutableLiveData;
    }
}
