package com.example.mobileprojectwagba.Repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import com.example.mobileprojectwagba.Models.CartItem;
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

public class CartRepo {

    static CartRepo instance;
    private ArrayList<CartItem> cartList = new ArrayList<>();
    private MutableLiveData<ArrayList<CartItem>> cart = new MutableLiveData<>();
    private MutableLiveData<Order> order = new MutableLiveData<>();
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Query getOrder;
    private String user;


    public static CartRepo getInstance()
    {
        if (instance == null)
        {
            instance = new CartRepo();
        }

        return instance;
    }

    public MutableLiveData<ArrayList<CartItem>> getCartItemsAndOrder (Context context)
    {
        cartList.clear();
        loadCartItemsAndOrder(context);
        cart.setValue(cartList);
        return cart;
    }

    public MutableLiveData<Order> getOrder()
    {
        return order;
    }

    private void loadCartItemsAndOrder(Context context)
    {
        cartList.clear();
        order.postValue(new Order("tempID","tempUser","tempDate",(long)0,"tempStatus"));
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference();
        getOrder = myRef.child("Orders");
        user = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Log.d("userNow",user);

        getOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    boolean orderStatusExists = childSnapshot.child("Status").exists();
                    if(!orderStatusExists)
                    {
                        Log.d("StatusState","Entered here");
                        return;
                    }

                    String orderID = childSnapshot.getKey();
                    String orderUser = childSnapshot.child("User").getValue(String.class);
                    String orderDate = childSnapshot.child("Date of Order").getValue(String.class);
                    long orderPrice = childSnapshot.child("Price").getValue(Long.class);
                    String orderStatus = childSnapshot.child("Status").getValue(String.class);


                    if(childSnapshot.child("User").getValue(String.class).equals(user) &&
                            (childSnapshot.child("Status").getValue(String.class).equals("Delivered") ||
                            childSnapshot.child("Status").getValue(String.class).equals("Canceled")||
                             childSnapshot.child("Status").getValue(String.class).equals("Ordered")||
                             childSnapshot.child("Status").getValue(String.class).equals("InProgress")||
                             childSnapshot.child("Status").getValue(String.class).equals("OnRoute")))
                    {
                        cartList.clear();
                        order.postValue(new Order(orderID,orderUser,orderDate,(long)0,orderStatus));
                    }

                    if(childSnapshot.child("User").getValue(String.class).equals(user) &&
                            (childSnapshot.child("Status").getValue(String.class).equals("InCart")))
                    {
                        //load order
                        order.postValue(new Order(orderID,orderUser,orderDate,orderPrice,orderStatus));


                        DataSnapshot dataSnapshot = childSnapshot.child("Items");
                        for (DataSnapshot grandchildSnapshot: dataSnapshot.getChildren())
                        {
                            String itemImage = grandchildSnapshot.child("Image").getValue(String.class);;
                            String itemName =  grandchildSnapshot.child("Name").getValue(String.class);
                            String itemRestaurantName =  grandchildSnapshot.child("RestaurantName").getValue(String.class);
                            long itemPrice,itemQuantity;

                            if(!grandchildSnapshot.child("Price").exists())
                            {
                                itemPrice = 0;
                            }
                            else
                            {
                                itemPrice = grandchildSnapshot.child("Price").getValue(Long.class);
                            }

                            if(!grandchildSnapshot.child("Quantity").exists())
                            {
                                itemQuantity = 0;
                            }
                            else
                            {
                                itemQuantity = grandchildSnapshot.child("Quantity").getValue(Long.class);
                            }

                            Log.d("Quantity",String.valueOf(itemQuantity));


                            cartList.add(new CartItem(itemImage,itemPrice,itemName,itemRestaurantName,itemQuantity));
                        }
                    }
                }
                cart.postValue(cartList);
                Log.d("CartList",String.valueOf(cart));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();
            }
        });

    }


}
