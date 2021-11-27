package com.example.foodify.Retrofit;

import com.example.foodify.Utils.Constants;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkClient {
    public static Retrofit instance;

    public static Retrofit getInstance() {
        instance = new Retrofit.Builder().
                baseUrl(Constants.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
                build();
        return instance;
    }
}
