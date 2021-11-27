package com.example.foodify.ViewHolder;

import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.foodify.Interface.ItemClickListener;
import com.example.foodify.R;


public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView txtmenuname;
    public TextView txtmenudesc;
    public ImageView txtmenuimage;
    private ItemClickListener itemClickListener;
    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtmenuimage=(ImageView)itemView.findViewById(R.id.category_img);
        txtmenudesc=(TextView)itemView.findViewById(R.id.category_desc);
        txtmenuname=(TextView)itemView.findViewById(R.id.category_name);
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
}
