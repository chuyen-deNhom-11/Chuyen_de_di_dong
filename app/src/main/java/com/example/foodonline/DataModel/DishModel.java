package com.example.foodonline.DataModel;

public class DishModel {
    String id, name, image, price, description, idTypeDish, idCombo;

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
}
