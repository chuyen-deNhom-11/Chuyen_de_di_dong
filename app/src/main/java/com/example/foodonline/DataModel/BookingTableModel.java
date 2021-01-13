package com.example.foodonline.DataModel;

public class BookingTableModel {
    String id,userId,userName,phoneNumber,adress,tableName,dateBooking,timeBooking,tableID;
    int status;

    public int getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableID() {
        return tableID;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAdress() {
        return adress;
    }

    public String getTableName() {
        return tableName;
    }

    public String getDateBooking() {
        return dateBooking;
    }

    public String getTimeBooking() {
        return timeBooking;
    }


    public BookingTableModel(String userId, String userName, String phoneNumber, String adress, String tableName, String dateBooking, String timeBooking, String tableID) {
        this.userId = userId;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.adress = adress;
        this.tableName = tableName;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.tableID = tableID;
    }

    public BookingTableModel(String tableName, String dateBooking, String timeBooking, String tableID) {
        this.tableName = tableName;
        this.dateBooking = dateBooking;
        this.timeBooking = timeBooking;
        this.tableID = tableID;
    }

    public BookingTableModel() {
    }
}
