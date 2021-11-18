package com.example.foodify.Model;

public class LoginData {
    String phone;
    String password;

    public LoginData(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public LoginData() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
