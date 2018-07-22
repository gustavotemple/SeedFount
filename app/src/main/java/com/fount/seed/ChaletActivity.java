package com.fount.seed;

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

import com.fount.seed.utils.Constants;
import com.fount.seed.utils.FirebaseUtils;
import com.google.firebase.database.Query;

public final class ChaletActivity extends KidsActivity {

    private static final String TAG = ChaletActivity.class.getSimpleName();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_chalet);
    }

    @Override
    protected KidsListAdapter getKidsListAdapter() {
        return new KidsListAdapter(FirebaseUtils.getInstance().getChaletKids(),
                null, getPackageName() + "." + TAG, Constants.CHALET);
    }

    @Override
    protected void setView() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChaletActivity.this.getApplicationContext(),
                        RegisterActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_PARENT, getPackageName() + "." + TAG);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout_chalet);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_chalet);
        navigationView.setNavigationItemSelectedListener(this);

        FirebaseUtils.getInstance().getChaletKids().addListenerForSingleValueEvent(initializeKid);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout_chalet);
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
            fab.setVisibility(View.GONE);
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
            fab.setVisibility(View.VISIBLE);
            setTitle(FirebaseUtils.DateGenerator.formatDate(FirebaseUtils.DateGenerator.getPeriod()));

            mClassDateListAdapter.cleanup();

            Query query = null;
            if (id == R.id.nav_all) {
                query = null;
            } else if (id == R.id.nav_chalet3_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_3_AM);
            } else if (id == R.id.nav_chalet3_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_3_PM);
            } else if (id == R.id.nav_chalet4_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_4_AM);
            } else if (id == R.id.nav_chalet4_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_4_PM);
            } else if (id == R.id.nav_chalet5_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_5_AM);
            } else if (id == R.id.nav_chalet5_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_5_PM);
            } else if (id == R.id.nav_chalet6_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_6_AM);
            } else if (id == R.id.nav_chalet6_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_6_PM);
            } else if (id == R.id.nav_chalet7_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_7_AM);
            } else if (id == R.id.nav_chalet7_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_7_PM);
            } else if (id == R.id.nav_chalet8_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_8_AM);
            } else if (id == R.id.nav_chalet8_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_8_PM);
            } else if (id == R.id.nav_chalet9_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_9_AM);
            } else if (id == R.id.nav_chalet9_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_9_PM);
            } else if (id == R.id.nav_chalet10_am) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_10_AM);
            } else if (id == R.id.nav_chalet10_pm) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_10_PM);
            }

            mKidsListAdapter = new KidsListAdapter(FirebaseUtils.getInstance().getChaletKids(),
                    query, getPackageName() + "." + TAG, Constants.CHALET);
            recyclerView.setAdapter(mKidsListAdapter);

            // Make sure new events are visible
            mKidsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onItemRangeChanged(int positionStart, int itemCount) {
                    recyclerView.smoothScrollToPosition(mKidsListAdapter.getItemCount());
                }
            });
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout_chalet);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
