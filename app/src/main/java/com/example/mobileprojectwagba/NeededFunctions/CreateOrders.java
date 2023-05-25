package com.example.mobileprojectwagba.NeededFunctions;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateOrders {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String orderID;
    private String lastKey;
    private String lastItemKey;
    private String date;
    private String itemID;
    private int itemIDNumber;
    private String user;
    private long prevOrderPrice;
    private boolean prevOrderPriceExists;
    private boolean newUser = true;
    private boolean inCartOrderFound = false;


    public void createOrder(Context context,String name,String restaurantName,String imageLink,long price)
    {
        date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(new Date());
        database = FirebaseDatabase.getInstance();
        user = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());
        myRef = database.getReference();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                //first ever order
                if(!snapshot.hasChild("Orders"))
                {
                    orderID = "Order1";
                    itemIDNumber = 1;
                    Order newOrder = new Order(orderID,user,date,(long)0, "InCart");
                    myRef.child("Orders").child(newOrder.getID()).child("User").setValue(newOrder.getUser());
                    myRef.child("Orders").child(newOrder.getID()).child("Date of Order").setValue(newOrder.getDate());
                    myRef.child("Orders").child(newOrder.getID()).child("Price").setValue(newOrder.getPrice());
                    myRef.child("Orders").child(newOrder.getID()).child("Status").setValue(newOrder.getStatus());
                }


                //add items to order
                else
                {
                    for (DataSnapshot childSnapshot: snapshot.child("Orders").getChildren())
                    {
                        if(childSnapshot.child("User").getValue(String.class).equals(user))
                        {
                            newUser = false;

                            //updateItemNumber
                            if (childSnapshot.child("Status").getValue(String.class).equals("InCart"))
                            {
                                inCartOrderFound = true;
                                lastKey = childSnapshot.getKey();
                                int prevIDNUmber = Integer.valueOf(lastKey.substring(lastKey.length() - 1));
                                orderID = "Order" + String.valueOf(prevIDNUmber);


                                for (DataSnapshot childSnapshot2 : childSnapshot.child("Items").getChildren()) {
                                    lastItemKey = childSnapshot2.getKey();
                                }
                                int prevItemIDNUmber = Integer.valueOf(lastItemKey.substring(lastItemKey.length() - 1));
                                itemIDNumber = prevItemIDNUmber + 1;
                            }
                        }

                    }
                }

                //Create New Order
                if (newUser && snapshot.hasChild("Orders") || (!inCartOrderFound && snapshot.hasChild("Orders")))
                {
                    itemIDNumber = 1;

                    for(DataSnapshot childSnapshot22: snapshot.child("Orders").getChildren())
                    {
                        lastKey = childSnapshot22.getKey();
                    }

                    int prevIDNUmber = Integer.valueOf(lastKey.substring(lastKey.length() - 1));
                    int newIDNumber = prevIDNUmber+1;
                    orderID = "Order"+String.valueOf(newIDNumber);

                    Order newOrder = new Order(orderID,user,date,(long)0, "InCart");
                    myRef.child("Orders").child(newOrder.getID()).child("User").setValue(newOrder.getUser());
                    myRef.child("Orders").child(newOrder.getID()).child("Date of Order").setValue(newOrder.getDate());
                    myRef.child("Orders").child(newOrder.getID()).child("Price").setValue(newOrder.getPrice());
                    myRef.child("Orders").child(newOrder.getID()).child("Status").setValue(newOrder.getStatus());
                }

                //Items inside order
                Log.d("itemIdNUmber",String.valueOf(itemIDNumber));
                itemID = "Item"+String.valueOf(itemIDNumber);
                myRef.child("Orders").child(orderID).child("Items").child(itemID).child("Name").setValue(name);
                myRef.child("Orders").child(orderID).child("Items").child(itemID).child("Image").setValue(imageLink);
                myRef.child("Orders").child(orderID).child("Items").child(itemID).child("Price").setValue(price);
                myRef.child("Orders").child(orderID).child("Items").child(itemID).child("RestaurantName").setValue(restaurantName);
                myRef.child("Orders").child(orderID).child("Items").child(itemID).child("Quantity").setValue((long)1);

                //Update order price
                prevOrderPriceExists = snapshot.child("Orders").child(orderID).child("Price").exists();
                if(!prevOrderPriceExists)
                {
                    prevOrderPrice = 0;
                }
                else
                {
                    prevOrderPrice = snapshot.child("Orders").child(orderID).child("Price").getValue(Long.class);
                }
                myRef.child("Orders").child(orderID).child("Price").setValue(prevOrderPrice+price);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
