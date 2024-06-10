package com.example.medicineapp.RoomDB.search;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "search")
public class SearchEntity {
    @PrimaryKey(autoGenerate = true)
    public Integer id;
    @ColumnInfo(name = "text")
    public String text;
}
