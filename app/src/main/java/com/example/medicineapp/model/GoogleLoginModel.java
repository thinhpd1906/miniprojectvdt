package com.example.medicineapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GoogleLoginModel {
    @SerializedName("googleToken")
    @Expose
    public String googleToken;

    public GoogleLoginModel(String googleToken) {
        this.googleToken = googleToken;
    }
}
