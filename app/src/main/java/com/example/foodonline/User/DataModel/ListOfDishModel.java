package com.example.foodonline.User.DataModel;

public class ListOfDishModel {
    String id,name,sListDish;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getsListDish() {
        return sListDish;
    }

    public void setListDish(String listDish) {
        this.sListDish = listDish;
    }

    public ListOfDishModel(String name, String id,String sListDish) {
        this.id = id;
        this.name = name;
        this.sListDish = sListDish;
    }

    public ListOfDishModel() {
    }

}
