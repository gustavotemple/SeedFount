package com.fount.seed.kids;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.fount.seed.attendance.ClassDateListAdapter;
import com.fount.seed.R;
import com.fount.seed.register.AddKidActivity;
import com.fount.seed.utils.Constants;
import com.fount.seed.database.firebase.FirebaseUtils;
import com.google.firebase.database.Query;

public final class RoomActivity extends KidsActivity {

    private static final String TAG = RoomActivity.class.getSimpleName();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_babies);
    }

    @Override
    protected KidsListAdapter getKidsListAdapter() {
        return new KidsListAdapter(FirebaseUtils.getInstance().getRoomKids());
    }

    @Override
    protected ClassDateListAdapter getClassDateListAdapter() {
        return new ClassDateListAdapter(FirebaseUtils.getInstance().getDatesRoom(),
                Constants.ROOM);
    }

    @Override
    protected void setView() {
        add.setOnClickListener(view -> {
            Intent intent = new Intent(RoomActivity.this.getApplicationContext(),
                    AddKidActivity.class);
            startActivityForResult(intent, Constants.INSERT);
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout_babies);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_babies);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUtils.getInstance().getRoomKids().addListenerForSingleValueEvent(initializeKid);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_babies);
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

        if (id == R.id.nav_about) {
            Log.i(TAG, "nav_about");
        } else if (id == R.id.nav_dates) {
            add.setVisibility(View.GONE);
            setTitle(R.string.class_dates);

            mKidsListAdapter.cleanup();
            recyclerView.setAdapter(mClassDateListAdapter);

            // Make sure new events are visible
            mClassDateListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    recyclerView.smoothScrollToPosition(mClassDateListAdapter.getItemCount());
                }
            });
        } else {
            add.setVisibility(View.VISIBLE);
            setTitle(FirebaseUtils.DateGenerator.formatDate(FirebaseUtils.DateGenerator.getPeriod()));

            mClassDateListAdapter.cleanup();

            Query query = null;
            if (id == R.id.nav_all) {
                query = null;
            } else if (id == R.id.nav_n1) {
                query = FirebaseUtils.getInstance().getRoomKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.ROOM_1);
            } else if (id == R.id.nav_n2) {
                query = FirebaseUtils.getInstance().getRoomKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.ROOM_2);
            } else if (id == R.id.nav_n3) {
                query = FirebaseUtils.getInstance().getRoomKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.ROOM_3);
            } else if (id == R.id.nav_n4) {
                query = FirebaseUtils.getInstance().getRoomKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.ROOM_4);
            } else if (id == R.id.nav_n5) {
                query = FirebaseUtils.getInstance().getRoomKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.ROOM_5);
            }

            if (query != null) {
                mKidsListAdapter = new KidsListAdapter(FirebaseUtils.getInstance().getRoomKids(),
                        query);
            } else {
                mKidsListAdapter = new KidsListAdapter(FirebaseUtils.getInstance().getRoomKids());
            }

            recyclerView.setAdapter(mKidsListAdapter);

            // Make sure new events are visible
            mKidsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    recyclerView.smoothScrollToPosition(mKidsListAdapter.getItemCount());
                }
            });
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout_babies);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
