package com.example.medicineapp.RoomDB.noti;
import android.content.Context;
import android.util.Log;

import com.example.medicineapp.RoomDB.MedicineDatabase;
import com.example.medicineapp.interfaces.IDetailNoti;
import com.example.medicineapp.interfaces.IGetIdNoti;
import com.example.medicineapp.model.NotiModel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NotiRepository {
    private NotiDao notiDao;
    private ExecutorService executorService;

    public NotiRepository(Context context) {
        MedicineDatabase db = MedicineDatabase.getDatabase(context);
        notiDao = db.notiDao();
        executorService = Executors.newSingleThreadExecutor();
    }
    public void insert(NotiModel noti, IGetIdNoti callback) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                NotiEntity notiEntity = new NotiEntity();
                notiEntity.title = noti.getTitle();
                notiEntity.description = noti.getDescription();
                String monthConvert = ((noti.getMonth() + 1) > 9) ? ("" + (noti.getMonth() + 1)): ("0" + (noti.getMonth() + 1));
                String dayConvert = (noti.getDay() > 9)? ("" + noti.getDay()): ("0" + noti.getDay());
                String hourConvert = (noti.getHour() > 9)? ("" + noti.getHour()): ("0" + noti.getHour());
                String minuteConvert = (noti.getMinute() > 9)? ("" + noti.getMinute()): ("0" + noti.getMinute());
                notiEntity.time = "" + noti.getYear() + "/" + monthConvert + "/" + dayConvert + " " + hourConvert + ":" + minuteConvert;
                notiEntity.repeat = noti.getRepeat();
                notiEntity.priority = noti.getPriority();
                long id = notiDao.addNoti(notiEntity);
                callback.getIdNoti(id);
            }
        });
    }

    public List<NotiEntity> getAllNotis() {
        Future<List<NotiEntity>> future = executorService.submit(new java.util.concurrent.Callable<List<NotiEntity>>() {
            @Override
            public List<NotiEntity> call() throws Exception {
                return notiDao.getAll();
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void getDetail(Integer id, IDetailNoti callback) {
        executorService.execute(() -> {
            NotiEntity noti = notiDao.getDetail(id);
            if (callback != null) {
                callback.getDetailNoti(noti);
            }
        });
    }
    public void delete(NotiEntity noti) {
        executorService.execute(() -> {
            notiDao.delete(noti);
        });
    }

    public void update(NotiModel noti, Integer id) {
        executorService.execute(() -> {
            NotiEntity notiEntity = new NotiEntity();
            notiEntity.id = id;
            notiEntity.title = noti.getTitle();
            notiEntity.description = noti.getDescription();
            String monthConvert = ((noti.getMonth() + 1) > 9) ? ("" + (noti.getMonth() + 1)): ("0" + (noti.getMonth() + 1));
            String dayConvert = (noti.getDay() > 9)? ("" + noti.getDay()): ("0" + noti.getDay());
            String hourConvert = (noti.getHour() > 9)? ("" + noti.getHour()): ("0" + noti.getHour());
            String minuteConvert = (noti.getMinute() > 9)? ("" + noti.getMinute()): ("0" + noti.getMinute());
            notiEntity.time = "" + noti.getYear() + "/" + monthConvert + "/" + dayConvert + " " + hourConvert + ":" + minuteConvert;
            notiEntity.repeat = noti.getRepeat();
            notiEntity.priority = noti.getPriority();
            notiDao.updateNoti(notiEntity);
        });
    }
    public List<NotiEntity> getActiveNotis(String time) {
        Future<List<NotiEntity>> future = executorService.submit(new java.util.concurrent.Callable<List<NotiEntity>>() {
            @Override
            public List<NotiEntity> call() throws Exception {
                return notiDao.getActiveNotis(time);
            }
        });

        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
