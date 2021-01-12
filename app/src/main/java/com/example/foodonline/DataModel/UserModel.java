package com.example.foodonline.DataModel;


public class UserModel {

    String uid, name, email, password, adress, numberPhone, type;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public void setNumberPhone(String phoneNumber) {
        this.numberPhone = phoneNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UserModel(String uid, String name, String email, String password, String adress, String numberPhone, String type) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.adress = adress;
        this.numberPhone = numberPhone;
        this.type = type;
    }

    public UserModel(String name, String adress, String numberPhone) {
        this.name = name;
        this.adress = adress;
        this.numberPhone = numberPhone;
    }

    public UserModel() {
    }
}
