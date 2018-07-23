package com.example.labreu.farmchickenfeederapp;

public class RefillEntry {
    private String date;
    private String time;
    private String grams;

    public RefillEntry () {

    }

    public RefillEntry (String date, String time, String grams) {
        this.time = time;
        this.date = date;
        this.grams = grams;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getGrams() {
        return grams;
    }

}
