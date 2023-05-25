package com.example.mobileprojectwagba.ViewModel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mobileprojectwagba.Models.CartItem;
import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.Repository.CartRepo;
import java.util.ArrayList;

public class CartViewModel extends ViewModel {

    MutableLiveData<ArrayList<CartItem>> cartItems;
    MutableLiveData<Order> orders;


    public void init(Context context)
    {
        if(cartItems != null)
        {
            return;
        }

        cartItems= CartRepo.getInstance().getCartItemsAndOrder(context);
        orders=CartRepo.getInstance().getOrder();
    }

    public LiveData<ArrayList<CartItem>> getCartItemsandOrder()
    {
        return cartItems;
    }

    public LiveData<Order> getOrder()
    {
        return orders;
    }
}
