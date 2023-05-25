package com.example.mobileprojectwagba.Repository;


import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.mobileprojectwagba.Models.Restaurant;
import com.example.mobileprojectwagba.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class RestaurantRepo {

    static RestaurantRepo instance;
    private ArrayList<Restaurant> restaurantList = new ArrayList<>();
    private MutableLiveData<ArrayList<Restaurant>> restaurant = new MutableLiveData<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query getRestaurant;

    public static RestaurantRepo getInstance()
    {
        if (instance == null)
        {
            instance = new RestaurantRepo();
        }

        return instance;
    }


    public MutableLiveData<ArrayList<Restaurant>> getRestaurants(Context context)
    {
        if (restaurantList.size()==0)
        {
            loadRestaurants(context);
        }

        restaurant.setValue(restaurantList);
        return restaurant;
    }

    private void loadRestaurants(Context context) {

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        getRestaurant = myRef.child("Restaurants");

        getRestaurant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren())
                {
                    String restaurantName = snapshot.getKey();
                    String restaurantImage = snapshot.child("Image").getValue(String.class);
                    restaurantList.add(new Restaurant(restaurantName,restaurantImage));
                }

                restaurant.postValue(restaurantList);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();

            }
        });


    }

}
