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
import com.example.mobileprojectwagba.Adapters.OrderHistoryAdapter;
import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.R;
import com.example.mobileprojectwagba.ViewModel.OrderHistoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    OrderHistoryAdapter orderHistoryAdapter;
    OrderHistoryViewModel orderHistoryViewModel;
    BottomNavigationView bottomNavigationView;
    ArrayList<Order> orders2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        recyclerView = findViewById(R.id.recycleView4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderHistoryViewModel = new ViewModelProvider(this).get(OrderHistoryViewModel.class);
        orderHistoryViewModel.init(getApplicationContext());
        orderHistoryViewModel.getOrderHistory().observe(this, new Observer<ArrayList<Order>>() {
            @Override
            public void onChanged(ArrayList<Order> orders) {
                int index = 0;
                for (int i = orders.size()-1;i>=0;i--)
                {
                    orders2.add(index,orders.get(i));
                    index++;
                }
                orderHistoryAdapter.notifyDataSetChanged();
            }
        });

        orderHistoryAdapter = new OrderHistoryAdapter(getApplicationContext(),orders2);
        recyclerView.setAdapter(orderHistoryAdapter);


        bottomNavigationView = findViewById(R.id.bottomNavigationView4);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.homePage:
                        Intent homePageIntent = new Intent(getApplicationContext(), RestaurantView.class);
                        startActivity(homePageIntent);
                        finish();
                        return true;

                    case R.id.profile:
                        Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(profileIntent);
                        finish();
                        return true;

                    case R.id.cart:
                        Intent cartIntent = new Intent(getApplicationContext(), Cart_Activity.class);
                        startActivity(cartIntent);
                        finish();
                        return true;
                }
                return false;
            }
        });
    }
}