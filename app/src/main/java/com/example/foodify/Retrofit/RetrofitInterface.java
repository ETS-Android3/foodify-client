package com.example.foodify.Retrofit;

import com.example.foodify.Model.AuthRespose;
import com.example.foodify.Model.LoginData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitInterface {
    @POST("user/login")
    Observable<AuthRespose> loginUser(@Body LoginData loginData);
}
