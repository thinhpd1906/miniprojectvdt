package com.example.medicineapp.networks.request;

public class UpdateNotiRequest {
    public UpdateNotiRequest(Integer id, int day, int month, int year, int hour, int minute, String title, String description, String tag) {
        this.id = id;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.description = description;
        this.tag = tag;
    }

    public Integer id;
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minute;

    public String title;
    public String description;
    public String tag;
}
