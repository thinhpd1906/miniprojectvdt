package com.example.medicineapp.networks;

import com.example.medicineapp.model.NotiModel;
import com.example.medicineapp.model.netWorksResponse.GetNotiDetailResponse;
import com.example.medicineapp.networks.request.UpdateNotiRequest;
import com.example.medicineapp.networks.response.AddTaskResponse;
import com.example.medicineapp.networks.response.DeleteNotiResponse;
import com.example.medicineapp.networks.response.ListTaskResponse;
import com.example.medicineapp.networks.response.UpdateNotiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ITaskService {
    @POST("api/v1/noti")
    Call<AddTaskResponse> createNoti(@Body NotiModel noti);
    @GET("/api/v1/noti/list")
    Call<ListTaskResponse> getAllNoti();
    @GET("/api/v1/noti/{parameter}")
    Call<GetNotiDetailResponse> GetNotiDetailResponse(@Path("parameter") Integer parameter);
    @PUT("/api/v1/noti/update")
    Call<UpdateNotiResponse> updateNoti(@Body UpdateNotiRequest updateNotiRequest);
    @DELETE("/api/v1/noti/delete/{id}")
    Call<DeleteNotiResponse> deleteNoti(@Path("id") Integer id);
}
