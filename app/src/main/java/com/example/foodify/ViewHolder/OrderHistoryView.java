package com.example.foodify.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.R;

public class OrderHistoryView extends RecyclerView.ViewHolder {
    public  TextView date;
    public  TextView address;
    public  TextView price;

    public OrderHistoryView(@NonNull View itemView) {
        super(itemView);
        date=itemView.findViewById(R.id.date);
        address=itemView.findViewById(R.id.address);
        price=itemView.findViewById(R.id.price);

    }

}
