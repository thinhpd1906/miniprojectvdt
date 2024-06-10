package com.example.medicineapp.notification;

import com.example.medicineapp.utils.ChannelImportance;

public class Channel {
    public Channel(String id, String name, String description, ChannelImportance important) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.important = important;
    }

    public String getId() {
        return id;
    }



    public String getName() {
        return name;
    }



    public String getDescription() {
        return description;
    }


    public ChannelImportance getImportant() {
        return important;
    }


    private String id;
    private String name;
    private String description;
    private ChannelImportance important;
}