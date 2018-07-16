package com.example.labreu.farmchickenfeederapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.Map;

import static java.lang.Integer.parseInt;

public class WeightMonitorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Main Container References
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mainContainerRef = dbRef.child("mainContainer");

        // Plate 1 References
        DatabaseReference plate1Ref = dbRef.child("plates").child("plate1");
        final DatabaseReference plate1RefillActionRef = dbRef.child("actions").child("refillPlate1");
        final DatabaseReference plate1StatusRef = plate1Ref.child("isActive");
        // Plate 2 References
        DatabaseReference plate2Ref = dbRef.child("plates").child("plate2");
        final DatabaseReference plate2RefillActionRef = dbRef.child("actions").child("refillPlate2");
        final DatabaseReference plate2StatusRef = plate2Ref.child("isActive");

        // Get views
        final View mainView = inflater.inflate(R.layout.fragment_weight_monitor, container, false);
        final TextView mainContainerPounds = (TextView) mainView.findViewById(R.id.pounds);
        final TextView mainContainerPercentage = (TextView) mainView.findViewById(R.id.main_container_percentage);
        // Plate 1
        final TextView plate1Pounds = (TextView) mainView.findViewById(R.id.plate_1_pounds);
        final TextView plate1LastFill = (TextView) mainView.findViewById(R.id.plate_1_last_fill);
        final Switch plate1Status = (Switch) mainView.findViewById(R.id.plate_1_status);
        final TextView plate1Percentage = (TextView) mainView.findViewById(R.id.plate_1_percentage);
        final Button plate1RefillButton = (Button) mainView.findViewById(R.id.refill_plate_1);
        // Plate 2
        final TextView plate2Pounds = (TextView) mainView.findViewById(R.id.plate_2_pounds);
        final TextView plate2LastFill = (TextView) mainView.findViewById(R.id.plate_2_last_fill);
        final Switch plate2Status = (Switch) mainView.findViewById(R.id.plate_2_status);
        final TextView plate2Percentage = (TextView) mainView.findViewById(R.id.plate_2_percentage);
        final Button plate2RefillButton = (Button) mainView.findViewById(R.id.refill_plate_2);

        //Get Spinners
        final DecoView mainContainerChart = (DecoView) mainView.findViewById(R.id.main_container_chart);
        final DecoView plate1Chart = (DecoView) mainView.findViewById(R.id.plate_1_chart);
        final DecoView plate2Chart = (DecoView) mainView.findViewById(R.id.plate_2_chart);

        // Set Value event listeners

        // Main Container
        mainContainerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();
                Integer capacity = Integer.parseInt(data.get("capacity"));
                Float weight = Float.parseFloat(data.get("pounds"));

                updatePounds(weight, mainContainerPounds, mainContainerPercentage, mainContainerChart, capacity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Plate 1
        plate1Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();
                Boolean status = getBoolean(data.get("isActive"));
                Integer capacity = Integer.parseInt(data.get("capacity"));
                Float weight = Float.parseFloat(data.get("pounds"));
                String lastFill = data.get("lastFill");

                plate1Status.setChecked(status);
                plate1LastFill.setText(lastFill);
                updatePounds(weight, plate1Pounds, plate1Percentage, plate1Chart, capacity);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // Plate 2
        plate2Ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();
                System.out.println(data);
                Boolean status = getBoolean(data.get("isActive"));
                Integer capacity = Integer.parseInt(data.get("capacity"));
                Float weight = Float.parseFloat(data.get("pounds"));
                String lastFill = data.get("lastFill");
                
                plate2Status.setChecked(status);
                plate2LastFill.setText(lastFill);
                updatePounds(weight, plate2Pounds, plate2Percentage, plate2Chart, capacity);
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
                plate1StatusRef.setValue(Boolean.toString(isChecked));
            }
        });
        plate1RefillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plate1RefillActionRef.setValue("true");
            }
        });
        // Plate 2
        plate2Status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                plate2StatusRef.setValue(Boolean.toString(isChecked));
            }
        });
        plate2RefillButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                plate2RefillActionRef.setValue("true");
            }
        });

        return mainView;
    }

    public void updatePounds(Float value, TextView poundsView, TextView percentageView, DecoView containerChart, Integer capacity) {
        String percentage = getPercentage(value, capacity);
        poundsView.setText(Float.toString(value));
        percentageView.setText(percentage + "%");

        containerChart.deleteAll();
        containerChart.addSeries(new SeriesItem.Builder(Color.parseColor("#EE1C00"))
                .setRange(0, 100, (value))
                .setLineWidth(32f)
                .setCapRounded(false)
                .build()
        );
    }

    public String getPercentage(float p, float x) {
        return String.format("%.0f", (p/x)*100);
    }

    public Boolean getBoolean(String value) {
        if (value.indexOf("true") != -1) {
            return true;
        } else {
            return false;
        }
    }
}
