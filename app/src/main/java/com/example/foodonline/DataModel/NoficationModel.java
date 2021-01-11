package com.example.foodonline.DataModel;

public class NoficationModel {
    String id, taiKhoan, hoaDon;
    int status;
    long time;

    public String getId() {
        return id;
    }

    public long getTime() {
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

    public NoficationModel(long time, int status) {
        this.time = time;
        this.status = status;
    }

    public NoficationModel() {
    }
}
