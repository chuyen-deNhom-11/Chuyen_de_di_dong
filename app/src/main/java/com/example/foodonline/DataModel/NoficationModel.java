package com.example.foodonline.DataModel;

public class NoficationModel {
    String id, taiKhoan, hoaDon,time;
    int status;

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public String getHoaDon() {
        return hoaDon;
    }

    public int getStatus() {
        return status;
    }

    public NoficationModel(String id, String taiKhoan, String hoaDon, String time, int status) {
        this.id = id;
        this.taiKhoan = taiKhoan;
        this.hoaDon = hoaDon;
        this.time = time;
        this.status = status;
    }

    public NoficationModel() {
    }
}
