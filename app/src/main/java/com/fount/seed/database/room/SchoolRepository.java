package com.fount.seed.database.room;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.List;

public class SchoolRepository {

    private final RoomDao roomDao;

    private final LiveData<List<RoomEntity>> all;
    private final LiveData<List<RoomEntity>> rooms;
    private final LiveData<List<RoomEntity>> chalets;
    private final LiveData<Integer> count;

    private static final int ROOM = 1;
    private static final int CHALET = 2;

    public SchoolRepository(@NonNull final Application application) {
        SchoolDatabase db = SchoolDatabase.getDatabase(application);
        roomDao = db.roomDao();
        all = roomDao.getAll();
        rooms = roomDao.getAllRooms();
        chalets = roomDao.getAllChalets();
        count = roomDao.getCount();
    }

    public void insertRoom(final RoomEntity roomEntity) {
        roomEntity.setType(ROOM);
        new InsertRoomAsyncTask(roomDao).execute(roomEntity);
    }

    public void insertChalet(final RoomEntity roomEntity) {
        roomEntity.setType(CHALET);
        new InsertRoomAsyncTask(roomDao).execute(roomEntity);
    }

    public void deleteAll() {
        new DeleteAllAsyncTask(roomDao).execute();
    }

    public void deleteAllRooms() {
        new DeleteAllRoomsAsyncTask(roomDao).execute();
    }

    public void deleteAllChalets() {
        new DeleteAllChaletsAsyncTask(roomDao).execute();
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

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void> {

        private final RoomDao roomDao;

        DeleteAllAsyncTask(final RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            roomDao.deleteAll();
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
            roomDao.deleteAllRooms();
            return null;
        }
    }

    private static class DeleteAllChaletsAsyncTask extends AsyncTask<Void, Void, Void> {

        private final RoomDao roomDao;

        DeleteAllChaletsAsyncTask(final RoomDao roomDao) {
            this.roomDao = roomDao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            roomDao.deleteAllChalets();
            return null;
        }
    }

}
