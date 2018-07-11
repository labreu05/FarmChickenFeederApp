package com.example.labreu.farmchickenfeederapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SchedulesFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mainView = inflater.inflate(R.layout.fragment_schedules, container, false);

        RecyclerView schedulesView = mainView.findViewById(R.id.schedules_recycler_view);
        schedulesView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference schedulesRef = dbRef.child("schedules").getRef();

        FirebaseRecyclerAdapter<Schedule, SchedulesHolder> scheduleAdapter = new FirebaseRecyclerAdapter<Schedule, SchedulesHolder>(Schedule.class, R.layout.schedule, SchedulesHolder.class, schedulesRef) {
            @Override
            protected void populateViewHolder(SchedulesHolder viewHolder, Schedule model, int position) {
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setIsRecurrent(model.getIsRecurrent());
            }
        };

        schedulesView.setAdapter(scheduleAdapter);

        return mainView;
    }
}
