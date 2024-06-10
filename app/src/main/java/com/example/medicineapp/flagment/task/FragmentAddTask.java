package com.example.medicineapp.flagment.task;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.medicineapp.R;
import com.example.medicineapp.RoomDB.noti.NotiEntity;
import com.example.medicineapp.RoomDB.noti.NotiRepository;
import com.example.medicineapp.interfaces.IDetailNoti;
import com.example.medicineapp.interfaces.IFragmentListener;
import com.example.medicineapp.interfaces.INoti;
import com.example.medicineapp.interfaces.OnDismissListener;
import com.example.medicineapp.model.NotiModel;
import com.example.medicineapp.utils.Time;
import com.example.medicineapp.viewModel.CalenderViewModel;
import java.time.LocalDateTime;


public class FragmentAddTask extends Fragment  {
    SetTimeFragment setTimeFragment;
    TextView setTime;
    Button create;
    INoti notiContext;
    NotiModel notiModel = new NotiModel();
    EditText title;
    EditText description;
    private CalenderViewModel model;
    Integer id;
    private NavController controller;
    NotiRepository notiRepo;
    private IFragmentListener fragmentListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentListener.onFragmentChanged("fragmentAddTask");
        setTimeFragment = new SetTimeFragment(new OnDismissListener() {
            @Override
            public void onFragmentDismissed() {
                setTime.setText(model.getDateTimeString());
            }
        });
        notiRepo = new NotiRepository(getActivity());
        model = new ViewModelProvider(requireActivity()).get(CalenderViewModel.class);
        model.date.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer date) {
                if(date != null) {
                    notiModel.setDay(date);
                }
            }
        });
        model.month.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer month) {
                if(month != null) {
                    notiModel.setMonth(month);
                }
            }
        });
        model.year.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer year) {
                if(year != null) {
                    notiModel.setYear(year);
                }
            }
        });
        model.hour.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer hour) {
                if(hour != null) {
                    notiModel.setHour(hour);
                }
            }
        });
        model.minute.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer minute) {
                if(minute != null) {
                    notiModel.setMinute(minute);
                }
            }
        });
        model.repeat.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String repeat) {
                notiModel.setRepeat(repeat);
            }
        });

        View rootView = inflater.inflate(R.layout.fragment_add_task, container, false);
        Spinner spinnerPrioperty =  rootView.findViewById(R.id.spinner);
        setTime = rootView.findViewById(R.id.setTime);
        create = rootView.findViewById(R.id.create);
        title = rootView.findViewById(R.id.add_task_title);
        description = rootView.findViewById(R.id.task_description);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.prioperties_array, R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinnerPrioperty.setAdapter(adapter);

        id = FragmentAddTaskArgs.fromBundle(getArguments()).getId();
        controller =
                (NavController) NavHostFragment.findNavController(this);
        String defaultString = spinnerPrioperty.getSelectedItem().toString();
        notiModel.setPriority(defaultString);
        if(id != 0) {
            notiRepo.getDetail(id, new IDetailNoti() {
                @Override
                public void getDetailNoti(NotiEntity noti) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        title.setText(noti.title);
                        description.setText(noti.description);
                        setTime.setText(noti.time);
                        int priorityPosition = adapter.getPosition(noti.priority);
                        spinnerPrioperty.setSelection(priorityPosition);
                        LocalDateTime time = Time.convertToLocalDateTime(noti.time);
                        notiModel.setYear(time.getYear());
                        notiModel.setMonth(time.getMonthValue() -1);
                        notiModel.setDay(time.getDayOfMonth());
                        notiModel.setHour(time.getHour());
                        notiModel.setMinute(time.getMinute());
                        notiModel.setRepeat(noti.repeat);
                        notiModel.setTitle(noti.title);
                        notiModel.setDescription(noti.description);
                        setTimeFragment.setRepeat(noti.repeat);
                        model.year.setValue(time.getYear());
                        model.month.setValue(time.getMonthValue() -1);
                        model.date.setValue(time.getDayOfMonth());
                        model.hour.setValue(time.getHour());
                        model.minute.setValue(time.getMinute());
                        model.repeat.setValue(noti.repeat);
                    });
                }
            });
        } else {
            model.year.setValue(null);
            model.month.setValue(null);
            model.date.setValue(null);
            model.hour.setValue(null);
            model.minute.setValue(null);
        }

        spinnerPrioperty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedString = adapterView.getItemAtPosition(i).toString();
                notiModel.setPriority(selectedString);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTimeFragment.show(getActivity().getSupportFragmentManager(), setTimeFragment.getTag());
            }
        });
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notiModel.setTitle(title.getText().toString());
                notiModel.setDescription(description.getText().toString());
                notiContext.onDataPass(notiModel, id);
                controller.navigate(R.id.taskHome);
            }
        });
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkFieldsForEmptyValues();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };
        setTime.addTextChangedListener(textWatcher);
        title.addTextChangedListener(textWatcher);
        description.addTextChangedListener(textWatcher);
        return rootView;
    }
    private void checkFieldsForEmptyValues() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String timeText = setTime.getText().toString();
                String titleText = title.getText().toString();

                if (!timeText.isEmpty() && !titleText.isEmpty()) {
                    create.setEnabled(true);
                    create.setBackgroundColor(getResources().getColor(R.color.purple_500));
                    create.setTextColor(getResources().getColor(R.color.white));
                } else {
                    create.setEnabled(false);
                    create.setBackgroundColor(getResources().getColor(R.color.hint));
                    create.setTextColor(getResources().getColor(R.color.light_white));

                }
            }
        });
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notiContext = (INoti) context;
        fragmentListener = (IFragmentListener) context;
    }
}