package com.example.mobileprojectwagba.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.*;

import com.example.mobileprojectwagba.NeededFunctions.CheckoutUpdateFirebase;
import com.example.mobileprojectwagba.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

public class Checkout extends AppCompatActivity {

    RadioGroup deliveryGate;
    RadioGroup deliveryTime;
    RadioButton twelveBtn;
    RadioButton threeBtn;
    String dateNow;
    String currentHour;
    Button btnConfirm;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        dateNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
        btnConfirm = findViewById(R.id.confirmBtn);
        btnCancel = findViewById(R.id.cancelBtn);
        Intent homePageIntent = new Intent(getApplicationContext(), RestaurantView.class);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                deliveryGate = findViewById(R.id.DeliverGate);
                deliveryTime = findViewById(R.id.DeliverTime);
                twelveBtn = findViewById(R.id.twelveNoon);
                threeBtn = findViewById(R.id.threePM);

                if (deliveryGate.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.noDeliveryGateSelected),Toast.LENGTH_LONG).show();
                    return;
                }

                if (deliveryTime.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.noDeliveryTimeSelected),Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d("time",String.valueOf(dateNow));
                currentHour = dateNow.substring(11,13);

                if (twelveBtn.isChecked())
                {
                    if (Integer.valueOf(currentHour)>=10)
                    {

                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.orderAfter10),Toast.LENGTH_LONG).show();
                        return;
                    }

                }

                else if (threeBtn.isChecked())
                {
                    if (Integer.valueOf(currentHour)>=13)
                    {

                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.orderAfter1),Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                CheckoutUpdateFirebase userConfirmOrder = new CheckoutUpdateFirebase();
                userConfirmOrder.userConfirmCancelOrder(getApplicationContext(),dateNow,"Ordered");
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.confirmOrder),Toast.LENGTH_LONG).show();

                //delay moving to homepage
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(homePageIntent);
                        finish();
                    }
                }, 3000);
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckoutUpdateFirebase userConfirmOrder = new CheckoutUpdateFirebase();
                userConfirmOrder.userConfirmCancelOrder(getApplicationContext(),dateNow,"Canceled");
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.cancelOrder),Toast.LENGTH_LONG).show();

                //delay moving to homepage
                Handler h = new Handler();
                h.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(homePageIntent);
                        finish();
                    }
                }, 3000);
            }
        });
    }
}