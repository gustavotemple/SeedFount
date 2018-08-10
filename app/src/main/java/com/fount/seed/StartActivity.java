package com.fount.seed;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.fount.seed.kids.ChaletActivity;
import com.fount.seed.kids.RoomActivity;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.utils.FirebaseUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

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

        buttonBabies.setOnClickListener(view -> startActivity(new Intent(StartActivity.this.getApplicationContext(), RoomActivity.class)));
        buttonChalet.setOnClickListener(view -> startActivity(new Intent(StartActivity.this.getApplicationContext(), ChaletActivity.class)));
        buttonManagement.setOnClickListener(view -> {
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        putAlarm(getApplicationContext());
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


