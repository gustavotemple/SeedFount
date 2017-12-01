package com.fount.seed.wrappers;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
@IgnoreExtraProperties
public class ClassDate implements Serializable {

    public String date;
    public HashMap<String, HashMap<String, Boolean>> studentAttendance;

    @SuppressWarnings("unused")
    public ClassDate() {
    }

    public ClassDate(String date) {
        this.date = date;
        this.studentAttendance = new HashMap<>();
    }

    public String getDate() {
        return date;
    }

    public HashMap<String, HashMap<String, Boolean>> getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(HashMap<String, HashMap<String, Boolean>> studentAttendance) {
        this.studentAttendance = studentAttendance;
    }
}
