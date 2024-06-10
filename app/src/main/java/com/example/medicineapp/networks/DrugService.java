package com.example.medicineapp.networks;

import android.content.Context;
import android.util.Log;

import com.example.medicineapp.interfaces.IGetDrug;
import com.example.medicineapp.networks.response.AddTaskResponse;
import com.example.medicineapp.networks.response.drug.Drug;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DrugService {
    private Context context;
    private IDrugService drugService;
    public DrugService(Context context) {
        this.context = context;
        drugService = Base.getClient(context).create(IDrugService.class);
    }
    public void searchDrug(String drugName, IGetDrug getDrug) {
        String searchQuery = "openfda.generic_name:\"" + drugName + "\"";
        Call<Drug> call = drugService.getDrug(searchQuery);
        call.enqueue(new Callback<Drug>() {
            @Override
            public void onResponse(Call<Drug> call, Response<Drug> response) {
                if (response.isSuccessful()) {
                    Drug drug = response.body();
                    getDrug.onGetDrug(drug);
                } else {
                    // Xử lý lỗi
                }
            }

            @Override
            public void onFailure(Call<Drug> call, Throwable t) {
//                Log.v("ok", "fail");
            }
        });
    }
}
