package com.example.foodify.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.R;


public class FoodViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
    public TextView txtmenuname;
    public TextView txtmenuprice;
    public TextView txtdesc;
    public ImageView txtmenuimage;
    public Button add_item;
    public CardView cardView;
//    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        txtmenuimage = (ImageView) itemView.findViewById(R.id.food_img);
        txtmenuprice = (TextView) itemView.findViewById(R.id.price);
        txtmenuname = (TextView) itemView.findViewById(R.id.date);
        txtdesc = (TextView) itemView.findViewById(R.id.address);
        add_item = (Button) itemView.findViewById(R.id.add_food);
        cardView=itemView.findViewById(R.id.foodCardView);
        cardView.setOnCreateContextMenuListener(this);

//        itemView.setOnClickListener(this);
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

    }
}
