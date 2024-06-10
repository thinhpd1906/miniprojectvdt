package com.example.medicineapp.networks.response.drug;

import com.google.gson.annotations.SerializedName;

public class ResultMeta {
    @SerializedName("skip")
    private int skip;

    @SerializedName("limit")
    private int limit;

    @SerializedName("total")
    private int total;
}
