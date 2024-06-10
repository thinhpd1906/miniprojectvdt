package com.example.medicineapp.RoomDB.search;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.medicineapp.RoomDB.noti.NotiEntity;

import java.util.List;


@Dao
public interface SearchDao {
    @Insert
    long addSearch(SearchEntity textValue);
    @Query("SELECT text FROM search")
    List<String> getAll();
    @Query("SELECT * FROM search WHERE text = :text LIMIT 1")
    SearchEntity findSearchByText(String text);
    @Delete
    void delete(SearchEntity search);
    @Query("SELECT * FROM search")
    List<SearchEntity> getList();
    @Query("DELETE FROM search")
    void deleteAll();
}
