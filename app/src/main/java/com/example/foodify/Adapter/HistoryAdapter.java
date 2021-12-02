package com.example.foodify.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Model.Category;
import com.example.foodify.Model.OrderHistory;
import com.example.foodify.Model.OrderHistoryStatus;

import com.example.foodify.R;
import com.example.foodify.ViewHolder.CategoryViewHolder;
import com.example.foodify.ViewHolder.OrderHistoryView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<OrderHistoryView> {
    public ArrayList<OrderHistory>orders;
    private Context context;

    public HistoryAdapter(ArrayList<OrderHistory> orders) {
        this.orders= orders;

    }

    @NonNull
    @Override
    public OrderHistoryView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_item,parent,false);
        context=view.getContext();
        return new OrderHistoryView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryView holder, int position) {
        OrderHistory history=orders.get(position);
        holder.date.setText(history.getStatus().getDelivered_time());
        holder.address.setText("BITS Pilani Hyderabad Campus, Secunderabad, Telangana 500078");
        holder.price.setText(history.getPrice() + " ₹");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }
}
