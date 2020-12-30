package com.example.foodonline.Admin.model;

import java.util.ArrayList;
import java.util.HashMap;

public class DishTypeEntity {
    private HashMap<String,Object> dish;
    private String name,key;

    public DishTypeEntity(HashMap<String,Object> dish, String name) {
        this.dish = dish;
        this.name = name;
    }

    public DishTypeEntity(String name) {
        this.name = name;
    }

    public DishTypeEntity() {
    }

    public HashMap<String,Object> getDish() {
        return dish;
    }

    public void setDish(HashMap<String,Object> dish) {
        this.dish = dish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
