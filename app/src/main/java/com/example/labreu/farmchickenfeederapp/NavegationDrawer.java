package com.example.labreu.farmchickenfeederapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class NavegationDrawer extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navegation_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();

        final View loadingScreen = findViewById(R.id.loadingScreen);

        DatabaseReference dbRef2 = FirebaseDatabase.getInstance().getReference();
        DatabaseReference actions = dbRef.child("actions");

        actions.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> data = (Map<String, String>) dataSnapshot.getValue();

                Boolean isRefillingPlate1 = getBoolean(data.get("refillPlate1"));
                Boolean isRefillingPlate2 = getBoolean(data.get("refillPlate2"));
                Boolean isCalibrating = getBoolean(data.get("calibrate"));

                if (isRefillingPlate1 || isRefillingPlate2 || isCalibrating) {
                    loadingScreen.setVisibility(View.VISIBLE);
                } else {
                    loadingScreen.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container, new WeightMonitorFragment()).commit();
        toolbar.setTitle("Weight Monitor");

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (!item.isChecked()) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            if (id == R.id.weight_monitor) {
                fragmentManager.beginTransaction().replace(R.id.main_container, new WeightMonitorFragment()).commit();
                toolbar.setTitle("Weight Monitor");
            } else if (id == R.id.schedules) {
                fragmentManager.beginTransaction().replace(R.id.main_container, new SchedulesFragment()).commit();
                toolbar.setTitle("Schedules");

            } else if (id == R.id.reports) {
                fragmentManager.beginTransaction().replace(R.id.main_container, new ReportsFragment()).commit();
                toolbar.setTitle("Reports");

            } else if (id == R.id.settings) {
                fragmentManager.beginTransaction().replace(R.id.main_container, new SettingsFragment()).commit();
                toolbar.setTitle("Settings");
            }
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Boolean getBoolean(String value) {
        if (value.indexOf("true") != -1) {
            return true;
        } else {
            return false;
        }
    }
}
