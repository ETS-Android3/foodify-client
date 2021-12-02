package com.example.foodify.Model;

public class AuthRespose {
    public String access_token;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int userId;

    public String getToken(){
        return this.access_token;
    }
}
