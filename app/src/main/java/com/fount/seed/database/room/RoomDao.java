package com.fount.seed.database.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface RoomDao {

    @Query("SELECT * FROM rooms_table")
    LiveData<List<RoomEntity>> getAll();

    @Query("SELECT * FROM rooms_table WHERE type = 1")
    LiveData<List<RoomEntity>> getAllRooms();

    @Query("SELECT * FROM rooms_table WHERE type = 2")
    LiveData<List<RoomEntity>> getAllChalets();

    @Query("SELECT COUNT(*) FROM rooms_table")
    LiveData<Integer> getCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(RoomEntity... roomEntity);

    @Query("DELETE FROM rooms_table")
    void deleteAll();

    @Query("DELETE FROM rooms_table WHERE type = 1")
    void deleteAllRooms();

    @Query("DELETE FROM rooms_table WHERE type = 2")
    void deleteAllChalets();

}
