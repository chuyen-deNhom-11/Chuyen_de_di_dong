package com.example.foodonline.DataModel;

public class ChefModel {
    String nameFood, amount, table;

    public String getNameFood() {
        return nameFood;
    }

    public String getAmount() {
        return amount;
    }

    public String getTable() {
        return table;
    }

    public ChefModel(String nameFood, String amount, String table) {
        this.nameFood = nameFood;
        this.amount = amount;
        this.table = table;
    }
}
