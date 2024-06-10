package com.example.medicineapp.RoomDB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.medicineapp.RoomDB.noti.NotiDao;
import com.example.medicineapp.RoomDB.noti.NotiEntity;
import com.example.medicineapp.RoomDB.search.SearchDao;
import com.example.medicineapp.RoomDB.search.SearchEntity;

@Database(entities = {NotiEntity.class, SearchEntity.class}, version = 4)
public abstract class MedicineDatabase extends RoomDatabase {
    public abstract NotiDao notiDao();
    public abstract SearchDao searchDao();
    private static volatile MedicineDatabase INSTANCE;
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // Migration logic here
            // For example, to add a new column to a table:
            // database.execSQL("ALTER TABLE noti_entity ADD COLUMN new_column INTEGER");
        }
    };
    public static MedicineDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MedicineDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    MedicineDatabase.class, "medicine_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
