package com.example.foodonline.DataModel;


import android.widget.EditText;

public class UserModel {

    String id, name, email, password, adress, numberPhone, type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
