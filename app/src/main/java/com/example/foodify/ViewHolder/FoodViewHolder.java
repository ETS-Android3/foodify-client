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
    public TextView txtmenudesc;
    public TextView txtmenuId;
    public ImageView txtmenuimage;
    public FloatingActionButton add_item;
    private ItemClickListener itemClickListener;

    public FoodViewHolder(@NonNull View itemView) {
        super(itemView);
        txtmenuimage=(ImageView)itemView.findViewById(R.id.food_img);
        txtmenudesc=(TextView)itemView.findViewById(R.id.food_desc);
        txtmenuname=(TextView)itemView.findViewById(R.id.food_name);
        txtmenuId=(TextView)itemView.findViewById(R.id.food_id);
        add_item=itemView.findViewById(R.id.add_food);
        NumberPicker numberPicker = (NumberPicker) itemView.findViewById(R.id.number_picker);
        numberPicker.setMax(15);
        numberPicker.setMin(5);
        numberPicker.setUnit(2);
        numberPicker.setValue(10);
        itemView.setOnClickListener(this);
//        itemView.setOnCreateContextMenuListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);

    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        menu.setHeaderTitle("Select Action");
//        menu.add(0,0,getAdapterPosition(), Common.UPDATE);
//        menu.add(0,1,getAdapterPosition(), Common.DELETE);
//    }
}
