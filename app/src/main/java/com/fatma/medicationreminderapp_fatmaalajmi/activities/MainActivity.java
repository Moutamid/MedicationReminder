package com.fatma.medicationreminderapp_fatmaalajmi.activities;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.R;
import com.fatma.medicationreminderapp_fatmaalajmi.databinding.ActivitySplashBinding;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.AllRemindersFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.ContactFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.HomeFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.LanguageFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.MedicationFragment;
import com.fxn.stash.Stash;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ActivitySplashBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Constants.setAppLocale(this, Stash.getString(Constants.CURRENT_LANGUAGE, "en"));
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        b = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        // Setup the action bar with the drawer toggle
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setup the navigation view item click listener
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation item clicks here
                int itemId = item.getItemId();
                if (itemId == R.id.nav_item_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                } else if (itemId == R.id.nav_item_all_reminders) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllRemindersFragment()).commit();
                } else if (itemId == R.id.nav_item_medication) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MedicationFragment()).commit();
                } else if (itemId == R.id.nav_item_language) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LanguageFragment()).commit();
                } else if (itemId == R.id.nav_item_contact) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactFragment()).commit();
                }

                // Close the drawer when an item is selected
                drawer.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        if (Stash.getBoolean(Constants.IS_LANGUAGE_SELECTED, false)) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllRemindersFragment()).commit();
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        }
        if (Build.VERSION.SDK_INT > 32) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.POST_NOTIFICATIONS,
                                android.Manifest.permission.SCHEDULE_EXACT_ALARM,
                                android.Manifest.permission.USE_EXACT_ALARM}, 1234);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.SCHEDULE_EXACT_ALARM)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.SCHEDULE_EXACT_ALARM, android.Manifest.permission.USE_EXACT_ALARM}, 1234);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

//        Toast.makeText(MainActivity.this, "other", Toast.LENGTH_SHORT).show();

        return super.onOptionsItemSelected(item);
    }

    public void goToLanguageFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LanguageFragment()).commit();
    }

    public void goToAllRemindersFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllRemindersFragment()).commit();
    }


    @Override
    public void onBackPressed() {
        // Close the drawer when the back button is pressed
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}