package com.example.mobileprojectwagba.ViewModel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.Repository.OrderHistoryRepo;
import java.util.ArrayList;

public class OrderHistoryViewModel extends ViewModel {

    MutableLiveData<ArrayList<Order>> orders;

    public void init(Context context)
    {
        if(orders != null)
        {
            return;
        }

        orders= OrderHistoryRepo.getInstance().getOrderHistory(context);
    }

    public LiveData<ArrayList<Order>> getOrderHistory()
    {
        return orders;
    }

}
