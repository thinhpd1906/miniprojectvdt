package com.example.medicineapp.adapter;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medicineapp.R;
import com.example.medicineapp.RoomDB.noti.NotiEntity;
import com.example.medicineapp.flagment.task.TaskHomeDirections;
import com.example.medicineapp.interfaces.IDeleteNoti;
import com.example.medicineapp.utils.Time;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolderView> {
    private IDeleteNoti iDeleteNoti;
    public List<NotiEntity> listTaskModel;

    public TaskAdapter(List<NotiEntity> listTaskModel, IDeleteNoti iDeleteNoti) {
        this.listTaskModel = listTaskModel;
        this.iDeleteNoti = iDeleteNoti;
    }

    @NonNull
    @Override
    public TaskHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskHolderView(layout);
    }
    @Override
    public void onBindViewHolder(@NonNull TaskHolderView taskHolder, int position) {

        taskHolder.exDate.setText(listTaskModel.get(position).time);
        taskHolder.title.setText(listTaskModel.get(position).title);
        taskHolder.status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = taskHolder.getAdapterPosition();
                Integer id = listTaskModel.get(adapterPosition).id;
                TaskHomeDirections.ActionTaskHomeToFragmentAddTask action = TaskHomeDirections.actionTaskHomeToFragmentAddTask();
                action.setId(id);
                Navigation.findNavController(view).navigate(action);
            }
        });
        taskHolder.exDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = taskHolder.getAdapterPosition();
                Integer id = listTaskModel.get(adapterPosition).id;
                TaskHomeDirections.ActionTaskHomeToFragmentAddTask action = TaskHomeDirections.actionTaskHomeToFragmentAddTask();
                action.setId(id);
                Navigation.findNavController(view).navigate(action);
            }
        });
        taskHolder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = taskHolder.getAdapterPosition();
                Integer id = listTaskModel.get(adapterPosition).id;
                TaskHomeDirections.ActionTaskHomeToFragmentAddTask action = TaskHomeDirections.actionTaskHomeToFragmentAddTask();
                action.setId(id);
                Navigation.findNavController(view).navigate(action);
            }
        });
        taskHolder.deleteNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(iDeleteNoti != null) {
                    Integer id = listTaskModel.get(position).id;
                    iDeleteNoti.onDeleteClick(id);
                }
            }
        });
        if (Time.convertToLocalDateTime(Time.getCurrentTimeStamp()).isAfter(Time.convertToLocalDateTime(listTaskModel.get(position).time)) && (listTaskModel.get(position).repeat.isEmpty())) {
            taskHolder.exDate.setTextColor(Color.parseColor("#8a8888"));
            taskHolder.title.setTextColor(Color.parseColor("#8a8888"));
            taskHolder.status.setColorFilter(Color.parseColor("#8a8888"));
        }
    }

    @Override
    public int getItemCount() {
        return listTaskModel.size();
    }

    public static class TaskHolderView extends RecyclerView.ViewHolder {
        public View row;
        public TextView exDate;
        public ImageButton status;
        public TextView title;
        public ImageButton deleteNoti;
        public TaskHolderView(View row) {
            super(row);
            this.row = row;
            status = row.findViewById(R.id.status);
            exDate = row.findViewById(R.id.exDate);
            deleteNoti = row.findViewById(R.id.deleteNoti);
            title = row.findViewById(R.id.title);
        }
    }
}
