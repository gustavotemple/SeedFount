package com.fount.seed.database.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class SchoolRepository {

    private RoomDao roomDao;
    private ChaletDao chaletDao;
    private LiveData<List<RoomEntity>> rooms;
    private LiveData<List<ChaletEntity>> chalets;

    public SchoolRepository(Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        roomDao = db.roomDao();
        chaletDao = db.chaletDao();
        rooms = roomDao.getAll();
        chalets = chaletDao.getAll();
    }

    public void insert(RoomEntity roomEntity) {
        new InsertRoomAsyncTask(roomDao).execute(roomEntity);
    }

    public void insert(ChaletEntity chaletEntity) {
        new InsertChaletAsyncTask(chaletDao).execute(chaletEntity);
    }

    public LiveData<List<RoomEntity>> getAllRooms() {
        return rooms;
    }

    public LiveData<List<ChaletEntity>> getAllChalets() {
        return chalets;
    }

    private static class InsertRoomAsyncTask extends AsyncTask<RoomEntity, Void, Void> {

        private RoomDao roomDao;

        InsertRoomAsyncTask(RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(final RoomEntity... params) {
            roomDao.deleteAll();
            roomDao.insertAll(params[0]);
            return null;
        }
    }

    private static class InsertChaletAsyncTask extends AsyncTask<ChaletEntity, Void, Void> {

        private ChaletDao chaletDao;

        InsertChaletAsyncTask(ChaletDao chaletDao) {
            this.chaletDao = chaletDao;
        }

        @Override
        protected Void doInBackground(final ChaletEntity... params) {
            chaletDao.deleteAll();
            chaletDao.insertAll(params[0]);
            return null;
        }
    }

}
