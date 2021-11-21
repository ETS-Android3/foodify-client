package com.example.foodify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foodify.Model.Category;
import com.example.foodify.R;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class CategoryById extends AppCompatActivity {
    String categoryId="";
    ImageView food_image;
    TextView food_name;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        food_image=findViewById(R.id.food_image);
        food_name=findViewById(R.id.food_name);
        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);


        if(getIntent()!=null)
        {
            categoryId=getIntent().getStringExtra("categoryId");
            Log.d("TAG", "Check category " +categoryId);
        }
        if(!categoryId.isEmpty() && categoryId!=null)
        {
            compositeDisposable.add(service.allFood(categoryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse, this::handleError)
            );
        }


    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(com.example.foodify.Model.CategoryById categoryById) {
        food_name.setText(categoryById.getItems().get(0).getName());

    }




    private void loadListFood(String categoryId) {

    }
}