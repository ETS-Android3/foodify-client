package com.example.foodify.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.foodify.Adapter.CategoryAdapter;
import com.example.foodify.Adapter.HistoryAdapter;
import com.example.foodify.Common.Common;
import com.example.foodify.Model.BadRequestException;
import com.example.foodify.Model.Category;
import com.example.foodify.R;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class OrderHistory extends AppCompatActivity {
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface service;
    RecyclerView recycler_history;
    RecyclerView.LayoutManager layoutManager;
//    String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlzU3RhZmYiOmZhbHNlLCJ1c2VySWQiOjF9LCJpYXQiOjE2MzgxMzYwMDF9._VH8jLlncuo3_3D8sOztquW55YNtUeWSHtCCE7m6g1I";
String token= Common.token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        recycler_history=findViewById(R.id.recycler_history);
        recycler_history.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recycler_history.setLayoutManager(layoutManager);
        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);
        loadOrders();
    }

    private void loadOrders() {
        compositeDisposable.add(service.getHistory(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        );

    }
    private void handleError(Throwable error) {

        if(error instanceof HttpException){
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                BadRequestException response = gson.fromJson(errorBody, BadRequestException.class);
                Toast.makeText(OrderHistory.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(OrderHistory.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.d("Error",error.getMessage());
        }
    }

    private void handleResponse(ArrayList<com.example.foodify.Model.OrderHistory> categories) {
        Log.d("History", String.valueOf(categories.size()));
        recycler_history.setAdapter(new HistoryAdapter(categories));
    }
}