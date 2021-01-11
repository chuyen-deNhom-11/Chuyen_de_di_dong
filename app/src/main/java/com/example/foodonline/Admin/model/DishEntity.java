package com.example.foodonline.Admin.model;

public class DishEntity {
    private String idDish, nameFood;
    private int price, soLuong;

    public DishEntity(String idDish, String nameFood, int price, int soLuong) {
        this.idDish = idDish;
        this.nameFood = nameFood;
        this.price = price;
        this.soLuong = soLuong;
    }

    public DishEntity() {
    }

    public String getIdDish() {
        return idDish;
    }

    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    public String getNameFood() {
        return nameFood;
    }

    public void setNameFood(String nameFood) {
        this.nameFood = nameFood;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}