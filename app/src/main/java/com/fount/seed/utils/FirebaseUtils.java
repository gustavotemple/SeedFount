package com.fount.seed.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;

import com.fount.seed.wrappers.ClassDate;
import com.fount.seed.wrappers.KidWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class FirebaseUtils {

    private static final String TAG = FirebaseUtils.class.getSimpleName();
    private static final String KIDS = "KidRegister";
    private static final String CLASS_DATE = "ClassDate";
    private static final String AM = "AM";
    private static final String PM = "PM";

    private DatabaseReference mDatabaseKids;
    private DatabaseReference mClassDate;

    private static FirebaseUtils instance = null;
    private static ClassDate classDate;
    private static String dateId;

    private FirebaseUtils() {
    }

    public static FirebaseUtils getInstance() {
        if (instance == null) {
            instance = new FirebaseUtils();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        return instance;
    }

    public void initialize() {
        mDatabaseKids = FirebaseDatabase.getInstance().getReference().child(KIDS);
        mDatabaseKids.keepSynced(true);
        mClassDate = FirebaseDatabase.getInstance().getReference().child(CLASS_DATE);
        mClassDate.keepSynced(true);
    }

    public DatabaseReference getDatabaseKids() {
        return mDatabaseKids;
    }

    public DatabaseReference getClassDate() {
        return mClassDate;
    }

    public void saveKid(@NonNull final KidWrapper kid) {
        kid.setUid(mDatabaseKids.push().getKey());
        mDatabaseKids.child(kid.getUid()).setValue(kid);
    }

    public void updateKid(@NonNull final KidWrapper kid) {
        mDatabaseKids.child(kid.getUid()).setValue(kid);
    }

    public void deleteKid(@NonNull final KidWrapper kid) {
        mDatabaseKids.child(kid.getUid()).removeValue();
    }

    private void getDateId() {

        if (mClassDate == null) {
            return;
        }

        mClassDate.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() instanceof Map) {
                    Map<String, Map<String, Object>> classDates = (Map<String, Map<String, Object>>) dataSnapshot.getValue();
                    for (Map.Entry<String, Map<String, Object>> classDate : classDates.entrySet()) {
                        for (Map.Entry<String, Object> date : classDate.getValue().entrySet()) {
                            if (date.getValue() instanceof String
                                    && ((String) date.getValue()).equalsIgnoreCase(DateGenerator.getPeriod())) {
                                FirebaseUtils.dateId = classDate.getKey();
                                FirebaseUtils.classDate = new ClassDate(DateGenerator.getPeriod());
                                return;
                            }
                        }
                    }
                }

                FirebaseUtils.classDate = new ClassDate(DateGenerator.getPeriod());
                FirebaseUtils.dateId = mClassDate.push().getKey();
                mClassDate.child(dateId).setValue(FirebaseUtils.classDate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void saveStudentAttendance(@NonNull final KidWrapper kid,
                                      @NonNull final String letter,
                                      final boolean value) {

        mClassDate.child(dateId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final ClassDate classDate = dataSnapshot.getValue(ClassDate.class);
                if (classDate == null) {
                    Log.e(TAG, "ClassDate error");
                    return;
                }

                final HashMap<String, Boolean> letters;
                final HashMap<String, HashMap<String, Boolean>> students;

                if (classDate.getStudentAttendance() == null) {
                    letters = new HashMap<>();
                    letters.put(letter, value);

                    students = new HashMap<>();
                    students.put(kid.getKidName(), letters);

                    classDate.setStudentAttendance(students);
                } else if (classDate.getStudentAttendance().isEmpty()
                        || classDate.getStudentAttendance().get(kid.getKidName()) == null) {
                    letters = new HashMap<>();
                    letters.put(letter, value);

                    classDate.getStudentAttendance().put(kid.getKidName(), letters);
                } else {
                    classDate.getStudentAttendance().get(kid.getKidName()).put(letter, value);
                }

                mClassDate.child(dateId).setValue(classDate);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static final class DateGenerator extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action == null) {
                return;
            } else {
                Log.i(TAG, "Action: " + action);
            }

            if (!action.equals(Constants.HOUR)
                    && !action.equals(Intent.ACTION_DATE_CHANGED)
                    && !action.equals(Intent.ACTION_TIMEZONE_CHANGED)
                    && !action.equals(Intent.ACTION_TIME_CHANGED)) {
                return;
            }

            if (dateId == null
                    || classDate == null
                    || !classDate.getDate().equalsIgnoreCase(getPeriod())) {
                FirebaseUtils.getInstance().getDateId();
            }
        }

        public static String getPeriod() {
            return (String) DateFormat.format("dd/MM/yyyy a",
                    new Date());
        }

        public static String formatDate(String date) {
            if (date.contains(AM)) {
                date = date.replaceAll(AM, "");
                date = date.concat("Manhã");
            } else if (date.contains(PM)) {
                date = date.replaceAll(PM, "");
                date = date.concat("Noite");
            }

            return date;
        }
    }
}
