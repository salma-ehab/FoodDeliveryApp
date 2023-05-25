package com.example.mobileprojectwagba.Repository;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.mobileprojectwagba.Models.Dish;
import com.example.mobileprojectwagba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class DishRepo {

    static DishRepo instance;
    private ArrayList<Dish> dishList = new ArrayList<>();
    private MutableLiveData<ArrayList<Dish>> dish = new MutableLiveData<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query getRestaurant;

    public static DishRepo getInstance()
    {
        if (instance == null)
        {
            instance = new DishRepo();
        }

        return instance;
    }


    public MutableLiveData<ArrayList<Dish>> getDishes(Context context,Intent intent)
    {

        loadDishes(context,intent);
        dish.setValue(dishList);
        return dish;
    }

    private void loadDishes(Context context,Intent intent) {

        dishList.clear();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        getRestaurant = myRef.child("Restaurants");

        getRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {

                Intent intentDishes = intent;
                String restaurantName = intentDishes.getStringExtra("restaurantName");
                Log.d("resName",restaurantName);

                for (DataSnapshot snapshot : datasnapshot.getChildren()) {

                    if (snapshot.getKey().equals(restaurantName) )
                    {
                        Log.d("dish","voila");
                        for (int i = 1;i<=4;i++)
                        {
                            String dishName = snapshot.child("Dishes").child("Dish"+String.valueOf(i)).child("Name").getValue(String.class);
                            String dishImage = snapshot.child("Dishes").child("Dish"+String.valueOf(i)).child("Image").getValue(String.class);
                            String dishDescription = snapshot.child("Dishes").child("Dish"+String.valueOf(i)).child("Description").getValue(String.class);
                            Long dishPrice = snapshot.child("Dishes").child("Dish"+String.valueOf(i)).child("Price").getValue(Long.class);
                            String dishAvailability = snapshot.child("Dishes").child("Dish"+String.valueOf(i)).child("Availability").getValue(String.class);

                            Log.d("dishDesc",dishDescription);
                            dishList.add(new Dish(dishName,dishImage,dishDescription,dishPrice,dishAvailability, restaurantName));

                        }
                    }

                }

              dish.postValue(dishList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();

            }
        });


    }



}
