package com.fount.seed.database.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "rooms_table")
public class RoomEntity {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "row")
    private int row;

    @NonNull
    @ColumnInfo(name = "number")
    private String number;

    @NonNull
    @ColumnInfo(name = "from")
    private String from;

    @NonNull
    @ColumnInfo(name = "to")
    private String to;

    public RoomEntity(int row,
                      @NonNull String number,
                      @NonNull String from,
                      @NonNull String to) {
        this.row = row;
        this.number = number;
        this.from = from;
        this.to = to;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    @NonNull
    public String getNumber() {
        return number;
    }

    public void setNumber(@NonNull String number) {
        this.number = number;
    }

    @NonNull
    public String getFrom() {
        return from;
    }

    public void setFrom(@NonNull String from) {
        this.from = from;
    }

    @NonNull
    public String getTo() {
        return to;
    }

    public void setTo(@NonNull String to) {
        this.to = to;
    }
}