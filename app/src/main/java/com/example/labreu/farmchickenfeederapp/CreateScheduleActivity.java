package com.example.labreu.farmchickenfeederapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class CreateScheduleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        Button addNewScheduleButton = this.findViewById(R.id.add_new_schedule_button);
        final EditText newScheduleDate = this.findViewById(R.id.new_schedule_date);
        final EditText newScheduleTime = this.findViewById(R.id.new_schedule_time);
        final CheckBox newScheduleIsRecurrent = this.findViewById(R.id.new_schedule_is_recurrent);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference schedulesRef = dbRef.child("schedules").getRef();

        newScheduleIsRecurrent.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    newScheduleDate.setText("-");
                    newScheduleDate.setEnabled(false);
                } else {
                    newScheduleDate.setText("");
                    newScheduleDate.setEnabled(true);
                }
            }
        });

        addNewScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = newScheduleDate.getText().toString();
                String time = newScheduleTime.getText().toString();
                Boolean isRecurrent = newScheduleIsRecurrent.isChecked();

                if ((!date.isEmpty() && !time.isEmpty()) || (isRecurrent && !time.isEmpty())) {
                    Schedule newSchedule = new Schedule(date, time, isRecurrent);

                    schedulesRef.push().setValue(newSchedule);

                    Toast.makeText(getBaseContext(), "Schedule Successfully saved",
                            Toast.LENGTH_SHORT).show();

                finish();
                } else {
                    Toast.makeText(getBaseContext(), "Please Fill all the fields.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Calendar calendar = Calendar.getInstance();

        newScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(CreateScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        String newMonth = "" + month;
                        String newDay = "" + day;

                        if(month + 1 < 10){

                            newMonth = "0" + month;
                        }

                        if(day < 10){

                            newDay  = "0" + day ;
                        }

                        newScheduleDate.setText(newDay + "-" + newMonth + "-" + year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1, calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();
            }
        });

        newScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(CreateScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        newScheduleTime.setText(getHour(hour, minute));
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), false);

                timePicker.show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
            default:
                return true;
        }
    }

    public Boolean getHour(String value) {
        if (value.indexOf("true") != -1) {
            return true;
        } else {
            return false;
        }
    }

    public String getHour(int hour, int minute) {
        int newHour = hour;
        String stringHour = "";
        String newMinute = "" + minute;
        String amOrPm = "pm";

        if (hour<12) {
            amOrPm = "am";
        }

        switch (hour) {
            case 0: {
                newHour = 12;
                break;
            }
            case 13: {
                newHour = 1;
                break;
            }
            case 14: {
                newHour = 2;
                break;
            }
            case 15: {
                newHour = 3;
                break;
            }
            case 16: {
                newHour = 4;
                break;
            }
            case 17: {
                newHour = 5;
                break;
            }
            case 18: {
                newHour = 6;
                break;
            }
            case 19: {
                newHour = 7;
                break;
            }
            case 20: {
                newHour = 8;
                break;
            }
            case 21: {
                newHour = 9;
                break;
            }
            case 22: {
                newHour = 10;
                break;
            }
            case 23: {
                newHour = 11;
                break;
            }
        }

        stringHour = "" + newHour;

        if(newHour  < 10){

            stringHour = "0" + newHour;
        }

        if(minute < 10){

            newMinute  = "0" + minute ;
        }

        return stringHour + ":" + newMinute + " " + amOrPm;
    }
}
