package com.example.foodify.Model;

public class OrderHistoryStatus {
    int id;
    int status;
    String delivered_time;
    String order_time;
    String dispatch_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDelivered_time() {
        return delivered_time;
    }

    public void setDelivered_time(String delivered_time) {
        this.delivered_time = delivered_time;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getDispatch_time() {
        return dispatch_time;
    }

    public void setDispatch_time(String dispatch_time) {
        this.dispatch_time = dispatch_time;
    }


}
