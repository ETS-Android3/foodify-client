package com.example.foodify.Utils;

import android.content.Context;
import android.widget.Toast;

public class Validation {
    public static Boolean loginValidate(String phone, String password, Context context){
        if(phone.length() != 10){
            Toast.makeText(context, "Invalid Phone", Toast.LENGTH_LONG).show();
            return false;
        } else if(password.length() < 6){
            Toast.makeText(context, "Password must be longer than 6 characters", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public static Boolean registerValidate(String phone, String password, String name, Context context){
        if(phone.length() != 10){
            Toast.makeText(context, "Invalid Phone", Toast.LENGTH_LONG).show();
            return false;
        } else if(password.length() < 6){
            Toast.makeText(context, "Password must be longer than 6 characters", Toast.LENGTH_LONG).show();
            return false;
        } else if (name.trim().length() == 0){
            Toast.makeText(context, "Name is required", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
