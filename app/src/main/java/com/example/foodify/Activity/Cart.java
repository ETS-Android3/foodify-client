package com.example.foodify.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Adapter.FoodAdapter;
import com.example.foodify.Common.Common;
import com.example.foodify.Model.AuthRespose;
import com.example.foodify.Model.BadRequestException;
import com.example.foodify.Model.CartItemSent;
import com.example.foodify.Model.CartSent;
import com.example.foodify.Model.FoodItem;
import com.example.foodify.R;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.example.foodify.Utils.DBHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class Cart extends AppCompatActivity {
    DBHelper DB;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface service;
    RecyclerView recycler_cart;
    RecyclerView.LayoutManager layoutManager;
    Button SendCart;
    int price=0;
    int calories=0;
//    String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlzU3RhZmYiOmZhbHNlLCJ1c2VySWQiOjF9LCJpYXQiOjE2MzgxMzYwMDF9._VH8jLlncuo3_3D8sOztquW55YNtUeWSHtCCE7m6g1I";
  String token=Common.token;
    Cursor items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);
        recycler_cart=findViewById(R.id.recycler_food);
        DB=new DBHelper(this);
        recycler_cart.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_cart.setLayoutManager(layoutManager);
        SendCart=findViewById(R.id.sendcart);
         items=DB.getCartData();
        Log.d("Token is", Common.token);
        if(items.getCount()==0)
        {
            SendCart.setVisibility(View.INVISIBLE);
        }
        SendCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               CartItemSent cartItemSents=new CartItemSent();
                ArrayList<CartSent>cartSents=new ArrayList<CartSent>();
                Cursor items=DB.getCartData();
                if(items.getCount()==0) {
                    Toast.makeText(Cart.this, "Please Add Items to Cart", Toast.LENGTH_SHORT).show();

                }

                else {
                    price=calories=0;

                    
                    while (items.moveToNext()) {
                        CartSent s = new CartSent();
                        int quantity=Integer.parseInt(items.getString(items.getColumnIndex("quantity")));
                        s.setItemId(items.getString(items.getColumnIndex("itemId")));
                        s.setQuantity(quantity);
                        cartSents.add(s);

                        price+=Integer.parseInt(items.getString(items.getColumnIndex("price")))*quantity;
                        calories+=Integer.parseInt(items.getString(items.getColumnIndex("calories")))*quantity;
                        Log.d("Price", String.valueOf(price));
                        Log.d("Calories", String.valueOf(calories));

                    }

                    cartItemSents.setOrderItem(cartSents);
                    cartItemSents.setPrice(price);
                    cartItemSents.setCalories(calories);
                    compositeDisposable.add(service.placeorder(cartItemSents, token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::handleResponse, this::handleError)
                    );
                }
            }

            private void handleError(Throwable error) {
                if(error instanceof HttpException){
                    Gson gson = new GsonBuilder().create();
                    try {
                        String errorBody = ((HttpException) error).response().errorBody().string();
                        BadRequestException response = gson.fromJson(errorBody, BadRequestException.class);
                        Toast.makeText(Cart.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Cart.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d("Error",error.getMessage());
                }
            }

            private void handleResponse(AuthRespose authRespose) {
                Intent intent=new Intent(Cart.this,Checkout.class);
                intent.putExtra("totalprice",Integer.toString(price));
                intent.putExtra("totalcalories",Integer.toString(calories));
                startActivity(intent);
            }
        });

        ArrayList<FoodItem>cartItems=new ArrayList<FoodItem>();

        if(items.getCount()>0) {
            while (items.moveToNext()) {
                FoodItem food = new FoodItem();
                food.setId(items.getString(items.getColumnIndex("itemId")));
                food.setCalories(Integer.parseInt(items.getString(items.getColumnIndex("calories"))));
                food.setDescription(items.getString(items.getColumnIndex("description")));
                food.setImage(items.getString(items.getColumnIndex("image")));
                food.setPrice(Integer.parseInt(items.getString(items.getColumnIndex("price"))));
                food.setName(items.getString(items.getColumnIndex("name")));
                cartItems.add(food);
                StringBuilder sb = new StringBuilder();
                int columnsQty = items.getColumnCount();
                for (int idx=0; idx<columnsQty; ++idx) {
                    sb.append(items.getString(idx));
                    if (idx < columnsQty - 1)
                        sb.append("; ");
                }
                Log.v("Print", String.format("Row: %d, Values: %s", items.getPosition(),
                        sb.toString()));
            }

            recycler_cart.setAdapter(new FoodAdapter(cartItems));

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT|ItemTouchHelper.LEFT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        cartItems.remove(viewHolder.getAdapterPosition());
                        removeItem((Integer) viewHolder.itemView.getTag());
//                        if(cartItems.size()==0)
//                            DB.deleteCart();
                        new FoodAdapter(cartItems).notifyDataSetChanged();
                }
            }).attachToRecyclerView(recycler_cart);

        }


    }

    private void removeItem(int tag) {
        DB.removeItem(tag);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getTitle().equals("DELETE"))
        {
            displayMessage("Item Deleted");
            return true;
        }
        else
        return super.onContextItemSelected(item);
    }

    private void displayMessage(String item_deleted) {
        Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show();
    }

}
