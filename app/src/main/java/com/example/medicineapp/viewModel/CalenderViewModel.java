package com.example.medicineapp.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CalenderViewModel  extends ViewModel {
    public MutableLiveData<Integer> date = new MutableLiveData<>();
    public MutableLiveData<Integer> month = new MutableLiveData<>();
    public MutableLiveData<Integer> year = new MutableLiveData<>();
    public MutableLiveData<Integer> hour = new MutableLiveData<>();
    public MutableLiveData<Integer> minute = new MutableLiveData<>();
    public MutableLiveData<String> repeat = new MutableLiveData<>();
    public String getDateTimeString() {
        // Lấy các giá trị ngày, tháng, năm, giờ và phút từ MutableLiveData
        int d = date.getValue() != null ? date.getValue() : 0;
        int m = month.getValue() != null ? month.getValue() : 0;
        int y = year.getValue() != null ? year.getValue() : 0;
        int h = hour.getValue() != null ? hour.getValue() : 0;
        int min = minute.getValue() != null ? minute.getValue() : 0;

        // Xây dựng chuỗi ngày tháng theo định dạng "yyyy/MM/dd HH:mm"
        return String.format("%04d/%02d/%02d %02d:%02d", y, m+1, d, h, min);
    }
}
