package com.example.foodonline.DataModel;

public class DishModel {
    String id, name, image, price, description, idTypeDish, idCombo;
    boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getIdTypeDish() {
        return idTypeDish;
    }

    public String getIdCombo() {
        return idCombo;
    }

    public String getName() {
        return name;
    }

    public DishModel() {
    }

    public DishModel(String id, String name, String image, String price, String description, String idTypeDish, String idCombo) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.idTypeDish = idTypeDish;
        this.idCombo = idCombo;
    }

    public DishModel(String id, String name, String image, String price, boolean check) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.check = check;
    }
}
