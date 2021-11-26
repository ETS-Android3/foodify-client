package com.example.foodify.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Model.CategoryById;
import com.example.foodify.Model.FoodItem;
import com.example.foodify.R;
import com.example.foodify.Utils.DBHelper;
import com.example.foodify.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    CategoryById array;
    ArrayList<FoodItem>item;
    private Context context;
    DBHelper DB;


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
        holder.txtmenuId.setText(item.get(position).getId());
        holder.txtmenudesc.setText(item.get(position).getDescription());
        DB=new DBHelper(context);
        Picasso.with(context).load(item.get(position).getImage()).into(holder.txtmenuimage);
        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id= Integer.parseInt(item.get(position).getId());
                int quantity= item.get(position).getPrice();
                Boolean checkinsert=DB.insertData(id,quantity);
                if(checkinsert)
                {
                    Toast.makeText(context, "Added Peacefuly", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, "Not Added", Toast.LENGTH_SHORT).show();
                }


            }
        });
//        holder.txtmenuname.setText(item.getItems().get(position).getName());
//        holder.txtmenudesc.setText(item.getItems().get(position).getDescription());
//        Picasso.with(context).load(item.getItems().get(position).getImage()).into(holder.txtmenuimage);



    }

    @Override
    public int getItemCount() {
        return item.size();
    }
}
