package com.example.medicineapp.model;

public class NotiModel {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private String title;
    private String description;
//    private String tag;
    private String repeat;
    private String priority;

//    public NotiModel(int year, int month, int day, int hour, int minute, String title, String description, String tag) {
//        this.year = year;
//        this.month = month;
//        this.day = day;
//        this.hour = hour;
//        this.minute = minute;
//        this.title = title;
//        this.description = description;
//        this.tag = tag;
//    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

//    public String getTag() {
//        return tag;
//    }

    public String getRepeat() {
        return repeat;
    }

    public String getPriority() {
        return priority;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

//    public void setTag(String tag) {
//        this.tag = tag;
//    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
