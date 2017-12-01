package com.fount.seed;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.utils.FirebaseUtils;
import com.fount.seed.wrappers.KidWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private RelativeLayout noKidsLayout;
    private TextView noKids;
    private FloatingActionButton fab;
    private KidsListAdapter mKidsListAdapter;
    private ClassDateListAdapter mClassDateListAdapter;
    private Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        FirebaseUtils.getInstance().initialize();
        FirebaseUtils.getInstance().getDatabaseKids().addListenerForSingleValueEvent(initializeKid);

        final Typeface type = Typeface.createFromAsset(getAssets(), Constants.FONT);

        noKidsLayout = findViewById(R.id.no_kids_layout);
        noKids = findViewById(R.id.no_kids);
        noKids.setTypeface(type);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mClassDateListAdapter = new ClassDateListAdapter();
        mKidsListAdapter = new KidsListAdapter(null);

        recyclerView.setAdapter(mKidsListAdapter);

        // Make sure new events are visible
        mKidsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mKidsListAdapter.getItemCount());
            }
        });

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this.getApplicationContext(), RegisterActivity.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkPermission();
        }

        putAlarm(getApplicationContext());
    }

    void putAlarm(Context context) {
        Intent alarmIntent = new Intent(context, FirebaseUtils.DateGenerator.class);
        alarmIntent.setAction(Constants.HOUR);
        PendingIntent displayIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    SystemClock.elapsedRealtime(),
                    AlarmManager.INTERVAL_HOUR,
                    displayIntent);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermission() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

        // Tear down Firebase listeners in adapter
        if (mKidsListAdapter != null) {
            mKidsListAdapter.cleanup();
        }
        if (mClassDateListAdapter != null) {
            mClassDateListAdapter.cleanup();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent");

        final KidWrapper kid = intent != null ? (KidWrapper) intent.getParcelableExtra(Constants.EXTRA_KEY_KID) : null;
        if (intent == null
                || kid == null) {
            Log.i(TAG, "No kid set in intent extra: " +
                    Constants.EXTRA_KEY_KID);
            return;
        } else {
            Log.i(TAG, "Kid: " + kid.getKidName());
        }

        int operation = intent.getIntExtra(Constants.EXTRA_KEY_OPERATION, Constants.NO_OP);
        Log.i(TAG, "Operation: " + operation);

        switch (operation) {
            case Constants.INSERT:
                addKid(kid);
                showMsg(R.string.kid_added);
                break;
            case Constants.UPDATE:
                mKidsListAdapter.replace(kid);
                showMsg(R.string.kid_updated);
                break;
            case Constants.DELETE:
                mKidsListAdapter.remove(kid);
                showMsg(R.string.kid_deleted);
                break;
            default:
                Log.e(TAG, "No op");
        }
    }

    /**
     * showMsg
     *
     * @param msgId int
     */
    private void showMsg(final int msgId) {
        Log.i(TAG, "msgId: " + msgId);

        runOnUiThread(new Runnable() {
            public void run() {
                if (snackbar != null
                        && snackbar.isShown()) {
                    return;
                }

                if (recyclerView != null) {
                    snackbar = Snackbar.make(recyclerView,
                            msgId,
                            Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
    }

    /**
     * addKid
     *
     * @param kid KidWrapper
     */
    private void addKid(final KidWrapper kid) {
        runOnUiThread(new Runnable() {
            public void run() {
                noKids.setVisibility(View.GONE);
                noKidsLayout.setVisibility(View.GONE);

                mKidsListAdapter.add(kid);
            }
        });
    }

    private final ValueEventListener initializeKid = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.i(TAG, "onDataChange");
            noKids.setVisibility(View.GONE);
            noKidsLayout.setVisibility(View.GONE);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            Log.e(TAG, "onCancelled: " + databaseError);
        }
    };

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_all) {
            fab.setVisibility(View.VISIBLE);

            mClassDateListAdapter.cleanup();

            mKidsListAdapter = new KidsListAdapter(null);
            recyclerView.setAdapter(mKidsListAdapter);

            // Make sure new events are visible
            mKidsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    recyclerView.smoothScrollToPosition(mKidsListAdapter.getItemCount());
                }
            });
        } else if (id == R.id.nav_morning) {
            fab.setVisibility(View.VISIBLE);

            mClassDateListAdapter.cleanup();

            final Query query = FirebaseUtils.getInstance().getDatabaseKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.AM);
            mKidsListAdapter = new KidsListAdapter(query);
            recyclerView.setAdapter(mKidsListAdapter);

            // Make sure new events are visible
            mKidsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    recyclerView.smoothScrollToPosition(mKidsListAdapter.getItemCount());
                }
            });
        } else if (id == R.id.nav_night) {
            fab.setVisibility(View.VISIBLE);

            mClassDateListAdapter.cleanup();

            final Query query = FirebaseUtils.getInstance().getDatabaseKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.PM);
            mKidsListAdapter = new KidsListAdapter(query);
            recyclerView.setAdapter(mKidsListAdapter);

            // Make sure new events are visible
            mKidsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    recyclerView.smoothScrollToPosition(mKidsListAdapter.getItemCount());
                }
            });
        } else if (id == R.id.nav_about) {
            Log.i(TAG, "nav_about");
        } else if (id == R.id.nav_dates) {
            fab.setVisibility(View.GONE);

            mKidsListAdapter.cleanup();
            recyclerView.setAdapter(mClassDateListAdapter);

            // Make sure new events are visible
            mClassDateListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    recyclerView.smoothScrollToPosition(mClassDateListAdapter.getItemCount());
                }
            });
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}