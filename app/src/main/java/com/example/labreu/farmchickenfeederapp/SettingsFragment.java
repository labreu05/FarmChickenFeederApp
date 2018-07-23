package com.example.labreu.farmchickenfeederapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class SettingsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Settings References
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
        final DatabaseReference mainContainerCapacityRef = dbRef.child("settings").child("mainContainerCapacity");
        final DatabaseReference calibrateActionRef = dbRef.child("actions").child("calibrate");
        final DatabaseReference platesCapacityRef = dbRef.child("settings").child("platesCapacity");
        final DatabaseReference plate1CapacityRef = dbRef.child("plates").child("plate1").child("capacity");
        final DatabaseReference plate2CapacityRef = dbRef.child("plates").child("plate2").child("capacity");

        // Find Views
        final View mainView = inflater.inflate(R.layout.fragment_settings, container, false);
        final EditText mainContainerCapacity = mainView.findViewById(R.id.main_container_capacity);
        final View SettingsMainContainer = mainView.findViewById(R.id.settings_main_container);
        final EditText platesCapacity = mainView.findViewById(R.id.plates_capacity);
        Button calibrateButton = mainView.findViewById(R.id.calibrateButton);

        // Firebase Listeners
        mainContainerCapacityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                System.out.println(value);
                mainContainerCapacity.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        platesCapacityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                platesCapacity.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mainContainerCapacity.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mainContainerCapacity.onTouchEvent(motionEvent);
                mainContainerCapacity.setSelection(mainContainerCapacity.getText().length());
                return true;
            }
        });

        // Data change events
        mainContainerCapacity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String value = getWeight(mainContainerCapacity.getText().toString());

                    mainContainerCapacityRef.setValue(value);
                    mainContainerCapacity.setText(value);

                    return false;
                }
                return false;
            }
        });

        platesCapacity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String value = getWeight(platesCapacity.getText().toString());

                    platesCapacityRef.setValue(value);
                    plate1CapacityRef.setValue(value);
                    plate2CapacityRef.setValue(value);
                    platesCapacity.setText(value);

                    return false;
                }
                return false;
            }
        });

        calibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                builder.setMessage("Do you want calibrate the system?")
                        .setPositiveButton("Yes",  new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                calibrateActionRef.setValue("true");

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        })
                        .show();
            }
        });

        return mainView;
    }

    public String getWeight(String weight) {
        String value = weight.replaceAll("^0+", "");

        if (value.length() == 0) {
            return "0";
        } else {
            return value;
        }

    }
}
