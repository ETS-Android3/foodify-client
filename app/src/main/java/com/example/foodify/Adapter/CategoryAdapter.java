package com.example.foodify.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Activity.AllCategories;
import com.example.foodify.Interface.ItemClickListener;
import com.example.foodify.Model.Category;
import com.example.foodify.R;
import com.example.foodify.ViewHolder.CategoryViewHolder;
import com.squareup.picasso.Picasso;
import com.example.foodify.Activity.CategoryFoodItems;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    List<Category>categoryList;
    private Context context;

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;

    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        context=view.getContext();
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category item=categoryList.get(position);

        holder.txtmenuname.setText(item.getName());
        holder.txtmenudesc.setText(item.getDescription());
        Picasso.with(context).load(item.getImage()).into(holder.txtmenuimage);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Log.d("Category",item.getId());
                Intent list=new Intent(context, CategoryFoodItems.class);
                list.putExtra("categoryId",item.getId());
                context.startActivity(list);


            }
        });
//      Picasso.with(this).load()



    }


    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
