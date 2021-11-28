package com.example.foodify.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Model.CategoryById;
import com.example.foodify.Model.FoodItem;
import com.example.foodify.R;
import com.example.foodify.Utils.DBHelper;
import com.example.foodify.ViewHolder.FoodViewHolder;
import com.squareup.picasso.Picasso;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodViewHolder> {
    CategoryById array;
    ArrayList<FoodItem> item;
    private Context context;
    DBHelper DB;
    int quantity = 0;
    int price = 0;
    ElegantNumberButton elegantNumberButton;

    TextView popupDesc;
    TextView popupTitle;
    TextView popupPrice;

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
        holder.txtmenuname.setText(item.get(position).getName());
        holder.txtmenuprice.setText(Integer.toString(item.get(position).getPrice()) + " ₹");
        holder.txtdesc.setText((item.get(position).getDescription()));
        DB = new DBHelper(context);
        Picasso.with(context).load(item.get(position).getImage()).into(holder.txtmenuimage);
        holder.add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                price = item.get(position).getPrice();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setCancelable(true);
                LayoutInflater inflater = LayoutInflater.from(context);
                View addQuantityDialog = inflater.inflate(R.layout.add_item_quantity, null);
                elegantNumberButton = addQuantityDialog.findViewById(R.id.quantity);
                popupTitle = addQuantityDialog.findViewById(R.id.popup_title);
                popupDesc = addQuantityDialog.findViewById(R.id.item_desc);
                popupPrice = addQuantityDialog.findViewById(R.id.popup_price);

                popupTitle.setText(item.get(position).getName());
                popupDesc.setText(item.get(position).getDescription());
                popupPrice.setText(price+" ₹");

                elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
                    @Override
                    public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                        if(newValue == 0){
                            Toast.makeText(context, "Select atleast one quantity", Toast.LENGTH_SHORT).show();
                        } else {
                            price = (price/oldValue)*newValue;
                            popupPrice.setText(price+" ₹");
                        }
                    }
                });

                builder.setView(addQuantityDialog)
                        .setPositiveButton("Add",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int itemId = Integer.parseInt(item.get(position).getId());
                                        int price = item.get(position).getPrice();
                                        String image = item.get(position).getImage();
                                        String name = item.get(position).getName();
                                        String description = item.get(position).getDescription();
                                        quantity = Integer.parseInt(elegantNumberButton.getNumber());

                                        if(DB.getExistingItem(itemId)) {
                                            DB.updateItem(itemId, quantity);
                                            Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                                        } else {
                                            DB.insertData(itemId, quantity, image, name, description, price);
                                            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
                                        }
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

    }


    //
    @Override
    public int getItemCount() {
        return item.size();
    }

}
