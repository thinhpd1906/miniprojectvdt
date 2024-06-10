package com.example.medicineapp.flagment.task;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.medicineapp.R;
import com.example.medicineapp.RoomDB.noti.NotiEntity;
import com.example.medicineapp.RoomDB.noti.NotiRepository;
import com.example.medicineapp.adapter.TaskAdapter;
import com.example.medicineapp.interfaces.IDeleteNoti;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class TaskHome extends Fragment {
    private List<NotiEntity> listTaskModel = new ArrayList<NotiEntity>();
    TaskAdapter taskAdapter;
    private FloatingActionButton addButton;
    private NavController controller;
    private NavHostFragment navHostFragment;
    IDeleteNoti iDeleteNoti;
    NotiRepository notiRepo;
    public TaskHome() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        notiRepo = new NotiRepository(getActivity());
        View rootView = inflater.inflate(R.layout.fragment_task_home, container, false);
        RecyclerView listTaskView =(RecyclerView) rootView.findViewById(R.id.listTask);
        listTaskView.setLayoutManager(new LinearLayoutManager(getActivity()));

        taskAdapter = new TaskAdapter(listTaskModel, new IDeleteNoti() {
            @Override
            public void onDeleteClick(Integer id) {
                iDeleteNoti.onDeleteClick(id);
                for (NotiEntity noti : listTaskModel) {
                    if (noti.id == id) {
                        listTaskModel.remove(noti);
                        notiRepo.delete(noti);
                        taskAdapter.notifyDataSetChanged();
                        break;
                    }
                }
            }
        });
        listTaskView.setAdapter(taskAdapter);
        listTaskView.setItemAnimator(new DefaultItemAnimator());
        addButton = (FloatingActionButton) rootView.findViewById(R.id.floating_action_button);
        List<NotiEntity> notiList = notiRepo.getAllNotis();
        if (!listTaskModel.isEmpty()) {
            listTaskModel.clear();
        }
        if (notiList != null && !notiList.isEmpty()) {
            listTaskModel.addAll(notiList);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        controller =
                (NavController) NavHostFragment.findNavController(this);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controller.navigate(R.id.fragmentAddTask);
            }
        });
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        iDeleteNoti = (IDeleteNoti) context;
    }
}