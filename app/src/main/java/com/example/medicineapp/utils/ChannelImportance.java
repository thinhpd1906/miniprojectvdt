package com.example.medicineapp.utils;

import android.app.NotificationManager;

public enum ChannelImportance {
    IMPORTANCE_HIGH(NotificationManager.IMPORTANCE_HIGH), IMPORTANCE_DEFAULT(NotificationManager.IMPORTANCE_DEFAULT), IMPORTANCE_LOW(NotificationManager.IMPORTANCE_LOW), IMPORTANCE_MIN(NotificationManager.IMPORTANCE_MIN);
    private ChannelImportance(int value) {
        this.value = value;
    }
    private final int value;
//
    public int getValue() {
        return value;
    }


}
