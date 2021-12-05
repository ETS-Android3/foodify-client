package com.example.foodify.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodify.Common.Common;
import com.example.foodify.Model.AuthRespose;
import com.example.foodify.Model.BadRequestException;
import com.example.foodify.Model.CartItemSent;
import com.example.foodify.Model.CartSent;
import com.example.foodify.Model.User;
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

public class Checkout extends AppCompatActivity {
    int totalprice = 0;
    int totalcalories = 0;
    TextView Price;
    TextView Calories;
    TextView address;
    TextView userName;
    Button place;

    String token = Common.token;
    Cursor items;

    DBHelper DB;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);
        DB = new DBHelper(this);
        items = DB.getCartData();

        compositeDisposable.add(service.userData(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError));
        userName = findViewById(R.id.orderingFor);
        address = findViewById(R.id.addresshome);
        place = findViewById(R.id.placeorder);
        Calories = (TextView) findViewById(R.id.totalCalories);
        Price = (TextView) findViewById(R.id.totalPrice);
        CartItemSent cartItemSents = new CartItemSent();
        ArrayList<CartSent> cartSents = new ArrayList<CartSent>();
        Cursor items = DB.getCartData();
        while (items.moveToNext()) {
            CartSent s = new CartSent();
            int quantity = Integer.parseInt(items.getString(items.getColumnIndex("quantity")));
            s.setItemId(items.getString(items.getColumnIndex("itemId")));
            s.setQuantity(quantity);
            cartSents.add(s);

            totalprice += Integer.parseInt(items.getString(items.getColumnIndex("price"))) * quantity;
            totalcalories += Integer.parseInt(items.getString(items.getColumnIndex("calories"))) * quantity;

        }


        Price.setText(totalprice + " ₹");
        Calories.setText(totalcalories + " kcal");
        cartItemSents.setOrderItem(cartSents);
        cartItemSents.setPrice(totalprice);
        cartItemSents.setCalories(totalcalories);

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (items.getCount() == 0) {
                    Toast.makeText(Checkout.this, "Please Add Items to Cart", Toast.LENGTH_SHORT).show();
                } else {
                    compositeDisposable.add(service.placeorder(cartItemSents, token)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::handleResponse, this::handleError)
                    );
                }
            }

            private void handleError(Throwable error) {
                if (error instanceof HttpException) {
                    Gson gson = new GsonBuilder().create();
                    try {
                        String errorBody = ((HttpException) error).response().errorBody().string();
                        BadRequestException response = gson.fromJson(errorBody, BadRequestException.class);
                        Toast.makeText(Checkout.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(Checkout.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    Log.d("Error", error.getMessage());
                }
            }

            private void handleResponse(AuthRespose authRespose) {
                DB.deleteCart();
                Intent intent = new Intent(Checkout.this, OrderSuccess.class);
                startActivity(intent);
            }
        });

    }

    private void handleError(Throwable error) {
        if (error instanceof HttpException) {
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                int errorCode = ((HttpException) error).response().code();
                if (errorCode == 401) {
                    BadRequestException response = gson.fromJson(errorBody, BadRequestException.class);
                    Toast.makeText(Checkout.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Checkout.this, "Cant get user data", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(Checkout.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.d("Error", error.getMessage());
        }
    }

    private void handleResponse(User user) {
        String[] splitAddress = user.getAddress().split(", ");
        address.setText(String.join(",\n", splitAddress));
        String orderingFor = user.getName() + " ," + user.getPhone();
        userName.setText(orderingFor);
    }
}