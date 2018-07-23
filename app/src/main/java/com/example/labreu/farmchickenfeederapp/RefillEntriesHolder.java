package com.example.labreu.farmchickenfeederapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class RefillEntriesHolder extends RecyclerView.ViewHolder {

    private final TextView date;
    private final TextView time;
    private final TextView grams;

    public RefillEntriesHolder(View itemView) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.refill_date);
        time = (TextView) itemView.findViewById(R.id.refill_time);
        grams = (TextView) itemView.findViewById(R.id.refill_grams);
    }

    public void setDate(String text) {
        date.setText(text);
    }

    public void setTime(String text) {
        time.setText(text);
    }

    public void setGrams(String text) {
        grams.setText(text);
    }
}
