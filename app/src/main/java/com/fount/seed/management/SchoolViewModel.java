package com.fount.seed.management;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.fount.seed.database.room.RoomEntity;
import com.fount.seed.database.room.SchoolRepository;

import java.util.List;

public class SchoolViewModel extends AndroidViewModel {

    private final SchoolRepository schoolRepo;
    private final LiveData<List<RoomEntity>> all;
    private final LiveData<List<RoomEntity>> rooms;
    private final LiveData<List<RoomEntity>> chalets;
    private final LiveData<Integer> count;

    SchoolViewModel(@NonNull final Application application) {
        super(application);
        schoolRepo = new SchoolRepository(application);
        all = schoolRepo.getAll();
        rooms = schoolRepo.getAllRooms();
        chalets = schoolRepo.getAllChalets();
        count = schoolRepo.getCount();
    }

    public void insertRoom(final RoomEntity roomEntity) {
        schoolRepo.insertRoom(roomEntity);
    }

    public void insertChalet(final RoomEntity chaletEntity) {
        schoolRepo.insertChalet(chaletEntity);
    }

    public void deleteAll() {
        schoolRepo.deleteAll();
    }

    public void deleteAllRooms() {
        schoolRepo.deleteAllRooms();
    }

    public void deleteAllChalets() {
        schoolRepo.deleteAllChalets();
    }

    public LiveData<List<RoomEntity>> getAll() {
        return all;
    }

    public LiveData<List<RoomEntity>> getAllRooms() {
        return rooms;
    }

    public LiveData<List<RoomEntity>> getAllChalets() {
        return chalets;
    }

    public LiveData<Integer> getCount() {
        return count;
    }

}
