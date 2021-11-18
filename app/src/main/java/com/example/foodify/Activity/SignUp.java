package com.example.foodify.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foodify.Model.AuthRespose;
import com.example.foodify.Model.BadRequestException;
import com.example.foodify.Model.LoginData;
import com.example.foodify.Model.RegisterData;
import com.example.foodify.R;
import com.example.foodify.Retrofit.NetworkClient;
import com.example.foodify.Retrofit.RetrofitInterface;
import com.example.foodify.Utils.Validation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;
import retrofit2.Retrofit;

public class SignUp extends AppCompatActivity {

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitInterface service;
    private EditText nameText, phoneText, passwordText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameText = findViewById(R.id.name);
        phoneText = findViewById(R.id.phone);
        passwordText = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);

        Retrofit retrofitClient = NetworkClient.getInstance();
        service = retrofitClient.create(RetrofitInterface.class);

        registerButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String phone = phoneText.getText().toString();
                String password = passwordText.getText().toString();
                String name = nameText.getText().toString();
                Boolean is_staff = false;
                if(Validation.registerValidate(phone, password, name,SignUp.this)) {
                    registerUser(phone, password, name, is_staff);
                }
            }
        });
    }

    private void registerUser(String phone, String password, String name, Boolean is_staff) {
        RegisterData registerData = new RegisterData(name, phone, is_staff, password);
        compositeDisposable.add(service.registerUser(registerData)
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
                Toast.makeText(SignUp.this, response.getMessage(), Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(SignUp.this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleResponse(AuthRespose authRespose) {
        Toast.makeText(SignUp.this, authRespose.getToken(), Toast.LENGTH_LONG).show();
    }
}