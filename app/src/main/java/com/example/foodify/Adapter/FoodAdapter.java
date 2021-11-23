package com.example.foodify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Model.CategoryById;
import com.example.foodify.Model.FoodItem;
import com.example.foodify.R;
import com.example.foodify.ViewHolder.FoodViewHolder;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    CategoryById array;
    ArrayList<FoodItem>item;
    private Context context;

    public FoodAdapter(CategoryById array) {
        this.array = array;
        item=array.getItems();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item,parent,false);
        context=view.getContext();
        return new FoodViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
//        com.example.foodify.Model.CategoryById item=array.getItems()
        holder.txtmenuname.setText(item.get(position).getName());
//        holder.txtmenuname.setText(item.getItems().get(position).getName());
//        holder.txtmenudesc.setText(item.getItems().get(position).getDescription());
//        Picasso.with(context).load(item.getItems().get(position).getImage()).into(holder.txtmenuimage);



    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
