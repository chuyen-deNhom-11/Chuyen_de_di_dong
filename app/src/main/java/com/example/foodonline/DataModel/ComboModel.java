package com.example.foodonline.DataModel;

public class ComboModel {
    String id;
    String nameCombo;
    String imageCombo;
    String totalCombo;
    String priceCombo;

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

    public String getTotalCombo() {
        return totalCombo;
    }

    public ComboModel(String id, String nameCombo, String imageCombo, String totalCombo, String priceCombo) {
        this.id = id;
        this.nameCombo = nameCombo;
        this.imageCombo = imageCombo;
        this.totalCombo = totalCombo;
        this.priceCombo = priceCombo;
    }
    public ComboModel() {
    }
}
