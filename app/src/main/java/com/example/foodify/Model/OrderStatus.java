package com.example.foodify.Model;

import java.util.Date;

public class OrderStatus {
    int id;
    int status;
    Date delivery_time;
    Date order_time;
    Date dispatch_time;
    Order order;

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

    public Date getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(Date delivery_time) {
        this.delivery_time = delivery_time;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public Date getDispatch_time() {
        return dispatch_time;
    }

    public void setDispatch_time(Date dispatch_time) {
        this.dispatch_time = dispatch_time;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
