package com.fount.seed.wrappers;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;

@SuppressWarnings("WeakerAccess")
@IgnoreExtraProperties
public class StudentAttendance implements Serializable {

    public String kidName;
    public HashMap<String, Boolean> letters;

    @SuppressWarnings("unused")
    public StudentAttendance() {
    }

    public StudentAttendance(String kidName) {
        this.kidName = kidName;
        this.letters = new HashMap<>();
    }

    public String getKidName() {
        return kidName;
    }

    public HashMap<String, Boolean> getLetters() {
        return letters;
    }

    public void setLetters(HashMap<String, Boolean> letters) {
        this.letters = letters;
    }

    @Override
    public String toString() {
        return kidName;
    }
}
