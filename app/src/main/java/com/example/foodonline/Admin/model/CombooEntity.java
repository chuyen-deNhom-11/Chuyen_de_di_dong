package com.example.foodonline.Admin.model;

import java.util.ArrayList;
import java.util.HashMap;

public class CombooEntity {
    private String imageCombo, nameCombo, priceCombo;
    private HashMap<String,Object> dish;
    private String key;

    public CombooEntity(String imageCombo, String nameCombo, String priceCombo, HashMap<String,Object> dish) {
        this.imageCombo = imageCombo;
        this.nameCombo = nameCombo;
        this.priceCombo = priceCombo;
        this.dish = dish;
    }

    public CombooEntity(String imageCombo, String nameCombo, String priceCombo) {
        this.imageCombo = imageCombo;
        this.nameCombo = nameCombo;
        this.priceCombo = priceCombo;
    }

    public CombooEntity() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageCombo() {
        return imageCombo;
    }

    public void setImageCombo(String imageCombo) {
        this.imageCombo = imageCombo;
    }

    public String getNameCombo() {
        return nameCombo;
    }

    public void setNameCombo(String nameCombo) {
        this.nameCombo = nameCombo;
    }

    public String getPriceCombo() {
        return priceCombo;
    }

    public void setPriceCombo(String priceCombo) {
        this.priceCombo = priceCombo;
    }

    public HashMap<String,Object> getDish() {
        return dish;
    }

    public void setDish(HashMap<String,Object> dish) {
        this.dish = dish;
    }
}
