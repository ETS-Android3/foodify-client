package com.example.foodify.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Interface.ItemClickListener;
import com.example.foodify.Model.CategoryById;
import com.example.foodify.Model.FoodItem;
import com.example.foodify.R;
import com.example.foodify.Utils.DBHelper;
import com.example.foodify.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    CategoryById array;
    ArrayList<FoodItem> item;
    private Context context;
    DBHelper DB;
    int quantity = 0;

    public FoodAdapter(CategoryById array) {
        this.array = array;
        item = array.getItems();


    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        context = view.getContext();
        return new FoodViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
//        com.example.foodify.Model.CategoryById item=array.getItems()
        holder.txtmenuname.setText(item.get(position).getName());
        holder.txtmenuprice.setText(Integer.toString(item.get(position).getPrice()) + " $");
        DB = new DBHelper(context);
        Picasso.with(context).load(item.get(position).getImage()).into(holder.txtmenuimage);
        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setView(R.layout.add_item_quantity)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Toast.makeText(view.getContext(), "Hello", Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }

                                });
                builder.create();
                builder.show();
            }
        });
//        holder.add_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int id= Integer.parseInt(item.get(position).getId());
//                holder.txtquantity.setText(Integer.toString(quantity));
//                Boolean checkinsert=DB.insertData(id,quantity);
//                if(checkinsert)
//                {
//                    Toast.makeText(context, "Added Peacefuly", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//        holder.plus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                quantity++;
//                holder.txtquantity.setText(Integer.toString(quantity));
//            }
//        });
//        holder.minus.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(quantity>0)
//                {
//                     quantity--;
//                     holder.txtquantity.setText(Integer.toString(quantity));
//                }
//            }
//        });
//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onClick(View view, int position, boolean isLongClick) {
//                Toast.makeText(context, "Teri Maa ki chut", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //
    @Override
    public int getItemCount() {
        return item.size();
    }

}
