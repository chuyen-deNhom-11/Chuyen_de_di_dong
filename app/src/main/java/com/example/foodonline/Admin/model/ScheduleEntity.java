package com.example.foodonline.Admin.model;

public class ScheduleEntity {
    private int day, shift;
    private String staff_id,staff_name, type, key;

    public ScheduleEntity(int day, int shift, String staff_id, String staff_name, String type) {
        this.day = day;
        this.shift = shift;
        this.staff_id = staff_id;
        this.staff_name = staff_name;
        this.type = type;
    }

    public ScheduleEntity() {
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getStaff_id() {
        return staff_id;
    }

    public void setStaff_id(String staff_id) {
        this.staff_id = staff_id;
    }

    public String getStaff_name() {
        return staff_name;
    }

    public void setStaff_name(String staff_name) {
        this.staff_name = staff_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
