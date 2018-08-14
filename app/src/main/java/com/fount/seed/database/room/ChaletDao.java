package com.fount.seed.database.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ChaletDao {

    @Query("SELECT * FROM chalets_table")
    LiveData<List<ChaletEntity>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ChaletEntity... chaletEntity);

    @Query("DELETE FROM chalets_table")
    void deleteAll();

}
