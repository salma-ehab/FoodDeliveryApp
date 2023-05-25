package com.example.mobileprojectwagba.NeededFunctions;

import android.content.Context;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.mobileprojectwagba.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CheckoutUpdateFirebase {

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference getOrder;
    private String user;
    private String orderId;

    public void userConfirmCancelOrder(Context context,String date,String status)
    {
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

                        orderId = childSnapshot.getKey();
                        getOrder.child(orderId).child("Date of Order").setValue(date);
                        getOrder.child(orderId).child("Status").setValue(status);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context,context.getResources().getString(R.string.errorMessage),Toast.LENGTH_SHORT).show();
            }
        });
    }


}
