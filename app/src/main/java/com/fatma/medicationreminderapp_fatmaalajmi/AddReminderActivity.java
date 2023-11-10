package com.fatma.medicationreminderapp_fatmaalajmi;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.fatma.medicationreminderapp_fatmaalajmi.databinding.ActivityAddReminderBinding;
import com.fatma.medicationreminderapp_fatmaalajmi.models.ReminderModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class AddReminderActivity extends AppCompatActivity {

    private ActivityAddReminderBinding b;
    int timePickerHour = 30;
    int timePickerMinute = 0;
    String selectedDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAddReminderBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.setInventoryReminderTextView.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select Date")
                    .build();

            // Set listener for date selection
            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Handle the selected date, for example, update the TextView
                selectedDate = datePicker.getHeaderText();
                b.setInventoryReminderTextView.setText("Remind on: " + selectedDate);
            });

            // Show the MaterialDatePicker
            datePicker.show(getSupportFragmentManager(), "datePicker");
        });

        b.setMedicineReminderTimeTv.setOnClickListener(v -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12) // Initial hour value
                    .setMinute(0) // Initial minute value
                    .setTitleText("Select Time")
                    .build();

            // Set listener for positive button click (OK button)
            timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePickerHour = timePicker.getHour();
                    timePickerMinute = timePicker.getMinute();

                    // Handle the selected time, for example, update the TextView
                    String selectedTime = String.format("%02d:%02d", timePickerHour, timePickerMinute);
                    b.setMedicineReminderTimeTv.setText("Remind daily at: " + selectedTime);
                }
            });

            // Show the MaterialTimePicker
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });

        b.setInventoryReminderSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                b.setInventoryReminderTextView.setVisibility(View.VISIBLE);
            } else {
                b.setInventoryReminderTextView.setVisibility(View.GONE);
            }

        });

        b.saveBtn.setOnClickListener(v -> {

            String name = b.medicationNameEt.getText().toString();
            String unit = b.medicationUnitEt.getText().toString();
            String dose = b.medicationDoseEt.getText().toString();
            String totalAmount = b.medicationAmountEt.getText().toString();
            String threshold = b.medicationThresholdEt.getText().toString();

            if (name.isEmpty())
                return;
            if (unit.isEmpty())
                return;
            if (dose.isEmpty())
                return;
            if (totalAmount.isEmpty())
                return;
            if (threshold.isEmpty())
                return;
            if (b.setInventoryReminderSwitch.isChecked())
                if (selectedDate.isEmpty())
                    return;
            if (timePickerHour == 30)
                return;


            ReminderModel reminderModel = new ReminderModel();
            reminderModel.medicationName = name;
            reminderModel.medicationUnit = unit;
            reminderModel.medicationDose = dose;
            reminderModel.medicationTotalAmount = totalAmount;
            reminderModel.medicationThreshold = threshold;
            reminderModel.reminderTime = timePickerHour+":"+timePickerMinute;
            reminderModel.remindRefill =
        });
    }
}
