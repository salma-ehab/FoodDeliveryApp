package com.example.mobileprojectwagba.ViewModel;

import android.content.Context;
import android.content.Intent;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mobileprojectwagba.Models.CartItem;
import com.example.mobileprojectwagba.Models.Dish;
import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.Repository.DishRepo;
import java.util.ArrayList;

public class DishViewModel extends ViewModel {

    MutableLiveData<ArrayList<Dish>> dishes;

    public void init(Context context, Intent intent)
    {
        if(dishes != null)
        {
            return;
        }

        dishes= DishRepo.getInstance().getDishes(context,intent);
    }

    public LiveData<ArrayList<Dish>> getDishes()
    {
        return dishes;
    }


}
