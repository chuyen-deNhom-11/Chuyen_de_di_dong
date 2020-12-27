package com.example.foodonline.Admin.model;

public class AccountEntity {
    private String adress, email, id, name, password, numberPhone, type;

    public AccountEntity(String adress, String email, String id, String name, String password, String numberPhone, String type) {
        this.adress = adress;
        this.email = email;
        this.id = id;
        this.name = name;
        this.password = password;
        this.numberPhone = numberPhone;
        this.type = type;
    }

    public AccountEntity(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public AccountEntity() {
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }
}
