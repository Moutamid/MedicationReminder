package com.fatma.medicationreminderapp_fatmaalajmi;

import com.fxn.stash.Stash;

public class Constants {
    public static final String CURRENT_LANGUAGE = "CURRENT_LANGUAGE";
    public static final String LANGUAGE_ARABIC = "LANGUAGE_ARABIC";
    public static final String LANGUAGE_ENGLISH = "LANGUAGE_ENGLISH";
    public static final String REMINDERS_LIST = "REMINDERS_LIST";
    public static final String REMINDER_TYPE = "REMINDER_TYPE";
    public static final String MEDICINE_REMINDER = "MEDICINE_REMINDER";
    public static final String INVENTORY_REMINDER = "INVENTORY_REMINDER";
    public static final String MESSAGE = "MESSAGE";
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
}
