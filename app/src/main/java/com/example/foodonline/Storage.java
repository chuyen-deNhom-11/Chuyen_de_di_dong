package com.example.foodonline;

import com.example.foodonline.Admin.model.AccountEntity;

public class Storage {

    private AccountEntity staff;
    private String uid;

    public void setSavedStaff(AccountEntity data) {
        this.staff = data;
    }

    public AccountEntity getSavedStaff() {
        return staff;
    }

    public void saveUId(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}
