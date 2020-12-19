package com.example.foodonline.DataModel;

public class CensorModel {
    String tv_type_booking;
    String tv_number_dish;
    String tv_oderer;
    String tv_price;

    public CensorModel(String tv_type_booking, String tv_number_dish, String tv_oderer, String tv_price) {
        this.tv_type_booking = tv_type_booking;
        this.tv_number_dish = tv_number_dish;
        this.tv_oderer = tv_oderer;
        this.tv_price = tv_price;
    }

    public String getTv_type_booking() {
        return tv_type_booking;
    }

    public String getTv_number_dish() {
        return tv_number_dish;
    }

    public String getTv_oderer() {
        return tv_oderer;
    }

    public String getTv_price() {
        return tv_price;
    }
}
