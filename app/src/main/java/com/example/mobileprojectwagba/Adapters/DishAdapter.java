package com.example.mobileprojectwagba.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mobileprojectwagba.Models.Dish;
import com.example.mobileprojectwagba.NeededFunctions.CreateOrders;
import com.example.mobileprojectwagba.R;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DishAdapter  extends RecyclerView.Adapter<DishAdapter.MyViewHolder>{

    Context context;
    ArrayList<Dish> dishList;


    public DishAdapter(Context context, ArrayList<Dish> dishList) {
        this.context = context;
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dishes_items,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Dish dish = dishList.get(position);
        holder.name.setText(dish.getName());
        Picasso.get().load(dish.getImageLink()).into(holder.imgView);
        holder.description.setText(dish.getDescription());
        holder.price.setText(String.valueOf(dish.getPrice()));
        holder.availability.setText(dish.getAvailability());

        context = holder.itemView.getContext();

        holder.btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(holder.availability.getText().equals("available"))
                {
                    CreateOrders createOrders = new CreateOrders();
                    createOrders.createOrder(context,dish.getName(),dish.getRestaurantName(),dish.getImageLink(),dish.getPrice());
                    Log.d("dishes",dish.getName());
                    Toast.makeText(context, context.getResources().getString(R.string.dishAdded), Toast.LENGTH_LONG).show();
                }

                else
                {
                    Toast.makeText(context, context.getResources().getString(R.string.dishCanNotBeAdded), Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;
        ImageView imgView;
        TextView description;
        TextView price;
        TextView availability;
        Button btnCart;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            imgView = itemView.findViewById(R.id.rImage2);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            availability = itemView.findViewById(R.id.availability);
            btnCart = itemView.findViewById(R.id.cartBtn);

        }

    }

}
