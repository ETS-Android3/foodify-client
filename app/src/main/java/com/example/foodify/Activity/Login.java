package com.example.foodify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.foodify.Common.Common;
import com.example.foodify.Model.AuthRespose;
import com.example.foodify.Model.BadRequestException;
import com.example.foodify.Model.LoginData;
import com.example.foodify.R;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.example.foodify.Utils.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class Login extends AppCompatActivity {
    private RetrofitInterface service;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private EditText phoneText, passwordText;
    private Button loginButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        phoneText = findViewById(R.id.phone);
        passwordText = findViewById(R.id.password);
        loginButton = findViewById(R.id.register);
        progressBar=findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.GONE);
        Paper.init(this);

        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progressBar.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager)getSystemService(SignUp.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                String phone = phoneText.getText().toString();
                String password = passwordText.getText().toString();
                if(Validation.loginValidate(phone, password, Login.this)) {
                    loginUser(phone, password);
                }
                else
                    progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void loginUser(String phone, String password) {
        LoginData loginaData = new LoginData(phone, password);

        compositeDisposable.add(service.loginUser(loginaData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse, this::handleError)
                );
    }

    private void handleError(Throwable error) {
        progressBar.setVisibility(View.GONE);
        if(error instanceof HttpException){
            Gson gson = new GsonBuilder().create();
            try {
                String errorBody = ((HttpException) error).response().errorBody().string();
                BadRequestException response = gson.fromJson(errorBody, BadRequestException.class);
                Toast.makeText(Login.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(Login.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            Log.d("Error",error.getMessage());
        }
    }

    private void handleResponse(AuthRespose authRespose) {
        progressBar.setVisibility(View.GONE);
        Paper.book().write(Common.token,authRespose.getToken());
        Intent intent=new Intent(Login.this,AllCategories.class);
        startActivity(intent);
    }
}