package com.example.foodify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class AllCategories extends AppCompatActivity {
    ImageView category_image;
    TextView category_name;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_categories);
        category_image=findViewById(R.id.category_image);
        category_name=findViewById(R.id.category_name);
        category_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent FoodList=new Intent(AllCategories.this, CategoryById.class);
                FoodList.putExtra("categoryId","1");
                startActivity(FoodList);
            }
        });
        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);

        compositeDisposable.add(service.allCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
        );

    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, "Pritesh i maa ki chut", Toast.LENGTH_SHORT).show();
    }

    private void handleResponse(ArrayList<Category> categories) {
        Toast.makeText(this, "Entered category", Toast.LENGTH_SHORT).show();
        Log.d("Response",categories.get(0).getName());
        Category data=categories.get(0);
        category_name.setText(data.getName());
        Picasso.with(getBaseContext()).load(data.getImage())
                .into(category_image);

    }

}