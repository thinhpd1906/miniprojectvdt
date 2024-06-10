package com.example.medicineapp.flagment.task;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.medicineapp.R;
import com.example.medicineapp.flagment.common.DatePickerFragment;
import com.example.medicineapp.flagment.common.TimePickerFragment;
import com.example.medicineapp.interfaces.INoti;
import com.example.medicineapp.interfaces.OnDismissListener;
import com.example.medicineapp.model.NotiModel;
import com.example.medicineapp.utils.Time;
import com.example.medicineapp.viewModel.CalenderViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;


public class SetTimeFragment extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    INoti notiData;
    TextView choseDate;
    TextView choseTime;
    TextView repeat;
    NotiModel notiModel = new NotiModel();
    public static final String TAG = "ModalBottomSheet";
    private CalenderViewModel model;
    Spinner repeatSpinner;
    private int repeatPosition = -1;
    String[] repeatSpinnerLabels = {"None", "Every minutes", "Every hour", "Daily", "Weekly"};
    final String[] repeatSpinnerValues = {"", "1p", "1h", "1d", "7d"};
    ArrayAdapter<String> repeatSpinerAdapter;
    private OnDismissListener dismissListener;
    public SetTimeFragment() {
        // Required empty public constructor
    }
    public SetTimeFragment(OnDismissListener dismissListener) {
        this.dismissListener = dismissListener;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_set_time, container, false);
        choseDate = rootView.findViewById(R.id.choseDate);
        choseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.setTargetFragment(SetTimeFragment.this, 0);
                datePicker.show(getFragmentManager(), "date picker");
            }
        });
        choseTime = rootView.findViewById(R.id.choseTime);
        choseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DialogFragment timePicker  = new TimePickerFragment();
                timePicker.setTargetFragment(SetTimeFragment.this, 0);
                timePicker.show(getFragmentManager(), "time picker");
            }
        });
        repeatSpinner =  rootView.findViewById(R.id.repeatSpinner);
        repeatSpinerAdapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, repeatSpinnerLabels);
        repeatSpinner.setAdapter(repeatSpinerAdapter);
        if(repeatPosition > -1) {
            repeatSpinner.setSelection(repeatPosition);
            repeatPosition = -1;
        }
        repeatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String repeatValue = repeatSpinnerValues[i];
                model.repeat.setValue(repeatValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterVie0w) {
            }
        });

        return rootView;
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        model.date.setValue(dayOfMonth);
        model.month.setValue(month);
        model.year.setValue(year);
        if (dismissListener != null) {
            if(model.hour.getValue() == null) {
                String timeNow = Time.getCurrentTimeStamp();
                LocalDateTime time = Time.convertToLocalDateTime(timeNow);
                model.hour.setValue(time.getHour());
                model.minute.setValue(time.getMinute());
            }
            dismissListener.onFragmentDismissed();
        }
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        model.hour.setValue(hourOfDay);
        model.minute.setValue(minute);
        if (dismissListener != null) {
            if(model.year.getValue() == null) {
                String timeNow = Time.getCurrentTimeStamp();
                LocalDateTime time = Time.convertToLocalDateTime(timeNow);
                model.year.setValue(time.getYear());
                model.month.setValue(time.getMonthValue() -1);
                model.date.setValue(time.getDayOfMonth());
            }
            dismissListener.onFragmentDismissed();
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        notiData = (INoti) context;
        model = new ViewModelProvider(requireActivity()).get(CalenderViewModel.class);

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void setRepeat(String repeat) {
        for (int i = 0; i < repeatSpinnerValues.length; i++) {
            if (repeatSpinnerValues[i].equals(repeat)) {
                repeatPosition = i;
                break;
            }
        }
    }

}