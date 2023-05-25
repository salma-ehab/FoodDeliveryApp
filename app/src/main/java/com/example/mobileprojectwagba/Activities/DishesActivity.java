package com.example.mobileprojectwagba.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import com.example.mobileprojectwagba.Adapters.DishAdapter;
import com.example.mobileprojectwagba.Models.Dish;
import com.example.mobileprojectwagba.R;
import com.example.mobileprojectwagba.ViewModel.DishViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import java.util.ArrayList;

public class DishesActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    DishAdapter dishAdapter;
    DishViewModel dishViewModel;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dishes);

        recyclerView = findViewById(R.id.recycleView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent dishIntent = getIntent();

        dishViewModel = new ViewModelProvider(this).get(DishViewModel.class);
        dishViewModel.init(getApplicationContext(),dishIntent);
        dishViewModel.getDishes().observe(this, new Observer<ArrayList<Dish>>() {
            @Override
            public void onChanged(ArrayList<Dish> dishes) {
               dishAdapter.notifyDataSetChanged();
               Log.d("dishObserver",dishes.toString());
            }
        });
        dishAdapter = new DishAdapter(getApplicationContext(),dishViewModel.getDishes().getValue());
        recyclerView.setAdapter(dishAdapter);


        //Navigation bar
        bottomNavigationView = findViewById(R.id.bottomNavigationView2);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.homePage:
                        Intent homePageIntent = new Intent(getApplicationContext(), RestaurantView.class);
                        startActivity(homePageIntent);
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