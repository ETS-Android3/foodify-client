package com.example.foodify.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodify.Interface.ItemClickListener;
import com.example.foodify.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.travijuu.numberpicker.library.NumberPicker;


public class FoodViewHolder extends RecyclerView.ViewHolder {
    public TextView txtmenuname;
    public TextView txtmenuprice;
    public TextView txtdesc;
    public ImageView txtmenuimage;
    public Button add_item;
//    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        txtmenuimage=(ImageView)itemView.findViewById(R.id.food_img);
        txtmenuprice=(TextView)itemView.findViewById(R.id.food_price);
        txtmenuname=(TextView)itemView.findViewById(R.id.food_name);
      txtdesc=(TextView)itemView.findViewById(R.id.textView2);
        add_item=(Button) itemView.findViewById(R.id.add_food);

//        itemView.setOnClickListener(this);
    }

//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }
////
//    @Override
//    public void onClick(View v) {
//        itemClickListener.onClick(v, getAdapterPosition(), false);
//    }


}
