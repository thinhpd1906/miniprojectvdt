package com.example.medicineapp.RoomDB.noti;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "noti")
public class NotiEntity {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @ColumnInfo(name = "title")
    public String title;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "time")
    public String time;
    @ColumnInfo(name = "repeat")
    public String repeat;
    @ColumnInfo(name = "priority")
    public String priority;
}
