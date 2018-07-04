package com.example.labreu.farmchickenfeederapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;


public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Settings References
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mainContainerCapacityRef = dbRef.child("settings").child("mainContainerCapacity");
        DatabaseReference platesCapacityRef = dbRef.child("settings").child("platesCapacity");

        View mainView = inflater.inflate(R.layout.fragment_settings, container, false);

        return mainView;
    }
}
