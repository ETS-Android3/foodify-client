package com.example.foodify.Activity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodify.Adapter.FoodAdapter;
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
    String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlzU3RhZmYiOmZhbHNlLCJ1c2VySWQiOjF9LCJpYXQiOjE2MzgxMzYwMDF9._VH8jLlncuo3_3D8sOztquW55YNtUeWSHtCCE7m6g1I";


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
        Cursor items=DB.getCartData();
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
                    while (items.moveToNext()) {
                        CartSent s = new CartSent();
                        s.setItemId(items.getString(items.getColumnIndex("itemId")));
                        s.setQuantity(Integer.parseInt(items.getString(items.getColumnIndex("quantity"))));
                        cartSents.add(s);

                    }

                    cartItemSents.setOrderItem(cartSents);
                    cartItemSents.setPrice(0);
                    cartItemSents.setCalories(1);
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
                Toast.makeText(Cart.this, "Order Placed", Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<FoodItem>cartItems=new ArrayList<FoodItem>();
//        Cursor items=DB.getCartData();
        if(items.getCount()>0) {
            while (items.moveToNext()) {
                FoodItem food = new FoodItem();
                food.setId(items.getString(items.getColumnIndex("itemId")));
                food.setCalories(0);
//            food.setCategoryId();
                food.setDescription(items.getString(items.getColumnIndex("description")));
                food.setImage(items.getString(items.getColumnIndex("image")));
                food.setPrice(Integer.parseInt(items.getString(items.getColumnIndex("price"))));
                food.setName(items.getString(items.getColumnIndex("name")));
                cartItems.add(food);

            }
            recycler_cart.setAdapter(new FoodAdapter(cartItems));
        }

    }
}
