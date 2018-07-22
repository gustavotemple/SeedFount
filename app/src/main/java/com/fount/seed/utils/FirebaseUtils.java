package com.fount.seed.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.CheckBox;

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
    private static final String KIDS_ROOM = "KidsRoom";
    private static final String KIDS_CHALET = "KidsChalet";
    private static final String DATES_ROOM = "DatesRoom";
    private static final String DATES_CHALET = "DatesChalet";

    private DatabaseReference mRoomKids;
    private DatabaseReference mChaletKids;
    private DatabaseReference mDatesRoom;
    private DatabaseReference mDatesChalet;

    private static FirebaseUtils instance = null;
    private static ClassDate roomClassDate;
    private static ClassDate chaletClassDate;
    private static String roomDateId;
    private static String chaletDateId;

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
        mRoomKids = FirebaseDatabase.getInstance().getReference().child(KIDS_ROOM);
        mRoomKids.keepSynced(true);
        mChaletKids = FirebaseDatabase.getInstance().getReference().child(KIDS_CHALET);
        mChaletKids.keepSynced(true);
        mDatesRoom = FirebaseDatabase.getInstance().getReference().child(DATES_ROOM);
        mDatesRoom.keepSynced(true);
        mDatesChalet = FirebaseDatabase.getInstance().getReference().child(DATES_CHALET);
        mDatesChalet.keepSynced(true);
    }

    public void clean() {
        roomClassDate = null;
        chaletClassDate = null;
        roomDateId = null;
        chaletDateId = null;
    }

    public DatabaseReference getRoomKids() {
        return mRoomKids;
    }

    public DatabaseReference getChaletKids() {
        return mChaletKids;
    }

    public DatabaseReference getDatesRoom() {
        return mDatesRoom;
    }

    public DatabaseReference getDatesChalet() {
        return mDatesChalet;
    }

    public void saveKid(@NonNull final KidWrapper kid,
                        @NonNull final DatabaseReference databaseReference) {
        kid.setUid(databaseReference.push().getKey());
        databaseReference.child(kid.getUid()).setValue(kid);
    }

    public void updateKid(@NonNull final KidWrapper kid,
                          @NonNull final DatabaseReference databaseReference) {
        databaseReference.child(kid.getUid()).setValue(kid);
    }

    public void deleteKid(@NonNull final KidWrapper kid,
                          @NonNull final DatabaseReference databaseReference) {
        databaseReference.child(kid.getUid()).removeValue();
    }

    private void getDateId(final DatabaseReference databaseReference) {
        if (databaseReference == null) {
            Log.e(TAG, "getDateId error");
            return;
        }

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() instanceof Map) {
                    Map<String, Map<String, Object>> classDates = (Map<String, Map<String, Object>>) dataSnapshot.getValue();
                    for (Map.Entry<String, Map<String, Object>> classDate : classDates.entrySet()) {
                        for (Map.Entry<String, Object> date : classDate.getValue().entrySet()) {
                            if (date.getValue() instanceof String
                                    && ((String) date.getValue()).equalsIgnoreCase(DateGenerator.getPeriod())) {

                                if (databaseReference == getDatesRoom()) {
                                    FirebaseUtils.roomDateId = classDate.getKey();
                                    FirebaseUtils.roomClassDate = new ClassDate(DateGenerator.getPeriod());
                                } else if (databaseReference == getDatesChalet()) {
                                    FirebaseUtils.chaletDateId = classDate.getKey();
                                    FirebaseUtils.chaletClassDate = new ClassDate(DateGenerator.getPeriod());
                                }

                                return;
                            }
                        }
                    }
                }

                if (databaseReference == getDatesRoom()) {
                    FirebaseUtils.roomDateId = mDatesRoom.push().getKey();
                    FirebaseUtils.roomClassDate = new ClassDate(DateGenerator.getPeriod());
                    databaseReference.child(roomDateId).setValue(FirebaseUtils.roomClassDate);
                } else if (databaseReference == getDatesChalet()) {
                    FirebaseUtils.chaletDateId = mDatesChalet.push().getKey();
                    FirebaseUtils.chaletClassDate = new ClassDate(DateGenerator.getPeriod());
                    databaseReference.child(chaletDateId).setValue(FirebaseUtils.chaletClassDate);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void setLetter(@NonNull final KidWrapper kid,
                          @NonNull final String letter,
                          @NonNull final CheckBox checkBox) {

        if (kid.classRoom == null
                || kid.classRoom.isEmpty()) {
            Log.e(TAG, "setLetter error");
            return;
        }

        String dateId = null;
        DatabaseReference databaseReference = null;
        if (kid.classRoom.contains(Constants.ROOM)) {
            dateId = roomDateId;
            databaseReference = mDatesRoom;
        } else if (kid.classRoom.contains(Constants.CHALET)) {
            dateId = chaletDateId;
            databaseReference = mDatesChalet;
        }

        if (dateId == null
                || databaseReference == null) {
            Log.e(TAG, "setLetter error");
            return;
        }

        databaseReference.child(dateId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                final ClassDate classDate = dataSnapshot.getValue(ClassDate.class);
                if (classDate == null) {
                    Log.e(TAG, "ClassDate error");
                    return;
                }

                if (classDate.getStudentAttendance() != null
                        && !classDate.getStudentAttendance().isEmpty()
                        && classDate.getStudentAttendance().get(kid.getKidName()) != null
                        && classDate.getStudentAttendance().get(kid.getKidName()).containsKey(letter)) {
                    checkBox.setChecked(classDate.getStudentAttendance().get(kid.getKidName()).get(letter));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void saveStudentAttendance(@NonNull final KidWrapper kid,
                                      @NonNull final String letter,
                                      final boolean value) {
        if (kid.classRoom == null
                || kid.classRoom.isEmpty()) {
            Log.e(TAG, "saveStudentAttendance error");
            return;
        }

        String dateId = null;
        DatabaseReference databaseReference = null;
        if (kid.classRoom.contains(Constants.ROOM)) {
            dateId = roomDateId;
            databaseReference = mDatesRoom;
        } else if (kid.classRoom.contains(Constants.CHALET)) {
            dateId = chaletDateId;
            databaseReference = mDatesChalet;
        }

        if (dateId == null
                || databaseReference == null) {
            Log.e(TAG, "saveStudentAttendance error");
            return;
        }

        saveStudentAttendance(kid, databaseReference, dateId, letter, value);
    }

    private void saveStudentAttendance(@NonNull final KidWrapper kid,
                                       @NonNull final DatabaseReference databaseReference,
                                       @NonNull final String dateId,
                                       @NonNull final String letter,
                                       final boolean value) {
        databaseReference.child(dateId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

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

                databaseReference.child(dateId).setValue(classDate);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static final class DateGenerator extends BroadcastReceiver {

        private static final String TAG = DateGenerator.class.getSimpleName();

        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action == null) {
                return;
            } else {
                Log.i(TAG, "Action: " + action);
            }

            if (!action.equals(Constants.INTERVAL)
                    && !action.equals(Intent.ACTION_DATE_CHANGED)
                    && !action.equals(Intent.ACTION_TIMEZONE_CHANGED)
                    && !action.equals(Intent.ACTION_TIME_CHANGED)) {
                return;
            }

            if (roomDateId == null
                    || chaletDateId == null
                    || roomClassDate == null
                    || chaletClassDate == null
                    || !roomClassDate.getDate().equalsIgnoreCase(getPeriod())
                    || !chaletClassDate.getDate().equalsIgnoreCase(getPeriod())) {
                FirebaseUtils.getInstance().getDateId(FirebaseUtils.getInstance().getDatesRoom());
                FirebaseUtils.getInstance().getDateId(FirebaseUtils.getInstance().getDatesChalet());
            }
        }

        public static String getPeriod() {
            return (String) DateFormat.format("dd/MM/yyyy a",
                    new Date());
        }

        public static String formatDate(String date) {
            if (date.contains(Constants.AM)) {
                date = date.replaceAll(Constants.AM, "");
                date = date.concat("Manhã");
            } else if (date.contains(Constants.PM)) {
                date = date.replaceAll(Constants.PM, "");
                date = date.concat("Noite");
            }

            return date;
        }
    }
}
