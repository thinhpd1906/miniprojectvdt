package com.example.medicineapp.utils;

import com.example.medicineapp.notification.Channel;

public class ChannelDefault {
    public static final Channel urgentChannel = new Channel("urgent", "urgent", "most important alarm", ChannelImportance.IMPORTANCE_HIGH);
    public static final Channel importantChannel = new Channel("important", "important", "important alarm", ChannelImportance.IMPORTANCE_DEFAULT);
    public static final Channel mediumChannel = new Channel("medium", "medium", "medium important alarm", ChannelImportance.IMPORTANCE_LOW);
    public static final Channel lowChannel = new Channel("low", "low", "low alarm", ChannelImportance.IMPORTANCE_MIN);

}
