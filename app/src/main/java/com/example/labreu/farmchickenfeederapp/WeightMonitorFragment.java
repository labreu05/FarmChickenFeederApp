package com.example.labreu.farmchickenfeederapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import org.w3c.dom.Text;

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

        // Get views
        final View mainView = inflater.inflate(R.layout.fragment_weight_monitor, container, false);
        final TextView mainContainerPounds = (TextView) mainView.findViewById(R.id.pounds);
        // Plate 1
        final TextView plate1Pounds = (TextView) mainView.findViewById(R.id.plate_1_pounds);
        final TextView plate1LastFill = (TextView) mainView.findViewById(R.id.plate_1_last_fill);
        final Switch plate1Status = (Switch) mainView.findViewById(R.id.plate_1_status);
        // Plate 2
        final TextView plate2Pounds = (TextView) mainView.findViewById(R.id.plate_2_pounds);
        final TextView plate2LastFill = (TextView) mainView.findViewById(R.id.plate_2_last_fill);
        final Switch plate2Status = (Switch) mainView.findViewById(R.id.plate_2_status);

        // Set Value event listeners
        // Main Container
        mainContainerPoundsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String text = dataSnapshot.getValue(String.class);

                mainContainerPounds.setText(text);
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

                plate1Pounds.setText(text);
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

                plate2Pounds.setText(text);
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
}
