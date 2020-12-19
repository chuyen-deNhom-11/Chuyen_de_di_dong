package com.example.foodonline.DataModel;

public class ComboModel {
    String id, nameCombo, imageCombo, totalDish, priceCombo, dish;

    public String getDish() {
        return dish;
    }

    public String getId() {
        return id;
    }

    public String getNameCombo() {
        return nameCombo;
    }

    public String getImageCombo() {
        return imageCombo;
    }

    public String getPriceCombo() {
        return priceCombo;
    }

    public String getTotalDish() {
        return totalDish;
    }

    public ComboModel(String id, String nameCombo, String imageCombo, String totalDish, String priceCombo, String dish) {
        this.id = id;
        this.nameCombo = nameCombo;
        this.imageCombo = imageCombo;
        this.totalDish = totalDish;
        this.priceCombo = priceCombo;
        this.dish = dish;
    }

    public ComboModel() {
    }
}
