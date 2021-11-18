package com.example.foodify.Model;

import java.util.ArrayList;

public class BadRequestException {
    Integer statusCode;
    ArrayList<String> message;
    String error;

    public BadRequestException(Integer statusCode, ArrayList<String> message, String error) {
        this.statusCode = statusCode;
        this.message = message;
        this.error = error;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message.get(0);
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    public String getError(){
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
