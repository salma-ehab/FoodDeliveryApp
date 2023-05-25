package com.example.mobileprojectwagba.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobileprojectwagba.Models.Restaurant;
import com.example.mobileprojectwagba.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.MyViewHolder> {

    Context context;
    ArrayList<Restaurant> restaurantList;
    OnRestaurantListener mOnRestaurantListner;

    public RestaurantAdapter(Context context, ArrayList<Restaurant> restaurantList,OnRestaurantListener onRestaurantListener) {
        this.context = context;
        this.restaurantList = restaurantList;
        this.mOnRestaurantListner = onRestaurantListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.restaurant_items,parent,false);

        return new MyViewHolder(view,mOnRestaurantListner);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Restaurant restaurant = restaurantList.get(position);
        Picasso.get().load(restaurant.getImageLink()).into(holder.imgView);

    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        ImageView imgView;
        OnRestaurantListener onRestaurantListener;
        public MyViewHolder(@NonNull View itemView,OnRestaurantListener onRestaurantListener) {
            super(itemView);
            imgView = itemView.findViewById(R.id.rImage);
            this.onRestaurantListener = onRestaurantListener;
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            onRestaurantListener.onRestaurantClicked(getAdapterPosition());
        }
    }

    public interface OnRestaurantListener
    {
        void onRestaurantClicked(int position);
    }


}
