package com.example.mobileprojectwagba.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.mobileprojectwagba.Adapters.CartAdapter;
import com.example.mobileprojectwagba.Models.CartItem;
import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.R;
import com.example.mobileprojectwagba.ViewModel.CartViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;

public class Cart_Activity extends AppCompatActivity {


    RecyclerView recyclerView;
    CartAdapter cartAdapter;
    CartViewModel cartViewModel;
    BottomNavigationView bottomNavigationView;
    Button checkoutBtn;
    TextView orderTotalBeforeTax;
    TextView tax;
    TextView orderTotalAfterTax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recycleView3);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderTotalBeforeTax = findViewById(R.id.dishesTotal);
        tax = findViewById(R.id.deliveryPrice);
        tax.setText(getResources().getString(R.string.tax));
        orderTotalAfterTax = findViewById(R.id.orderTotal);

        cartViewModel = new ViewModelProvider(this).get(CartViewModel.class);
        cartViewModel.init(getApplicationContext());
        cartViewModel.getCartItemsandOrder().observe(this, new Observer<ArrayList<CartItem>>() {
            @Override
            public void onChanged(ArrayList<CartItem> cartItems) {
                cartAdapter.notifyDataSetChanged();
            }
        });
        cartAdapter = new CartAdapter(getApplicationContext(),cartViewModel.getCartItemsandOrder().getValue());
        recyclerView.setAdapter(cartAdapter);

        //update Total Price
       cartViewModel.getOrder().observe(this, new Observer<Order>() {
           @Override
           public void onChanged(Order order) {
               orderTotalBeforeTax.setText(String.valueOf((Double.valueOf(order.getPrice()))+" "+getResources().getString(R.string.egyptianCurrency)));
               double tax = 15.0/100.0;
               double orderAfterTax = ((Double.valueOf(order.getPrice()))*(tax))+((Double.valueOf(order.getPrice())));
               orderTotalAfterTax.setText(String.valueOf(orderAfterTax)+" "+getResources().getString(R.string.egyptianCurrency));
           }
       });

        bottomNavigationView = findViewById(R.id.bottomNavigationView3);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.homePage:
                        Intent dishesIntent = new Intent(getApplicationContext(), RestaurantView.class);
                        startActivity(dishesIntent);
                        finish();
                        return true;

                    case R.id.history:
                        Intent historyIntent = new Intent(getApplicationContext(), HistoryActivity.class);
                        startActivity(historyIntent);
                        finish();
                        return true;

                    case R.id.profile:
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profileIntent);
                        finish();
                        return true;
                }
                return false;
            }
        });

        checkoutBtn = findViewById(R.id.cartBtn);
        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checkoutIntent = new Intent(getApplicationContext(), Checkout.class);
                startActivity(checkoutIntent);
                finish();
            }
        });


    }
}