package com.example.mobileprojectwagba.Adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobileprojectwagba.Models.CartItem;
import com.example.mobileprojectwagba.NeededFunctions.UpdateAndDeleteFirebase;
import com.example.mobileprojectwagba.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder>{

    Context context;
    ArrayList<CartItem> cartList;

    public CartAdapter(Context context, ArrayList<CartItem> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_items,parent,false);
        return new CartAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Resources resource = holder.itemView.getContext().getResources();
        CartItem cart = cartList.get(position);
        Picasso.get().load(cart.getImageLink()).into(holder.imgView);
        holder.price.setText(String.valueOf(cart.getPrice())+" "+resource.getString(R.string.egyptianCurrency));
        holder.name.setText(cart.getName());
        holder.quantityText.setText(String.valueOf(cart.getQuantity()));

        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cart.getQuantity()>1)
                {
                    long originalItemPrice = cart.getPrice() / cart.getQuantity();
                    cart.setQuantity(cart.getQuantity()-1);
                    cart.setPrice(originalItemPrice*cart.getQuantity());
                    holder.quantityText.setText(String.valueOf(cart.getQuantity()));

                    UpdateAndDeleteFirebase updateFirebase = new UpdateAndDeleteFirebase();
                    updateFirebase.updateFirebase(context,cart);
                }
            }
        });

        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long originalItemPrice = cart.getPrice() / cart.getQuantity();
                cart.setQuantity(cart.getQuantity()+1);
                cart.setPrice(originalItemPrice*cart.getQuantity());
                holder.quantityText.setText(String.valueOf(cart.getQuantity()));

                UpdateAndDeleteFirebase updateFirebase = new UpdateAndDeleteFirebase();
                updateFirebase.updateFirebase(context,cart);
            }
        });

        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateAndDeleteFirebase deleteFirebase = new UpdateAndDeleteFirebase();
                deleteFirebase.deleteFirebase(context,cart);
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });
    }


    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgView;
        TextView price;
        TextView name;
        Button btnPlus;
        Button btnMinus;
        TextView quantityText;
        TextView deleteItem;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
              imgView = itemView.findViewById(R.id.rImage3);
              price = itemView.findViewById(R.id.priceHolder);
              name = itemView.findViewById(R.id.dishNameCart);
              btnPlus = itemView.findViewById(R.id.plusBtn);
              btnMinus = itemView.findViewById(R.id.minusBtn);
              quantityText = itemView.findViewById(R.id.numberClicked);
              deleteItem = itemView.findViewById(R.id.txtViewDeleteIcon);

        }

    }
}
