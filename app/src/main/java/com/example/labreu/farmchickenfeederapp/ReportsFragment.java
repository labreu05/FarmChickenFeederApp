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

import java.util.Calendar;
import java.util.Date;

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
                        startDate.setText(day+"-"+(month+1)+"-"+year);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1, calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();
            }
        });

        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePicker = new DatePickerDialog(mainView.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        endDate.setText(day+"-"+(month+1)+"-"+year);

                        setDataListener(mainView);
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)-1, calendar.get(Calendar.DAY_OF_MONTH));

                datePicker.show();
            }
        });

        //Set Recycler view
        RecyclerView refillsView = mainView.findViewById(R.id.refill_entries_recycler_view);

        refillsView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference refillsRef = dbRef.child("refills").getRef();

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

    public void setDataListener(View mainView) {
        System.out.println("SETTED XDXDXDXDXD");
        RecyclerView refillsView = mainView.findViewById(R.id.refill_entries_recycler_view);

        refillsView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference refillsRef = dbRef.child("refills").getRef();

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
}
