package com.example.foodonline;

import com.example.foodonline.Admin.model.AccountEntity;

public class Storage {

    private AccountEntity staff;

    public void setSavedStaff(AccountEntity data) {
        this.staff = data;
    }

    public AccountEntity getSavedStaff() {
        return staff;
    }
}
