package com.fatma.medicationreminderapp_fatmaalajmi;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.fatma.medicationreminderapp_fatmaalajmi.databinding.ActivitySplashBinding;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.AllRemindersFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.ContactFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.HomeFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.LanguageFragment;
import com.fatma.medicationreminderapp_fatmaalajmi.fragments.MedicationFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;

    private ActivitySplashBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                switch (item.getItemId()) {
                    case R.id.nav_item_home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                        break;

                    case R.id.nav_item_all_reminders:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllRemindersFragment()).commit();
                        break;

                    case R.id.nav_item_medication:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MedicationFragment()).commit();
                        break;

                    case R.id.nav_item_language:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LanguageFragment()).commit();
                        break;

                    case R.id.nav_item_contact:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ContactFragment()).commit();
                        break;
                }

                // Close the drawer when an item is selected
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }

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