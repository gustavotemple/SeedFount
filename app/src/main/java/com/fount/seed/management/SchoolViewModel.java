package com.fount.seed.management;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.fount.seed.database.room.ChaletEntity;
import com.fount.seed.database.room.RoomEntity;
import com.fount.seed.database.room.SchoolRepository;

import java.util.List;

class SchoolViewModel extends AndroidViewModel {

    private final SchoolRepository schoolRepo;
    private final LiveData<List<RoomEntity>> rooms;
    private final LiveData<List<ChaletEntity>> chalets;

    SchoolViewModel(@NonNull final Application application) {
        super(application);
        schoolRepo = new SchoolRepository(application);
        rooms = schoolRepo.getAllRooms();
        chalets = schoolRepo.getAllChalets();
    }

    public void insert(final RoomEntity roomEntity) {
        schoolRepo.insert(roomEntity);
    }

    public void insert(final ChaletEntity chaletEntity) {
        schoolRepo.insert(chaletEntity);
    }

    public void deleteAllRooms() {
        schoolRepo.deleteAllRooms();
    }

    public void deleteAllChalets() {
        schoolRepo.deleteAllChalets();
    }

    public LiveData<List<RoomEntity>> getAllRooms() {
        return rooms;
    }

    public LiveData<List<ChaletEntity>> getAllChalets() {
        return chalets;
    }

}
