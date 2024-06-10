package com.example.medicineapp.networks.response.drug;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Openfda {
    @SerializedName("application_number")
    private List<String> applicationNumber;

    @SerializedName("brand_name")
    private List<String> brandName;

    @SerializedName("generic_name")
    private List<String> genericName;

    @SerializedName("manufacturer_name")
    private List<String> manufacturerName;

    @SerializedName("product_ndc")
    private List<String> productNdc;

    @SerializedName("product_type")
    private List<String> productType;

    @SerializedName("route")
    private List<String> route;

    @SerializedName("substance_name")
    private List<String> substanceName;

    @SerializedName("rxcui")
    private List<String> rxcui;

    @SerializedName("spl_id")
    private List<String> splId;

    @SerializedName("spl_set_id")
    private List<String> splSetId;

    @SerializedName("package_ndc")
    private List<String> packageNdc;

    @SerializedName("is_original_packager")
    private List<Boolean> isOriginalPackager;

    @SerializedName("upc")
    private List<String> upc;

    @SerializedName("unii")
    private List<String> unii;

    public List<String> getApplicationNumber() {
        return applicationNumber;
    }

    public List<String> getBrandName() {
        return brandName;
    }

    public List<String> getGenericName() {
        return genericName;
    }

    public List<String> getManufacturerName() {
        return manufacturerName;
    }

    public List<String> getProductNdc() {
        return productNdc;
    }

    public List<String> getProductType() {
        return productType;
    }

    public List<String> getRoute() {
        return route;
    }

    public List<String> getSubstanceName() {
        return substanceName;
    }

    public List<String> getRxcui() {
        return rxcui;
    }

    public List<String> getSplId() {
        return splId;
    }

    public List<String> getSplSetId() {
        return splSetId;
    }

    public List<String> getPackageNdc() {
        return packageNdc;
    }

    public List<Boolean> getIsOriginalPackager() {
        return isOriginalPackager;
    }

    public List<String> getUpc() {
        return upc;
    }

    public List<String> getUnii() {
        return unii;
    }
}
