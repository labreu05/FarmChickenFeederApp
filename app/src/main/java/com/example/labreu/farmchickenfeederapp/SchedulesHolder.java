package com.example.labreu.farmchickenfeederapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class SchedulesHolder extends RecyclerView.ViewHolder {

    private final TextView date;
    private final TextView time;
    private final TextView isRecurrent;

    public SchedulesHolder(View itemView) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.schedule_date);
        time = (TextView) itemView.findViewById(R.id.schedule_time);
        isRecurrent = (TextView) itemView.findViewById(R.id.schedule_is_recurrent);
    }

    public void setDate(String text) {
        date.setText(text);
    }

    public void setTime(String text) {
        time.setText(text);
    }

    public void setIsRecurrent(String text) {
        isRecurrent.setText(text);
    }
}
