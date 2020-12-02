package com.example.foodonline.User.DataModel;

public class ListOfDishModel {
    String name,dish;

    public String getName() {
        return name;
    }

    public String getDish() {
        return dish;
    }

    public ListOfDishModel(String name, String dish) {
        this.name = name;
        this.dish = dish;
    }

    public ListOfDishModel() {
    }
}
