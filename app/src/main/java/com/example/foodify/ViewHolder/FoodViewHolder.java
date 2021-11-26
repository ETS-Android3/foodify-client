package com.example.foodify.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodify.Interface.ItemClickListener;
import com.example.foodify.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travijuu.numberpicker.library.NumberPicker;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtmenuname;
    public TextView txtmenuprice;
    public TextView txtquantity;
    public ImageView txtmenuimage;
    public ImageView plus;
    public ImageView minus;
    public FloatingActionButton add_item;
    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        txtmenuimage=(ImageView)itemView.findViewById(R.id.food_img);
        txtmenuprice=(TextView)itemView.findViewById(R.id.food_price);
        txtmenuname=(TextView)itemView.findViewById(R.id.food_name);
        txtquantity=(TextView)itemView.findViewById(R.id.food_quantity);
        add_item=itemView.findViewById(R.id.add_food);
        plus=itemView.findViewById(R.id.plus);
        minus=itemView.findViewById(R.id.minus);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }


}
