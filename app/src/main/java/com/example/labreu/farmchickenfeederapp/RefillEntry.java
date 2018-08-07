package com.example.labreu.farmchickenfeederapp;

public class RefillEntry {
    private String plate;
    private String date;
    private String time;
    private String grams;

    public RefillEntry () {

    }

    public RefillEntry (String plate, String date, String time, String grams) {
        this.plate = plate;
        this.time = time;
        this.date = date;
        this.grams = grams;
    }

    public String getPlate() {
        return plate;
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
