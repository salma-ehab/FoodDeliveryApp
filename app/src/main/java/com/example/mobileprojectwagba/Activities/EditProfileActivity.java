package com.example.mobileprojectwagba.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileprojectwagba.Models.UserTable;
import com.example.mobileprojectwagba.R;
import com.example.mobileprojectwagba.ViewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class EditProfileActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    String currentUserEmail;
    EditText userName;
    EditText userPhoneNumber;
    String userNameValue;
    String userPhoneNumberValue;
    String phoneRegularExpression;
    UserTable newUserData;
    Button confirmBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        phoneRegularExpression = "^01[0125][0-9]{8}$";

        currentUserEmail = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(getApplicationContext());


        confirmBtn = findViewById(R.id.confirmEditBtn);
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                userName = findViewById(R.id.editTxtUserNameEditProfile);
                userPhoneNumber = findViewById(R.id.editTxtUserMobileEditProfile);

                userNameValue = userName.getText().toString();
                userPhoneNumberValue = userPhoneNumber.getText().toString();

                if(!userNameValue.isEmpty())
                {
                    userViewModel.getUserData(currentUserEmail).observe(EditProfileActivity.this, new Observer<UserTable>() {
                        @Override
                        public void onChanged(UserTable userTable) {
                            newUserData = new UserTable(userTable.getID(),userNameValue,userTable.getEmail(),userTable.getPassword(), userTable.getPhoneNumber());
                            userViewModel.updateUser(newUserData);
                        }
                    });
                }
                

                if(!userPhoneNumberValue.isEmpty())
                {
                    if ((userPhoneNumberValue).matches(phoneRegularExpression))
                    {
                        userViewModel.getUserData(currentUserEmail).observe(EditProfileActivity.this, new Observer<UserTable>() {
                            @Override
                            public void onChanged(UserTable userTable) {
                                newUserData = new UserTable(userTable.getID(),userTable.getName(),userTable.getEmail(),userTable.getPassword(), userPhoneNumberValue);
                                userViewModel.updateUser(newUserData);
                            }
                        });
                    }

                    else
                    {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.wrongPhoneNumber), Toast.LENGTH_LONG).show();
                        return;
                    }
                }


                Toast.makeText(getApplicationContext(),getResources().getString(R.string.confirmProfileEdit),Toast.LENGTH_LONG).show();
            }
        });
    }
}