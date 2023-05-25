package com.example.mobileprojectwagba.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class OrderHistoryRepo {

    static OrderHistoryRepo instance;
    private ArrayList<Order> orderList = new ArrayList<>();
    private MutableLiveData<ArrayList<Order>> orders = new MutableLiveData<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query getOrder;
    private String user;

    public static OrderHistoryRepo getInstance()
    {
        if (instance == null)
        {
            instance = new OrderHistoryRepo();
        }

        return instance;
    }

    public MutableLiveData<ArrayList<Order>> getOrderHistory (Context context)
    {
        orderList.clear();
        loadOrderHistory(context);
        orders.setValue(orderList);
        return orders;
    }

    private void loadOrderHistory(Context context)
    {
        orderList.clear();
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference();
        getOrder = myRef.child("Orders");
        user = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    boolean orderStatusExists = childSnapshot.child("Status").exists();
                    if(!orderStatusExists)
                    {
                        Log.d("StatusState","Entered here");
                        return;
                    }

                    if(childSnapshot.child("User").getValue(String.class).equals(user))
                    {
                        String orderID = childSnapshot.getKey();
                        String orderDate = childSnapshot.child("Date of Order").getValue(String.class);
                        long orderPrice = childSnapshot.child("Price").getValue(Long.class);
                        String orderStatus = childSnapshot.child("Status").getValue(String.class);

                        orderList.add(new Order(orderID,user,orderDate,orderPrice,orderStatus));
                    }
                }
                orders.postValue(orderList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
