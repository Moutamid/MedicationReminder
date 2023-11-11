package com.fatma.medicationreminderapp_fatmaalajmi.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.R;
import com.fatma.medicationreminderapp_fatmaalajmi.databinding.ActivityAddReminderBinding;
import com.fatma.medicationreminderapp_fatmaalajmi.models.ReminderModel;
import com.fatma.medicationreminderapp_fatmaalajmi.notification.NotificationScheduler;
import com.fxn.stash.Stash;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;

public class AddReminderActivity extends AppCompatActivity {

    private ActivityAddReminderBinding b;
    int timePickerHour = 30;
    int timePickerMinute = 0;
    String selectedDate = "";
    long selectedDateMillis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAddReminderBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        b.setInventoryReminderTextView.setOnClickListener(v -> {
            MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText(getString(R.string.select_date))
                    .build();

            // Set listener for date selection
            datePicker.addOnPositiveButtonClickListener(selection -> {
                // Handle the selected date, for example, update the TextView
                selectedDate = datePicker.getHeaderText();
                b.setInventoryReminderTextView.setText(getString(R.string.remind_on) + selectedDate);
                selectedDateMillis = selection;
            });

            // Show the MaterialDatePicker
            datePicker.show(getSupportFragmentManager(), getString(R.string.datepicker));
        });

        b.setMedicineReminderTimeTv.setOnClickListener(v -> {
            MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setHour(12) // Initial hour value
                    .setMinute(0) // Initial minute value
                    .setTitleText(R.string.select_time)
                    .build();

            // Set listener for positive button click (OK button)
            timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timePickerHour = timePicker.getHour();
                    timePickerMinute = timePicker.getMinute();

                    // Handle the selected time, for example, update the TextView
                    String selectedTime = String.format("%02d:%02d", timePickerHour, timePickerMinute);
                    b.setMedicineReminderTimeTv.setText(getString(R.string.remind_daily_at) + selectedTime);
                }
            });

            // Show the MaterialTimePicker
            timePicker.show(getSupportFragmentManager(), getString(R.string.timepicker));
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
            reminderModel.notificationID = Constants.getNewID();
            reminderModel.medicationName = name;
            reminderModel.medicationUnit = unit;
            reminderModel.medicationDose = dose;
            reminderModel.medicationTotalAmount = totalAmount;
            reminderModel.medicationThreshold = threshold;
            reminderModel.reminderTime = timePickerHour + ":" + timePickerMinute;
            reminderModel.remindRefill = b.setInventoryReminderSwitch.isChecked() + "";
            reminderModel.inventoryReminderDate = selectedDate;

            // MEDICINE LOW REMINDER
            Calendar medicineReminderCalender = Calendar.getInstance();
            medicineReminderCalender.setTimeInMillis(System.currentTimeMillis());

            medicineReminderCalender.set(Calendar.HOUR_OF_DAY, timePickerHour);
            medicineReminderCalender.set(Calendar.MINUTE, timePickerMinute);
            medicineReminderCalender.set(Calendar.SECOND, 0);

            if (Calendar.getInstance().after(medicineReminderCalender)) {
                medicineReminderCalender.add(Calendar.DAY_OF_YEAR, 1);
            }

            String medicineReminderMsg = getString(R.string.it_s_time_to_take_your)
                    + name
                    + getString(R.string.medicines_of)
                    + reminderModel.reminderTime
                    + " (" + dose + ")";

            NotificationScheduler.scheduleNotification(
                    AddReminderActivity.this, medicineReminderCalender,
                    medicineReminderMsg, Constants.MEDICINE_REMINDER);

            if (b.setInventoryReminderSwitch.isChecked()) {
                // INVENTORY LOW REMINDER
                String inventoryLowMsg = getString(R.string.medication_inventory_is_low_of) + name;

                Calendar inventoryCalender = Calendar.getInstance();
                inventoryCalender.setTimeInMillis(selectedDateMillis);
                if (Calendar.getInstance().after(inventoryCalender)) {
                    inventoryCalender.add(Calendar.DAY_OF_YEAR, 1);
                }

                NotificationScheduler.scheduleNotification(
                        AddReminderActivity.this, inventoryCalender,
                        inventoryLowMsg, Constants.INVENTORY_REMINDER);
            }
            // SAVE REMINDER LOCALLY SO DISPLAYED LATER
            ArrayList<ReminderModel> reminderModelArrayList =
                    Stash.getArrayList(Constants.REMINDERS_LIST, ReminderModel.class);

            reminderModelArrayList.add(reminderModel);

            Stash.put(Constants.REMINDERS_LIST, reminderModelArrayList);

            Toast.makeText(getApplicationContext(), getString(R.string.saved), Toast.LENGTH_SHORT).show();
            finish();

        });
    }

}
