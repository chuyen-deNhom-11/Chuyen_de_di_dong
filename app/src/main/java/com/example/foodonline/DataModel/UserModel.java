package com.example.foodonline.DataModel;


import android.widget.EditText;

public class UserModel {
    private String uid,name,email,taiKhoan,address,phoneNumber,password,type;


//    EditText edtName;
//    EditText edtTaiKhoan;
//    EditText edtPass;
//    EditText edtConfirmPass;
//    EditText edtAddress;
//    EditText edtEmail;
//    EditText edtPhoneNumber;


    public UserModel() {
    }

    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }



    public UserModel(String uid, String name, String email, String taiKhoan, String address, String phoneNumber, String password, String type) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.taiKhoan = taiKhoan;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.type = type;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;

    }
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
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

}
