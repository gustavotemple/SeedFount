package com.fount.seed;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;

import com.fount.seed.database.room.RoomEntity;
import com.fount.seed.kids.ChaletActivity;
import com.fount.seed.kids.RoomActivity;
import com.fount.seed.management.ManagementActivity;
import com.fount.seed.management.SchoolViewModel;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.database.firebase.FirebaseUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.buttonBabies)
    public Button buttonBabies;

    @BindView(R.id.buttonChalet)
    public Button buttonChalet;

    @BindView(R.id.buttonManagement)
    public Button buttonManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        FirebaseUtils.getInstance().initialize();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        putAlarm(getApplicationContext());

        initializeRoom();

    }

    @SuppressWarnings("unused")
    @OnClick(R.id.buttonBabies)
    public void roomActivity(View view) {
        startActivity(new Intent(StartActivity.this.getApplicationContext(),
                RoomActivity.class));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.buttonChalet)
    public void chaletActivity(View view) {
        startActivity(new Intent(StartActivity.this.getApplicationContext(),
                ChaletActivity.class));
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.buttonManagement)
    public void managementActivity(View view) {
        startActivity(new Intent(StartActivity.this.getApplicationContext(),
                ManagementActivity.class));
    }

    private void initializeRoom() {
        final SchoolViewModel schoolViewModel = ViewModelProviders.of(this).get(SchoolViewModel.class);

        schoolViewModel.getCount().observe(this, count -> {
            if (count == null || count > 0) {
                return;
            }

            RoomEntity roomEntity0 = new RoomEntity(0, "N1",
                    "04/2017", (String) DateFormat.format("MM/yyyy", new Date()));
            RoomEntity roomEntity1 = new RoomEntity(1, "N2", "04/2016", "03/2017");
            RoomEntity roomEntity2 = new RoomEntity(2, "N3", "06/2015", "03/2016");
            RoomEntity roomEntity3 = new RoomEntity(3, "N4", "01/2015", "05/2015");
            RoomEntity roomEntity4 = new RoomEntity(4, "N5", "07/2014", "12/2014");

            schoolViewModel.insertRoom(roomEntity0);
            schoolViewModel.insertRoom(roomEntity1);
            schoolViewModel.insertRoom(roomEntity2);
            schoolViewModel.insertRoom(roomEntity3);
            schoolViewModel.insertRoom(roomEntity4);

            RoomEntity chaletEntity0 = new RoomEntity(0, "Chalet 3", "07/2006", "06/2007");
            RoomEntity chaletEntity1 = new RoomEntity(1, "Chalet 4", "07/2007", "06/2008");
            RoomEntity chaletEntity2 = new RoomEntity(2, "Chalet 5", "07/2008", "06/2009");
            RoomEntity chaletEntity3 = new RoomEntity(3, "Chalet 6", "07/2009", "06/2010");
            RoomEntity chaletEntity4 = new RoomEntity(4, "Chalet 7", "07/2010", "06/2011");
            RoomEntity chaletEntity5 = new RoomEntity(5, "Chalet 8", "07/2011", "06/2012");
            RoomEntity chaletEntity6 = new RoomEntity(6, "Chalet 9", "07/2012", "06/2013");
            RoomEntity chaletEntity7 = new RoomEntity(7, "Chalet 10", "07/2013", "06/2014");

            schoolViewModel.insertChalet(chaletEntity0);
            schoolViewModel.insertChalet(chaletEntity1);
            schoolViewModel.insertChalet(chaletEntity2);
            schoolViewModel.insertChalet(chaletEntity3);
            schoolViewModel.insertChalet(chaletEntity4);
            schoolViewModel.insertChalet(chaletEntity5);
            schoolViewModel.insertChalet(chaletEntity6);
            schoolViewModel.insertChalet(chaletEntity7);
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        FirebaseUtils.getInstance().clean();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    void putAlarm(Context context) {
        Intent alarmIntent = new Intent(context, FirebaseUtils.DateGenerator.class);
        alarmIntent.setAction(Constants.INTERVAL);
        PendingIntent displayIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    AlarmManager.INTERVAL_FIFTEEN_MINUTES,
                    displayIntent);
        }
    }
}


