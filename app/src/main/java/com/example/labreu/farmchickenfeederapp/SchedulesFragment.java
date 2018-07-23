package com.example.labreu.farmchickenfeederapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
        FloatingActionButton addScheduleButton = mainView.findViewById(R.id.add_schedule_button);

        schedulesView.setLayoutManager(new LinearLayoutManager(mainView.getContext()));
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference schedulesRef = dbRef.child("schedules").getRef();

        final FragmentManager fragmentManager = getFragmentManager();

        final FirebaseRecyclerAdapter<Schedule, SchedulesHolder> scheduleAdapter = new FirebaseRecyclerAdapter<Schedule, SchedulesHolder>(Schedule.class, R.layout.schedule, SchedulesHolder.class, schedulesRef) {
            @Override
            protected void populateViewHolder(SchedulesHolder viewHolder, Schedule model, int position) {
                viewHolder.setDate(model.getDate());
                viewHolder.setTime(model.getTime());
                viewHolder.setIsRecurrent(model.getIsRecurrent());
                viewHolder.setDeleteOption(getRef(position));
            }
        };

        schedulesView.setAdapter(scheduleAdapter);

        addScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createScheduleActivity = new Intent(getContext(), CreateScheduleActivity.class);
                startActivity(createScheduleActivity);
            }
        });

        return mainView;
    }
}
