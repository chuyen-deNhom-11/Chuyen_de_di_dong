package com.example.foodonline.DataModel;

public class AcountModel {
    String taikhoan,matkhau;
    int role;

    public String getTaikhoan() {
        return taikhoan;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public int getRole() {
        return role;
    }

    public AcountModel(String taikhoan, String matkhau, int role) {
        this.taikhoan = taikhoan;
        this.matkhau = matkhau;
        this.role = role;
    }

    public AcountModel() {
    }
}
