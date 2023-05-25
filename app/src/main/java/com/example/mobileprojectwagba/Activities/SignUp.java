package com.example.mobileprojectwagba.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.util.Patterns;
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

public class SignUp extends AppCompatActivity {

    Intent intentLogin;
    TextView txtLogIn;
    EditText IDTxt;
    EditText nameTxt;
    EditText emailTxt;
    EditText passwordTxt;
    EditText phoneNumberTxt;
    String IDValue;
    String nameValue;
    String emailValue;
    String passwordValue;
    String phoneNumberValue;
    String phoneRegularExpression;
    Button btnSignUp;
    SignUpLogInViewModel signUpLogInViewModel;
    UserViewModel userViewModel;
    UserTable user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //go to Login activity through link
        intentLogin = new Intent(this, Login.class);
        txtLogIn = findViewById(R.id.txtView3);
        String logIn = getResources().getString(R.string.login);
        SpannableString spanString = new SpannableString(logIn);

        ClickableSpan clickSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                startActivity(intentLogin);
                finish();
            }
        };

        spanString.setSpan(clickSpan,16,22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtLogIn.setText(spanString);
        txtLogIn.setMovementMethod(LinkMovementMethod.getInstance());

        //view model
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.init(getApplicationContext());


        //signUp user
        signUpLogInViewModel = new ViewModelProvider(this).get(SignUpLogInViewModel.class);
        signUpLogInViewModel.init();
        signUpLogInViewModel.getFireBaseUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null)
                {
                    insertUser();
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.successfulSignUp),Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDTxt = findViewById(R.id.IDSignUp);
                nameTxt = findViewById(R.id.nameSignUp);
                emailTxt = findViewById(R.id.emailSignUp);
                passwordTxt = findViewById(R.id.passwordSignUp);
                phoneNumberTxt = findViewById(R.id.mobileSignUp);

                IDValue = IDTxt.getText().toString();
                nameValue = nameTxt.getText().toString();
                emailValue = emailTxt.getText().toString();
                passwordValue = passwordTxt.getText().toString();
                phoneNumberValue = phoneNumberTxt.getText().toString();
                phoneRegularExpression = "^01[0125][0-9]{8}$";

                if(IDValue.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyID), Toast.LENGTH_LONG).show();
                    return;
                }

                if (userViewModel.getUserID(IDValue)!=null)
                {
                    if(!userViewModel.getUserID(IDValue).isEmpty())
                    {
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.failedSignUpIdAlreadyExists), Toast.LENGTH_LONG).show();
                        return;
                    }
                }


                if(nameValue.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyName), Toast.LENGTH_LONG).show();
                    return;
                }

                if(phoneNumberValue.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.emptyPhoneNumber), Toast.LENGTH_LONG).show();
                    return;
                }

                if (!((phoneNumberValue).matches(phoneRegularExpression)))
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.wrongPhoneNumber), Toast.LENGTH_LONG).show();
                    return;
                }

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

                signUpLogInViewModel.signUp(emailValue,passwordValue,getApplicationContext(),intentLogin);
            }
        });
    }

    public void insertUser()
    {
        user = new UserTable(IDValue,nameValue,emailValue,passwordValue,phoneNumberValue);
        Log.d("userEmail",user.getEmail());
        userViewModel.insertUser(user);
    }

}