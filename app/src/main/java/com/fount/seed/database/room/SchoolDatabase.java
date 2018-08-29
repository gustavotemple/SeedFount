package com.fount.seed.database.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = RoomEntity.class, version = 1, exportSchema = false)
public abstract class SchoolDatabase extends RoomDatabase {
    public abstract RoomDao roomDao();

    private static SchoolDatabase INSTANCE;

    public static SchoolDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (SchoolDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            SchoolDatabase.class, "school_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
