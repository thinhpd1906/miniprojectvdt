package com.example.medicineapp.RoomDB.search;

import android.content.Context;
import com.example.medicineapp.RoomDB.MedicineDatabase;
import com.example.medicineapp.RoomDB.noti.NotiEntity;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SearchRepository {
    private SearchDao searchDao;
    private ExecutorService executorService;

    public SearchRepository(Context context) {
        MedicineDatabase db = MedicineDatabase.getDatabase(context);
        searchDao = db.searchDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public void insert(String text) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                SearchEntity existingEntity = searchDao.findSearchByText(text);
                if (existingEntity == null) {
                    SearchEntity searchEntity = new SearchEntity();
                    searchEntity.text = text;
                    searchDao.addSearch(searchEntity);
                }

            }
        });
    }

    public List<String> getAllSearches() {
        Future<List<String>> future = executorService.submit(new java.util.concurrent.Callable<List<String>>() {
            @Override
            public List<String> call() throws Exception {
                return searchDao.getAll();
            }
        });
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void delete(SearchEntity search) {
        executorService.execute(() -> {
            searchDao.delete(search);
        });
    }
    public List<SearchEntity> getListSearches() {
        Future<List<SearchEntity>> future = executorService.submit(new java.util.concurrent.Callable<List<SearchEntity>>() {
            @Override
            public List<SearchEntity> call() throws Exception {
                return searchDao.getList();
            }
        });
        try {
            return future.get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void deleteAll() {
        executorService.execute(() -> {
            searchDao.deleteAll();
        });
    }
}
