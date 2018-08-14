package com.fount.seed.database.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class SchoolRepository {

    private final RoomDao roomDao;
    private final ChaletDao chaletDao;
    private final LiveData<List<RoomEntity>> rooms;
    private final LiveData<List<ChaletEntity>> chalets;

    public SchoolRepository(@NonNull final Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        roomDao = db.roomDao();
        chaletDao = db.chaletDao();
        rooms = roomDao.getAll();
        chalets = chaletDao.getAll();
    }

    public void insert(final RoomEntity roomEntity) {
        new InsertRoomAsyncTask(roomDao).execute(roomEntity);
    }

    public void insert(final ChaletEntity chaletEntity) {
        new InsertChaletAsyncTask(chaletDao).execute(chaletEntity);
    }

    public void deleteAllRooms() {
        new DeleteAllRoomsAsyncTask(roomDao).execute();
    }

    public void deleteAllChalets() {
        new DeleteAllChaletsAsyncTask(chaletDao).execute();
    }

    public LiveData<List<RoomEntity>> getAllRooms() {
        return rooms;
    }

    public LiveData<List<ChaletEntity>> getAllChalets() {
        return chalets;
    }

    private static class InsertRoomAsyncTask extends AsyncTask<RoomEntity, Void, Void> {

        private final RoomDao roomDao;

        InsertRoomAsyncTask(final RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(final RoomEntity... params) {
            roomDao.insertAll(params[0]);
            return null;
        }
    }

    private static class InsertChaletAsyncTask extends AsyncTask<ChaletEntity, Void, Void> {

        private final ChaletDao chaletDao;

        InsertChaletAsyncTask(final ChaletDao chaletDao) {
            this.chaletDao = chaletDao;
        }

        @Override
        protected Void doInBackground(final ChaletEntity... params) {
            chaletDao.insertAll(params[0]);
            return null;
        }
    }

    private static class DeleteAllRoomsAsyncTask extends AsyncTask<Void, Void, Void> {

        private final RoomDao roomDao;

        DeleteAllRoomsAsyncTask(final RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            roomDao.deleteAll();
            return null;
        }
    }

    private static class DeleteAllChaletsAsyncTask extends AsyncTask<Void, Void, Void> {

        private final ChaletDao chaletDao;

        DeleteAllChaletsAsyncTask(final ChaletDao chaletDao) {
            this.chaletDao = chaletDao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            chaletDao.deleteAll();
            return null;
        }
    }

}
