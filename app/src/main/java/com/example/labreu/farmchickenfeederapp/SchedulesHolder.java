package com.example.labreu.farmchickenfeederapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class SchedulesHolder extends RecyclerView.ViewHolder {

    private final TextView date;
    private final TextView time;
    private final CheckBox isRecurrent;
    private final ImageView scheduleDeleteOption;

    public SchedulesHolder(View itemView) {
        super(itemView);

        date = (TextView) itemView.findViewById(R.id.schedule_date);
        time = (TextView) itemView.findViewById(R.id.schedule_time);
        isRecurrent = (CheckBox) itemView.findViewById(R.id.schedule_is_recurrent);
        scheduleDeleteOption = (ImageView) itemView.findViewById(R.id.schedule_delete);
    }

    public void setDate(String text) {
        date.setText(text);
    }

    public void setTime(String text) {
        time.setText(text);
    }

    public void setIsRecurrent(Boolean isSelected) {
        isRecurrent.setChecked(isSelected);
    }

    public void setDeleteOption(final DatabaseReference childRef) {
        scheduleDeleteOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

                builder.setMessage("Do you want to delete this schedule?")
                        .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                childRef.removeValue();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });
    }
}
