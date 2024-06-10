package com.example.medicineapp.networks;

import com.example.medicineapp.networks.response.drug.Drug;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IDrugService {
    @GET(".")
    Call<Drug> getDrug(@Query("search") String search);
}
