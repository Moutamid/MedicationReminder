package com.fatma.medicationreminderapp_fatmaalajmi.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.databinding.ActivityMedicineDetailBinding;

public class MedicineDetailActivity extends AppCompatActivity {

    private ActivityMedicineDetailBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMedicineDetailBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.detailTextView.setText(getIntent().getStringExtra(Constants.PARAMS));

    }

}
