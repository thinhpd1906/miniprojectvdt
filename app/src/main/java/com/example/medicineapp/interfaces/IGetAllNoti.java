package com.example.medicineapp.interfaces;

import com.example.medicineapp.model.TaskModel;

import java.util.List;

public interface IGetAllNoti {
    void onGetAllNotiReceived(List<TaskModel> listTask);
}
