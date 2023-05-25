package com.example.mobileprojectwagba.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileprojectwagba.Models.Order;
import com.example.mobileprojectwagba.R;

import java.util.ArrayList;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.MyViewHolder>{

    Context context;
    ArrayList<Order> orderHistoryList;

    public OrderHistoryAdapter(Context context, ArrayList<Order> orderHistoryList) {
        this.context = context;
        this.orderHistoryList = orderHistoryList;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.history_items,parent,false);
        return new OrderHistoryAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryAdapter.MyViewHolder holder, int position) {

        Order orderHistory = orderHistoryList.get(position);
        holder.ID.setText(orderHistory.getID());
        holder.date.setText(orderHistory.getDate());
        holder.price.setText(String.valueOf(orderHistory.getPrice()));
        holder.status.setText(orderHistory.getStatus());

    }

    @Override
    public int getItemCount() {

        return orderHistoryList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView ID;
        TextView date;
        TextView price;
        TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ID = itemView.findViewById(R.id.orderID);
            date = itemView.findViewById(R.id.date);
            price = itemView.findViewById(R.id.priceHistory);
            status = itemView.findViewById(R.id.status);

        }

    }
}
