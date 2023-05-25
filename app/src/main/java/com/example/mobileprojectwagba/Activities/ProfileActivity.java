package com.example.mobileprojectwagba.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.mobileprojectwagba.Models.UserTable;
import com.example.mobileprojectwagba.R;
import com.example.mobileprojectwagba.ViewModel.UserViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    String currentUserEmail;
    TextView nameTxt;
    TextView IDTxt;
    TextView emailTxt;
    TextView phoneNumberTxt;
    BottomNavigationView bottomNavigationView;
    Button editProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //populate profile page
        currentUserEmail = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        Log.d("userEmail",currentUserEmail);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(getApplicationContext());
        userViewModel.getUserData(currentUserEmail).observe(this, new Observer<UserTable>() {
            @Override
            public void onChanged(UserTable userTable) {
                nameTxt = findViewById(R.id.userName);
                IDTxt =  findViewById(R.id.userID);
                emailTxt =  findViewById(R.id.userEmail);
                phoneNumberTxt =  findViewById(R.id.userMobile);

                nameTxt.setText(userTable.getName());
                IDTxt.setText(userTable.getID());
                emailTxt.setText(userTable.getEmail());
                phoneNumberTxt.setText(userTable.getPhoneNumber());
            }
        });


        bottomNavigationView = findViewById(R.id.bottomNavigationView5);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.history:
                        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(historyIntent);
                        finish();
                        return true;

                    case R.id.homePage:
                        Intent homePageIntent = new Intent(getApplicationContext(), RestaurantView.class);
                        startActivity(homePageIntent);
                        finish();
                        return true;

                    case R.id.cart:
                        Intent cartPageIntent = new Intent(getApplicationContext(), Cart_Activity.class);
                        startActivity(cartPageIntent);
                        finish();
                        return true;
                }
                return false;
            }
        });

        editProfileBtn = findViewById(R.id.editProfileBtn);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(editProfileIntent);
            }
        });

    }
}