package com.fatma.medicationreminderapp_fatmaalajmi;

import android.content.Context;
import android.content.res.Configuration;

import com.fxn.stash.Stash;

import java.util.Locale;

public class Constants {
    public static final String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";
    public static final String LANGUAGE_ARABIC = "ar";
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String REMINDERS_LIST = "REMINDERS_LIST";
    public static final String REMINDER_TYPE = "REMINDER_TYPE";
    public static final String MEDICINE_REMINDER = "MEDICINE_REMINDER";
    public static final String INVENTORY_REMINDER = "INVENTORY_REMINDER";
    public static final String MESSAGE = "MESSAGE";
    public static final String IS_LANGUAGE_SELECTED = "IS_LANGUAGE_SELECTED";
    public static final String PARAMS = "PARAMS";
    private static final String NOTIFICATION_ID = "NOTIFICATION_ID";

    public static int getNewID(){
        int value = Stash.getInt(Constants.NOTIFICATION_ID, 0);
        value++;
        Stash.put(Constants.NOTIFICATION_ID, value);
        return value;
    }

    public static int getRandomNumber(){
        int value = Stash.getInt("randomNumber", 0);
        value++;
        Stash.put("randomNumber", value);
        return value;
    }

    public static void setAppLocale(Context context, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);

        context.getResources().updateConfiguration(configuration, context.getResources().getDisplayMetrics());
    }

}
