package com.example.foodonline.Admin.model;

public class FoodEntity {
    private String combo, description, image , name, price, type, key;

    public FoodEntity(String combo, String description, String image, String name, String price, String type) {
        this.combo = combo;
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type;
    }

    public FoodEntity() {
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCombo() {
        return combo;
    }

    public void setCombo(String combo) {
        this.combo = combo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }
}
