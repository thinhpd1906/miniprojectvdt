package com.example.medicineapp.networks;

import android.util.Log;

import com.example.medicineapp.interfaces.IGetAllNoti;
import com.example.medicineapp.interfaces.IGetDetailNoti;
import com.example.medicineapp.model.NotiModel;
import com.example.medicineapp.model.TaskModel;
import com.example.medicineapp.model.netWorksResponse.GetNotiDetailResponse;
import com.example.medicineapp.networks.request.UpdateNotiRequest;
import com.example.medicineapp.networks.response.AddTaskResponse;
import com.example.medicineapp.networks.response.DeleteNotiResponse;
import com.example.medicineapp.networks.response.ListTaskResponse;
import com.example.medicineapp.networks.response.UpdateNotiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//
//public class TaskService {
//    ITaskService notiService = Base.getClient().create(ITaskService.class);
//    public void createNoti (NotiModel notiModel, IGetDetailNoti getDetailNoti) {
//        Call<AddTaskResponse> call = notiService.createNoti(notiModel);
//        call.enqueue(new Callback<AddTaskResponse>() {
//            @Override
//            public void onResponse(Call<AddTaskResponse> call, Response<AddTaskResponse> response) {
//                Log.v("create noti", "success");
//                TaskModel taskModel = response.body().getData();
//                getDetailNoti.getDetailNoti(taskModel);
//            }
//
//            @Override
//            public void onFailure(Call<AddTaskResponse> call, Throwable t) {
//                Log.v("create noti", "failue");
//            }
//        });
//    }
//    public void getAllNoti(IGetAllNoti getAllNotification) {
//        Call<ListTaskResponse> call = notiService.getAllNoti();
//        call.enqueue(new Callback<ListTaskResponse>() {
//            @Override
//            public void onResponse(Call<ListTaskResponse> call, Response<ListTaskResponse> response) {
//                Log.v("api get list task", "success");
//                List<TaskModel> listTask = response.body().getData();
//                getAllNotification.onGetAllNotiReceived(listTask);
//            }
//
//            @Override
//            public void onFailure(Call<ListTaskResponse> call, Throwable t) {
//                Log.e("api get list task","failed");
//            }
//        });
//    }
//    public void getNotiDetail(Integer id, IGetDetailNoti getDetailNoti) {
//        Call<GetNotiDetailResponse> call = notiService.GetNotiDetailResponse(id);
//        call.enqueue(new Callback<GetNotiDetailResponse>() {
//            @Override
//            public void onResponse(Call<GetNotiDetailResponse> call, Response<GetNotiDetailResponse> response) {
//                Log.v("get detail tag", "success");
//                TaskModel taskModel = response.body().getData();
//                getDetailNoti.getDetailNoti(taskModel);
//            }
//
//            @Override
//            public void onFailure(Call<GetNotiDetailResponse> call, Throwable t) {
//                Log.v("get detail tag", "failed");
//            }
//        });
//    }
//    public void updateNoti(UpdateNotiRequest updateNotiRequest) {
//        Call<UpdateNotiResponse> call = notiService.updateNoti(updateNotiRequest);
//        call.enqueue(new Callback<UpdateNotiResponse>() {
//            @Override
//            public void onResponse(Call<UpdateNotiResponse> call, Response<UpdateNotiResponse> response) {
//                Log.v("update noti", "success");
//            }
//
//            @Override
//            public void onFailure(Call<UpdateNotiResponse> call, Throwable t) {
//                Log.v("update noti", "failed");
//            }
//        });
//    }
//    public void deleteNoti(Integer id) {
//        Call<DeleteNotiResponse> call = notiService.deleteNoti(id);
//        call.enqueue(new Callback<DeleteNotiResponse>() {
//            @Override
//            public void onResponse(Call<DeleteNotiResponse> call, Response<DeleteNotiResponse> response) {
//                Log.v("delete noti", "success");
//            }
//
//            @Override
//            public void onFailure(Call<DeleteNotiResponse> call, Throwable t) {
//                Log.v("delete noti", "failed");
//            }
//        });
//    }
//}
