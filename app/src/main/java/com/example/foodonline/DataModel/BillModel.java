package com.example.foodonline.DataModel;

import java.util.ArrayList;

public class BillModel {
    String id, user,name,adress,phone,price,nameTable,dateBooking,timeBooking,tableID;
    long time;
    int amount,status,type;

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public String getUser() {
        return user;
    }

    public String getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public String getPhone() {
        return phone;
    }

    public int getAmount() {
        return amount;
    }

    public long getTime() {
        return time;
    }

    public int getStatus() {
        return status;
    }

    public String getNameTable() {
        return nameTable;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }

    public String getTableID() {
        return tableID;
    }

    public BillModel(String id, String user, String price, int amount) {
        this.id = id;
        this.user = user;
        this.price = price;
        this.amount = amount;
    }

    public BillModel(String user, String name, String adress, String phone, String price, int amount, int status) {
        this.user = user;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.price = price;
        this.amount = amount;
        this.status = status;
    }

    public BillModel(String user, String name, String adress, String phone, String price, String nameTable, String dateBooking, String timeBooking, String tableID, int amount, long time,int Type, int status) {
        this.user = user;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.price = price;
        this.nameTable = nameTable;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.tableID = tableID;
        this.amount = amount;
        this.time = time;
        this.type = Type;
        this.status = status;
    }

    public BillModel(String id, String user, String name, String adress, String phone, String price, String nameTable, String dateBooking, String timeBooking, String tableID, long time, int amount, int status, int type) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.adress = adress;
        this.phone = phone;
        this.price = price;
        this.nameTable = nameTable;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.tableID = tableID;
        this.time = time;
        this.amount = amount;
        this.status = status;
        this.type = type;
    }

    public BillModel() {
    }
}
