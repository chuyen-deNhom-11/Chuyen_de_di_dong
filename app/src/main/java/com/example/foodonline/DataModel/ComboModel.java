package com.example.foodonline.DataModel;

public class ComboModel {
    String id, nameCombo, imageCombo, totalDish, priceCombo, listDish;

    public String getListDish() {
        return listDish;
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

    public void setListDish(String listDish) {
        this.listDish = listDish;
    }

    public ComboModel(String id, String nameCombo, String imageCombo, String totalDish, String priceCombo, String listDish) {
        this.id = id;
        this.nameCombo = nameCombo;
        this.imageCombo = imageCombo;
        this.totalDish = totalDish;
        this.priceCombo = priceCombo;
        this.listDish = listDish;
    }

    public ComboModel(String id, String nameCombo, String imageCombo, String totalDish, String priceCombo) {
        this.id = id;
        this.nameCombo = nameCombo;
        this.imageCombo = imageCombo;
        this.totalDish = totalDish;
        this.priceCombo = priceCombo;
    }

    public ComboModel() {
    }
}
