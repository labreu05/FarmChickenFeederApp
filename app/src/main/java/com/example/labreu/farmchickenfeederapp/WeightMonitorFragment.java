package com.example.labreu.farmchickenfeederapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;

import static java.lang.Integer.parseInt;

public class WeightMonitorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get Firebase References
        // Main Container References
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mainContainerPoundsRef = dbRef.child("mainContainer").child("pounds");
        // Plate 1 References
        DatabaseReference plate1Ref = dbRef.child("plates").child("plate1");
        DatabaseReference plate1PoundsRef = plate1Ref.child("pounds");
        final DatabaseReference plate1StatusRef = plate1Ref.child("isActive");
        DatabaseReference plate1LastFillRef = plate1Ref.child("lastFill");
        // Plate 2 References
        DatabaseReference plate2Ref = dbRef.child("plates").child("plate2");
        DatabaseReference plate2PoundsRef = plate2Ref.child("pounds");
        final DatabaseReference plate2StatusRef = plate2Ref.child("isActive");
        DatabaseReference plate2LastFillRef = plate2Ref.child("lastFill");
        // Settings Ref
        final DatabaseReference platesCapacity = dbRef.child("settings").child("platesCapacity");
        DatabaseReference mainContainerCapacity = dbRef.child("settings").child("mainContainerCapacity");

        // Get views
        final View mainView = inflater.inflate(R.layout.fragment_weight_monitor, container, false);
        final TextView mainContainerPounds = (TextView) mainView.findViewById(R.id.pounds);
        final TextView mainContainerPercentage = (TextView) mainView.findViewById(R.id.main_container_percentage);
        // Plate 1
        final TextView plate1Pounds = (TextView) mainView.findViewById(R.id.plate_1_pounds);
        final TextView plate1LastFill = (TextView) mainView.findViewById(R.id.plate_1_last_fill);
        final Switch plate1Status = (Switch) mainView.findViewById(R.id.plate_1_status);
        final TextView plate1Percentage = (TextView) mainView.findViewById(R.id.plate_1_percentage);
        // Plate 2
        final TextView plate2Pounds = (TextView) mainView.findViewById(R.id.plate_2_pounds);
        final TextView plate2LastFill = (TextView) mainView.findViewById(R.id.plate_2_last_fill);
        final Switch plate2Status = (Switch) mainView.findViewById(R.id.plate_2_status);
        final TextView plate2Percentage = (TextView) mainView.findViewById(R.id.plate_2_percentage);

        //Get Spinners
        final DecoView mainContainerChart = (DecoView) mainView.findViewById(R.id.main_container_chart);
        final DecoView plate1Chart = (DecoView) mainView.findViewById(R.id.plate_1_chart);
        final DecoView plate2Chart = (DecoView) mainView.findViewById(R.id.plate_2_chart);

// Create background track
        // Set Value event listeners
        // Main Container
        mainContainerPoundsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);

                updatePounds(text, mainContainerPounds, mainContainerPercentage, mainContainerChart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Plate 1
        plate1PoundsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);

                updatePounds(text, plate1Pounds, plate1Percentage, plate1Chart);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        plate1StatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean status = dataSnapshot.getValue(Boolean.class);

                plate1Status.setChecked(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        plate1LastFillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);

                plate1LastFill.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Plate 2
        plate2PoundsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);

                updatePounds(text, plate2Pounds, plate2Percentage, plate2Chart);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        plate2StatusRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Boolean status = dataSnapshot.getValue(Boolean.class);

                plate2Status.setChecked(status);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        plate2LastFillRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);

                plate2LastFill.setText(text);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Set Value Change Listener
        // Plate 1
        plate1Status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                plate1StatusRef.setValue(isChecked);
            }
        });
        // Plate 2
        plate2Status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                plate2StatusRef.setValue(isChecked);
            }
        });

        return mainView;
    }

    public void updatePounds(String value, TextView poundsView, TextView percentageView, DecoView containerChart) {
        poundsView.setText(value);
        percentageView.setText(value + "%");

        containerChart.deleteAll();
        containerChart.addSeries(new SeriesItem.Builder(Color.parseColor("#004ac1"))
                .setRange(0, 100, parseInt(value))
                .setLineWidth(32f)
                .setCapRounded(false)
                .build()
        );
    }

    public String getPercentage(float p, float x) {
        return String.format("%.2f", (p/100)*x);
    }
}
