package com.example.labreu.farmchickenfeederapp;

public class Schedule {
    private Boolean isRecurrent;
    private String date;
    private String time;

    public Schedule () {

    }

    public Schedule (String date, String time, Boolean isRecurrent) {
        this.time = time;
        this.date = date;
        this.isRecurrent = isRecurrent;
    }

    public String getTime() {
        return time;
    }

    public Boolean getIsRecurrent() {
        return isRecurrent;
    }

    public String getDate() {
        return date;
    }
}
