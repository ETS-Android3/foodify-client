package com.example.foodify.Model;

import java.util.ArrayList;

public class CartItemSent {
    int price;
    int calories;
    ArrayList<CartSent> orderItem;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public ArrayList<CartSent> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(ArrayList<CartSent> orderItem) {
        this.orderItem = orderItem;
    }
}
