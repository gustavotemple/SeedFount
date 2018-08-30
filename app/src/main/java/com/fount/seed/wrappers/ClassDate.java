package com.fount.seed.wrappers;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
@IgnoreExtraProperties
public class ClassDate implements Serializable {

    public String uid;
    public String date;
    public String comment;
    public HashMap<String, HashMap<String, Boolean>> studentAttendance;

    @SuppressWarnings("unused")
    public ClassDate() {
    }

    public ClassDate(String uid,
                     String date) {
        this.uid = uid;
        this.date = date;
        this.comment = "";
        this.studentAttendance = new HashMap<>();
    }

    public String getUid() {
        return uid;
    }

    public String getDate() {
        return date;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public HashMap<String, HashMap<String, Boolean>> getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(HashMap<String, HashMap<String, Boolean>> studentAttendance) {
        this.studentAttendance = studentAttendance;
    }
}
