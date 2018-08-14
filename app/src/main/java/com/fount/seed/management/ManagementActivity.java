package com.fount.seed.management;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.fount.seed.R;
import com.fount.seed.database.room.ChaletEntity;
import com.fount.seed.database.room.RoomEntity;
import com.fount.seed.utils.CustomExceptionHandler;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagementActivity extends AppCompatActivity {

    private static final String TAG = ManagementActivity.class.getSimpleName();
    private SchoolViewModel schoolViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        ButterKnife.bind(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        schoolViewModel = ViewModelProviders.of(this).get(SchoolViewModel.class);
        schoolViewModel.getAllRooms().observe(this, rooms -> {
            if (rooms != null && !rooms.isEmpty()) {
                Log.i(TAG, rooms.size() + "");
            }
        });
        schoolViewModel.getAllChalets().observe(this, chalets ->
        {
            if (chalets != null && !chalets.isEmpty()) {
                Log.i(TAG, chalets.size() + "");
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view,
                "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    @OnClick(R.id.save)
    public void save() {

        RoomEntity roomEntity = new RoomEntity("", "", "");
        ChaletEntity chaletEntity = new ChaletEntity("", "", "");

        schoolViewModel.insert(roomEntity);
        schoolViewModel.insert(chaletEntity);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
