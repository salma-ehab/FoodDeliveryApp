package com.example.mobileprojectwagba.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobileprojectwagba.Adapters.RestaurantAdapter;
import com.example.mobileprojectwagba.Models.Restaurant;
import com.example.mobileprojectwagba.R;
import com.example.mobileprojectwagba.ViewModel.RestaurantViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class RestaurantView extends AppCompatActivity implements RestaurantAdapter.OnRestaurantListener {


    RecyclerView recyclerView;
    RestaurantAdapter restaurantAdapter;
    RestaurantViewModel restaurantViewModel;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_view);

        Toast.makeText(getApplicationContext(), getResources().getString(R.string.loadingToast), Toast.LENGTH_LONG).show();

        recyclerView = findViewById(R.id.recycleView1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        restaurantViewModel = new ViewModelProvider(this).get(RestaurantViewModel.class);
        restaurantViewModel.init(getApplicationContext());
        restaurantViewModel.getRestaurants().observe(this, new Observer<ArrayList<Restaurant>>() {
            @Override
            public void onChanged(ArrayList<Restaurant> restaurants) {
                restaurantAdapter.notifyDataSetChanged();
            }
        });
        restaurantAdapter = new RestaurantAdapter(getApplicationContext(),restaurantViewModel.getRestaurants().getValue(),this);
        recyclerView.setAdapter(restaurantAdapter);

        //NavigationBar
        bottomNavigationView = findViewById(R.id.bottomNavigationView1);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
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

    //To open dishes activity
    @Override
    public void onRestaurantClicked(int position) {
        Intent intentDishes = new Intent(this, DishesActivity.class);
        Restaurant res = restaurantViewModel.getRestaurants().getValue().get(position);
        intentDishes.putExtra("restaurantName",res.getName());
        Log.d("resName",res.getName());
        startActivity(intentDishes);
        finish();
    }


}