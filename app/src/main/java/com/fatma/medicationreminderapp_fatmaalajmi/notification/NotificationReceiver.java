package com.fatma.medicationreminderapp_fatmaalajmi.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fatma.medicationreminderapp_fatmaalajmi.Constants;
import com.fatma.medicationreminderapp_fatmaalajmi.R;
import com.fatma.medicationreminderapp_fatmaalajmi.activities.MainActivity;

import java.util.Calendar;

public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String reminderType = intent.getStringExtra(Constants.REMINDER_TYPE);
        String message = intent.getStringExtra(Constants.MESSAGE);
        NotificationHelper notificationHelper = new NotificationHelper(context);

        if (reminderType.equals(Constants.MEDICINE_REMINDER)) {
            // MEDICINE REMINDER

            notificationHelper.sendHighPriorityNotification(context.getString(R.string.medicine_reminder), message, MainActivity.class);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.add(Calendar.HOUR_OF_DAY, 12);

            NotificationScheduler.scheduleNotification(context, calendar,
                    message, Constants.MEDICINE_REMINDER);

        } else {
            // INVENTORY REMINDER
            notificationHelper.sendHighPriorityNotification(context.getString(R.string.inventory_reminder), message, MainActivity.class);
        }
    }
}
