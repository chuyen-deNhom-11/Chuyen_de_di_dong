package com.example.foodonline.DataModel;

public class UserModel {
    String id,name,email,password,adress,numberPhone,type;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getAdress() {
        return adress;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public String getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public UserModel(String id, String name, String email, String password, String adress, String numberPhone, String type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.numberPhone = numberPhone;
        this.type = type;
    }

    public UserModel() {
    }
}
