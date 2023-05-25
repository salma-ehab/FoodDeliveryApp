package com.example.mobileprojectwagba.NeededFunctions;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.mobileprojectwagba.Models.CartItem;
import com.example.mobileprojectwagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateAndDeleteFirebase {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference getOrder;
    private String user;
    private String orderID;
    private String itemID;
    private String newItemID;
    private long prevOrderPrice;


    public void updateFirebase(Context context,CartItem cartItem)
    {
        Log.d("ButtonClicked","1");
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference();
        getOrder = myRef.child("Orders");
        user =  String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    if(childSnapshot.child("User").getValue(String.class).equals(user)
                            && childSnapshot.child("Status").getValue(String.class).equals("InCart"))
                    {
                        DataSnapshot dataSnapshot = childSnapshot.child("Items");
                        prevOrderPrice = 0;
                        for (DataSnapshot grandChildSnapshot: dataSnapshot.getChildren())
                        {
                            itemID = grandChildSnapshot.getKey();
                            if(grandChildSnapshot.child("Name").getValue(String.class).equals(cartItem.getName()))
                            {

                                newItemID = grandChildSnapshot.getKey();
                                orderID = childSnapshot.getKey();
                                getOrder.child(orderID).child("Items").child(newItemID).child("Quantity").setValue(cartItem.getQuantity());
                                getOrder.child(orderID).child("Items").child(newItemID).child("Price").setValue(cartItem.getPrice());

                            }
                            if(!itemID.equals(newItemID))
                            {

                                prevOrderPrice+= dataSnapshot.child(itemID).child("Price").getValue(Long.class);

                            }

                            else
                            {
                                prevOrderPrice+= cartItem.getPrice();
                            }

                        }
                    }
                }

                Log.d("checkCart",String.valueOf(prevOrderPrice));
                getOrder.child(orderID).child("Price").setValue(prevOrderPrice);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteFirebase(Context context,CartItem cartItem)
    {
        database = FirebaseDatabase.getInstance();
        myRef =  database.getReference();
        getOrder = myRef.child("Orders");
        user =  String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot childSnapshot: snapshot.getChildren())
                {
                    if (childSnapshot.child("Status").getValue() == null)
                    {
                        break;
                    }
                    if(childSnapshot.child("User").getValue(String.class).equals(user)
                            && childSnapshot.child("Status").getValue(String.class).equals("InCart"))
                    {
                        DataSnapshot dataSnapshot = childSnapshot.child("Items");
                        prevOrderPrice = 0;
                        for (DataSnapshot grandChildSnapshot: dataSnapshot.getChildren())
                        {
                            itemID = grandChildSnapshot.getKey();
                            if(grandChildSnapshot.child("Name").getValue(String.class).equals(cartItem.getName()))
                            {
                                newItemID = grandChildSnapshot.getKey();
                                orderID = childSnapshot.getKey();
                                grandChildSnapshot.getRef().setValue(null);
                            }

                            if( dataSnapshot.child(itemID).child("Price").exists())
                            {
                                if(!itemID.equals(newItemID))
                                {
                                    prevOrderPrice+= dataSnapshot.child(itemID).child("Price").getValue(Long.class);

                                }
                            }

                            else
                            {
                                prevOrderPrice-= cartItem.getPrice();
                            }
                        }
                    }
                }
                Log.d("checkCart",String.valueOf(prevOrderPrice));
                getOrder.child(orderID).child("Price").setValue(prevOrderPrice);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();
            }
        });


    }


}
