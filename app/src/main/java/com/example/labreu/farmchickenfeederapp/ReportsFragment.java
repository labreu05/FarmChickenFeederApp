package com.example.labreu.farmchickenfeederapp;


import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ReportsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View mainView = inflater.inflate(R.layout.fragment_reports, container, false);

        final EditText startDate = mainView.findViewById(R.id.start_date);
        final EditText endDate = mainView.findViewById(R.id.end_date);
        final Calendar calendar = Calendar.getInstance();

        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(mainView.getContext(), new DatePickerDialog.OnDateSetListener() {
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

                        String rawStartDate = newDay+"-"+ newMonth+"-"+year;
                        String rawEndDate = endDate.getText().toString();
                        System.out.println(rawStartDate);
                        Long millisStartDate = getFormattedtDate(rawStartDate);
                        Long millisEndDate = getFormattedtDate(rawEndDate);
                        startDate.setText(rawStartDate);

                        setDataListener(mainView, millisStartDate, millisEndDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(mainView.getContext(), new DatePickerDialog.OnDateSetListener() {
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

                        String rawEndDate = newDay+"-"+ newMonth+"-"+year;
                        String rawStartDate = startDate.getText().toString();
                        System.out.println(rawStartDate);
                        Long millisStartDate = getFormattedtDate(rawStartDate);
                        Long millisEndDate = getFormattedtDate(rawEndDate);

                        endDate.setText(rawEndDate);
                        setDataListener(mainView, millisStartDate, millisEndDate);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();
            }
        });

        //Set Recycler view
        RecyclerView refillsView = mainView.findViewById(R.id.refill_entries_recycler_view);

        refillsView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference refillsRef = dbRef.child("refills").orderByChild("millisDate").getRef();


        final FirebaseRecyclerAdapter<RefillEntry, RefillEntriesHolder> scheduleAdapter = new FirebaseRecyclerAdapter<RefillEntry, RefillEntriesHolder>(RefillEntry.class, R.layout.refill_entry, RefillEntriesHolder.class, refillsRef) {
            @Override
            protected void populateViewHolder(RefillEntriesHolder viewHolder, RefillEntry model, int position) {
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setGrams(model.getGrams());
            }
        };

        refillsView.setAdapter(scheduleAdapter);

        // Inflate the layout for this fragment
        return mainView;
    }

    public void setDataListener(View mainView, Long startDate, Long endDate) {
        RecyclerView refillsView = mainView.findViewById(R.id.refill_entries_recycler_view);
        refillsView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        System.out.println("Start Date: "+ startDate);
        System.out.println("End Date: "+ endDate);
        final DatabaseReference refillsRef = dbRef.child("refills").orderByChild("millisDate").startAt(startDate).endAt(endDate).getRef();

        final FirebaseRecyclerAdapter<RefillEntry, RefillEntriesHolder> scheduleAdapter = new FirebaseRecyclerAdapter<RefillEntry, RefillEntriesHolder>(RefillEntry.class, R.layout.refill_entry, RefillEntriesHolder.class, refillsRef) {
            @Override
            protected void populateViewHolder(RefillEntriesHolder viewHolder, RefillEntry model, int position) {
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setGrams(model.getGrams());
            }
        };

        refillsView.setAdapter(scheduleAdapter);
    }

    public long getFormattedtDate(String date)  {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("America/Santo_Domingo"));
        String rawDate = date + " 00:00:00";
        Date parsedDate = null;

        try {
            parsedDate = sdf.parse(rawDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//
        System.out.println(parsedDate);
        System.out.println(parsedDate.getTime());
        return parsedDate.getTime();
    }
}
