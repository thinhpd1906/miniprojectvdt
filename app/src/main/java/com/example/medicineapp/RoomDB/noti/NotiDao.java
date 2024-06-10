package com.example.medicineapp.RoomDB.noti;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface NotiDao {
    @Insert
    long addNoti(NotiEntity noti);
    @Delete
    void delete(NotiEntity noti);
    @Query("SELECT * FROM noti")
    List<NotiEntity> getAll();
    @Query("SELECT * FROM noti WHERE id = :id")
    NotiEntity getDetail(Integer id);
    @Update
    void updateNoti(NotiEntity noti);
    @Query("SELECT * FROM noti WHERE ((time > :time) OR (repeat != ''))")
    List<NotiEntity> getActiveNotis(String time);
}
