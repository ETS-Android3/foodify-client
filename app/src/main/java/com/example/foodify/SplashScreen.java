package com.example.foodify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodify.Activity.AllCategories;
import com.example.foodify.Activity.MainActivity;
import com.example.foodify.Common.Common;
import com.example.foodify.Model.BadRequestException;
import com.example.foodify.Model.User;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.example.foodify.Utils.DBHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class SplashScreen extends AppCompatActivity {
    private RetrofitInterface service;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    LottieAnimationView lottieAnimationView;
    DBHelper DB;
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);
        lottieAnimationView=findViewById(R.id.animationview);
        DB=new DBHelper(this);

//        Paper.init(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                Log.d("Token",Common.token);
//                String token = Paper.book().read(Common.token);

                Cursor cursor=DB.getUserData();
                if (cursor.moveToFirst()) {
                    do {
                        StringBuilder sb = new StringBuilder();
                        int columnsQty = cursor.getColumnCount();
                        for (int idx=0; idx<columnsQty; ++idx) {
                            sb.append(cursor.getString(idx));
                            if (idx < columnsQty - 1)
                                sb.append("; ");
                        }
                        Log.v("Print", String.format("Row: %d, Values: %s", cursor.getPosition(),
                                sb.toString()));
                        token=cursor.getString(1);
                    } while (cursor.moveToNext());
                }

//                if(c.getCount()>0) {
////                if(c.getCount()==0)
//                     token = c.getString(1);
//                }
                if (token != null) {
                    if (!token.isEmpty())
                        loginuser(token);
                    else
                    {
                        Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                        startActivity(intent);
                    }
                }


               else {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                }
           }

        },2000);

    }



    private void loginuser(String token) {
        compositeDisposable.add(service.userData(token)
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
                Toast.makeText(SplashScreen.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(SplashScreen.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.d("Error",error.getMessage());
        }
    }

    private void handleResponse(User user) {
        Intent intent=new Intent(SplashScreen.this, AllCategories.class);
        startActivity(intent);
    }
}