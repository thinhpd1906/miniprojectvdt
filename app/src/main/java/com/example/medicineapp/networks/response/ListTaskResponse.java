package com.example.medicineapp.networks.response;

import com.example.medicineapp.model.TaskModel;

import java.util.List;

public class ListTaskResponse {
    private List<TaskModel> data;

    public List<TaskModel> getData() {
        return data;
    }

    public void setData(List<TaskModel> data) {
        this.data = data;
    }
}
