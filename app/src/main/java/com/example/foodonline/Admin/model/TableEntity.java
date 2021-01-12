package com.example.foodonline.Admin.model;

public class TableEntity  {
   private int  numberPeople,status;
   private String key;

    public TableEntity() {
    }

    public TableEntity(int numberPeople, int status) {
        this.numberPeople = numberPeople;
        this.status = status;
    }



    public int getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}
