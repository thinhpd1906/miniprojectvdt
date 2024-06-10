package com.example.medicineapp.networks.response.drug;

import com.google.gson.annotations.SerializedName;

public class Meta {
    @SerializedName("disclaimer")
    private String disclaimer;

    @SerializedName("terms")
    private String terms;

    @SerializedName("license")
    private String license;

    @SerializedName("last_updated")
    private String lastUpdated;

    @SerializedName("results")
    private ResultMeta results;

}
