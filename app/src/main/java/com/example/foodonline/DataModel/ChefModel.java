package com.example.foodonline.DataModel;

public class ChefModel {
    String idBill, nameFood, amount, table, keyDish,reason,totalPrice;
    int status,type,soLuong,price;

    public String getReason() {
        return reason;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getPrice() {
        return price;
    }

    public String getKeyDish() {
        return keyDish;
    }

    public void setKeyDish(String keyDish) {
        this.keyDish = keyDish;
    }

    public String getNameFood() {
        return nameFood;
    }

    public String getAmount() {
        return amount;
    }

    public int getType() {
        return type;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public ChefModel() {
    }
}
