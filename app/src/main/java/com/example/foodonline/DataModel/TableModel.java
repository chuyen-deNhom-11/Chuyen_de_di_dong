package com.example.foodonline.DataModel;

public class TableModel {
        String id, nameTable ,numberPeople, position;
        int status;

    public String getNameTable() {
        return nameTable;
    }

    public String getId() {
        return id;
    }

    public String getNumberPeople() {
        return numberPeople;
    }

    public String getPosition() {
        return position;
    }

    public int getStatus() {
        return status;
    }

    public TableModel(String id, String nameTable, String numberPeople, String position, int status) {
        this.id = id;
        this.nameTable = nameTable;
        this.numberPeople = numberPeople;
        this.position = position;
        this.status = status;
    }
}
