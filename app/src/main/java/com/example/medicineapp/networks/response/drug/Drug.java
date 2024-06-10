package com.example.medicineapp.networks.response.drug;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Drug {
    @SerializedName("meta")
    private Meta meta;

    @SerializedName("results")
    private List<Results> results;

    public Meta getMeta() {
        return meta;
    }

    public List<Results> getResults() {
        return results;
    }
}
