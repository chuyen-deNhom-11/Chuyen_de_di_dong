package com.example.foodonline.DataModel;

public class CartModel {
    String idDish;
    String nameFood;
    int soLuong,price;


    public String getNameFood() {
        return nameFood;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getIdDish() {
        return idDish;
    }

    public CartModel(String idUSer) {
        this.idDish = idUSer;
    }

    public int getPrice() {
        return price;
    }

    public CartModel(String nameFood, int soLuong, int price) {
        this.nameFood = nameFood;
        this.soLuong = soLuong;
        this.price = price;
    }

    public CartModel(String idDish, String nameFood, int soLuong, int price) {
        this.idDish = idDish;
        this.nameFood = nameFood;
        this.soLuong = soLuong;
        this.price = price;
    }

    public CartModel() {
    }
}
