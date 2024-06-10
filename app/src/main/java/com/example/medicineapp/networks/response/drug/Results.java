package com.example.medicineapp.networks.response.drug;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Results {
    @SerializedName("spl_product_data_elements")
    private List<String> splProductDataElements;

    @SerializedName("active_ingredient")
    private List<String> activeIngredient;

    @SerializedName("purpose")
    private List<String> purpose;

    @SerializedName("indications_and_usage")
    private List<String> indicationsAndUsage;

    @SerializedName("warnings")
    private List<String> warnings;

    @SerializedName("do_not_use")
    private List<String> doNotUse;

    @SerializedName("ask_doctor")
    private List<String> askDoctor;

    @SerializedName("ask_doctor_or_pharmacist")
    private List<String> askDoctorOrPharmacist;

    @SerializedName("stop_use")
    private List<String> stopUse;

    @SerializedName("pregnancy_or_breast_feeding")
    private List<String> pregnancyOrBreastFeeding;

    @SerializedName("keep_out_of_reach_of_children")
    private List<String> keepOutOfReachOfChildren;

    @SerializedName("dosage_and_administration")
    private List<String> dosageAndAdministration;

    @SerializedName("storage_and_handling")
    private List<String> storageAndHandling;

    @SerializedName("inactive_ingredient")
    private List<String> inactiveIngredient;

    @SerializedName("questions")
    private List<String> questions;

    @SerializedName("package_label_principal_display_panel")
    private List<String> packageLabelPrincipalDisplayPanel;

    @SerializedName("set_id")
    private String setId;

    @SerializedName("id")
    private String id;

    @SerializedName("effective_time")
    private String effectiveTime;

    @SerializedName("version")
    private String version;

    @SerializedName("openfda")
    private Openfda openfda;

    public List<String> getSplProductDataElements() {
        return splProductDataElements;
    }

    public List<String> getActiveIngredient() {
        return activeIngredient;
    }

    public List<String> getPurpose() {
        return purpose;
    }

    public List<String> getIndicationsAndUsage() {
        return indicationsAndUsage;
    }

    public List<String> getWarnings() {
        return warnings;
    }

    public List<String> getDoNotUse() {
        return doNotUse;
    }

    public List<String> getAskDoctor() {
        return askDoctor;
    }

    public List<String> getAskDoctorOrPharmacist() {
        return askDoctorOrPharmacist;
    }

    public List<String> getStopUse() {
        return stopUse;
    }

    public List<String> getPregnancyOrBreastFeeding() {
        return pregnancyOrBreastFeeding;
    }

    public List<String> getKeepOutOfReachOfChildren() {
        return keepOutOfReachOfChildren;
    }

    public List<String> getDosageAndAdministration() {
        return dosageAndAdministration;
    }

    public List<String> getStorageAndHandling() {
        return storageAndHandling;
    }

    public List<String> getInactiveIngredient() {
        return inactiveIngredient;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public List<String> getPackageLabelPrincipalDisplayPanel() {
        return packageLabelPrincipalDisplayPanel;
    }

    public String getSetId() {
        return setId;
    }

    public String getId() {
        return id;
    }

    public String getEffectiveTime() {
        return effectiveTime;
    }

    public String getVersion() {
        return version;
    }

    public Openfda getOpenfda() {
        return openfda;
    }
}
