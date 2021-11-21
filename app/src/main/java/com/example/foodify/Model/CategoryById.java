package com.example.foodify.Model;

import java.util.ArrayList;

public class CategoryById {
    public String name;
    public String description;
    public String id;
    public String image;
    ArrayList<FoodItem>items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ArrayList<FoodItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<FoodItem> items) {
        this.items = items;
    }
}
