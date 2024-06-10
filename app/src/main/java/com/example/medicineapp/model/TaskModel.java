package com.example.medicineapp.model;

public class TaskModel {
    public TaskModel(Integer id, Integer day, Integer year, Integer hour, Integer minute, String title, String description, String tag, String expDate, String status) {
        this.id = id;
        this.day = day;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.expDate = expDate;
        this.status = status;
    }

    public Integer id;
    public Integer day;
    public Integer year;
    public Integer hour;
    public Integer minute;

    public String title;
    public String description;
    public String tag;
    public String expDate;
    public String status;

    public Integer getId() {
        return id;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getHour() {
        return hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getTag() {
        return tag;
    }

    public String getExpDate() {
        return expDate;
    }

    public String getStatus() {
        return status;
    }
}
