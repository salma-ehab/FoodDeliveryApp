package com.example.mobileprojectwagba.ViewModel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mobileprojectwagba.Models.Restaurant;
import com.example.mobileprojectwagba.Repository.RestaurantRepo;

import java.util.ArrayList;

public class RestaurantViewModel extends ViewModel {

    MutableLiveData<ArrayList<Restaurant>> restaurants;

    public void init(Context context)
    {
        if(restaurants != null)
        {
            return;
        }

        restaurants= RestaurantRepo.getInstance().getRestaurants(context);
    }

    public LiveData<ArrayList<Restaurant>> getRestaurants()
    {
        return restaurants;
    }


}
