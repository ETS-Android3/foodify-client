package com.example.foodify.Model;

public class OrderHistory {
    int id;
    int price;
    int calories;
    String created_at;
    OrderHistoryStatus status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public OrderHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(OrderHistoryStatus status) {
        this.status = status;
    }
}
