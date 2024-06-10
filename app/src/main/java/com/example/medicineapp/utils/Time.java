package com.example.medicineapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class Time {

    private Time() {
        throw new IllegalStateException("Utility class");
    }

    public static String getCurrentTimeStamp() {
        SimpleDateFormat sdf = null;
        sdf = new SimpleDateFormat("YYYY/MM/dd HH:mm");
        Date now = new Date();
        String strDate = sdf.format(now);
        return strDate;
    }
    public static long getTimeMilSeconds(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            Date date = sdf.parse(time);
            long millis = date.getTime();
            return millis;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static LocalDateTime convertToLocalDateTime(String time) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
            LocalDateTime datetime = LocalDateTime.parse(time, formatter);
            return datetime;
    }
}
