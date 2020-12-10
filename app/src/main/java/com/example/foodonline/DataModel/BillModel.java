package com.example.foodonline.DataModel;

public class BillModel {
    String id, user,nameDish,price;
    int amount;

    public String getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getNameDish() {
        return nameDish;
    }

    public String getPrice() {
        return price;
    }

    public int getAmount() {
        return amount;
    }

    public BillModel(String id, String user, String nameDish, String price, int amount) {
        this.id = id;
        this.user = user;
        this.nameDish = nameDish;
        this.price = price;
        this.amount = amount;
    }

    public BillModel() {
    }
}
