package com.example.labreu.farmchickenfeederapp;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;


public class AddScheduleFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mainView =  inflater.inflate(R.layout.fragment_add_schedule, container, false);
        Button addNewScheduleButton = mainView.findViewById(R.id.add_new_schedule_button);
        final EditText newScheduleDate = mainView.findViewById(R.id.new_schedule_date);
        final EditText newScheduleTime = mainView.findViewById(R.id.new_schedule_time);
        final CheckBox newScheduleIsRecurrent = mainView.findViewById(R.id.new_schedule_is_recurrent);


        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference schedulesRef = dbRef.child("schedules").getRef();
        final FragmentManager fragmentManager = getFragmentManager();

        addNewScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = newScheduleDate.getText().toString();
                String time = newScheduleTime.getText().toString();
                Boolean isRecurrent = newScheduleIsRecurrent.isChecked();

                if (date.isEmpty() && time.isEmpty()) {
                    Schedule newSchedule = new Schedule(date, time, isRecurrent);

                    schedulesRef.push().setValue(newSchedule);

                    Toast.makeText(getActivity(), "Schedule Successfully saved",
                            Toast.LENGTH_SHORT).show();

                    fragmentManager.beginTransaction().replace(R.id.main_container, new SchedulesFragment()).commit();
                } else {
                    Toast.makeText(getActivity(), "Please Fill all the fields.",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        final Calendar calendar = Calendar.getInstance();

        newScheduleDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(mainView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        newScheduleDate.setText(day+"-"+(month+1)+"-"+year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1, calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();
            }
        });

        newScheduleTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePicker = new TimePickerDialog(mainView.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        newScheduleTime.setText(hour + ":" + minute);
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);

                timePicker.show();
            }
        });

        return mainView;
    }

    public Boolean getBoolean(String value) {
        if (value.indexOf("true") != -1) {
            return true;
        } else {
            return false;
        }
    }
}
