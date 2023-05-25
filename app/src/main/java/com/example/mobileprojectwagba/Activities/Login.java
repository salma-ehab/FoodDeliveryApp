package com.example.mobileprojectwagba.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileprojectwagba.Models.UserTable;
import com.example.mobileprojectwagba.R;
import com.example.mobileprojectwagba.ViewModel.SignUpLogInViewModel;
import com.example.mobileprojectwagba.ViewModel.UserViewModel;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Intent intentSignUp;
    TextView txtSignUp;
    EditText emailTxt;
    EditText passwordTxt;
    String emailValue;
    String passwordValue;
    Button btnLogin;
    Intent intentHomePage;
    SignUpLogInViewModel signUpLogInViewModel;
    UserViewModel userViewModel;
    UserTable user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //go to signup activity through link
        intentSignUp = new Intent(this, SignUp.class);
        txtSignUp = findViewById(R.id.txtView3);
        String signUp = getResources().getString(R.string.signUp);
        SpannableString spanString = new SpannableString(signUp);

        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(intentSignUp);
                finish();
            }
        };

        spanString.setSpan(clickSpan,20,27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtSignUp.setText(spanString);
        txtSignUp.setMovementMethod(LinkMovementMethod.getInstance());

        //check if user is signed up then go to homepage
        intentHomePage = new Intent(this, RestaurantView.class);

        //view model (hardcoded so that profile can work without signing up)
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(getApplicationContext());

        signUpLogInViewModel = new ViewModelProvider(this).get(SignUpLogInViewModel.class);
        signUpLogInViewModel.init();
        signUpLogInViewModel.getFireBaseUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null)
                {
                    user = new UserTable("18p6253","salma ehab",emailValue,passwordValue,"01036736367");
                    userViewModel.insertUser(user);
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.successfulLogin),Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLogin = findViewById(R.id.btn1);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailTxt = findViewById(R.id.emailogIn);
                passwordTxt = findViewById(R.id.passwordLogin);
                emailValue = emailTxt.getText().toString();
                passwordValue = passwordTxt.getText().toString();

                if(emailValue.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyEmail), Toast.LENGTH_LONG).show();
                    return;
                }

                if(passwordValue.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyPassword),Toast.LENGTH_LONG).show();
                    return;
                }

                signUpLogInViewModel.LogIn(emailValue,passwordValue,getApplicationContext(),intentHomePage);

            }
        });


    }
}