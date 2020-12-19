package com.example.foodonline.DataModel;

public class TableModel {
        String id, nameTable , position;
        int status,numberPeople;

    public void setId(String id) {
        this.id = id;
    }

    public void setNameTable(String nameTable) {
        this.nameTable = nameTable;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setNumberPeople(int numberPeople) {
        this.numberPeople = numberPeople;
    }

    public String getNameTable() {
        return nameTable;
    }

    public String getId() {
        return id;
    }

    public int getNumberPeople() {
        return numberPeople;
    }

    public String getPosition() {
        return position;
    }

    public int getStatus() {
        return status;
    }

    public TableModel(String id, String nameTable, int numberPeople, String position, int status) {
        this.id = id;
        this.nameTable = nameTable;
        this.numberPeople = numberPeople;
        this.position = position;
        this.status = status;
    }

    public TableModel(String id, String nameTable, int numberPeople, int status) {
        this.id = id;
        this.nameTable = nameTable;
        this.numberPeople = numberPeople;
        this.status = status;
    }

    public TableModel() {
    }
}
