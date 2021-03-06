package com.fount.seed.kids;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fount.seed.attendance.ClassDateListAdapter;
import com.fount.seed.R;
import com.fount.seed.register.AddKidActivity;
import com.fount.seed.utils.Constants;
import com.fount.seed.database.firebase.FirebaseUtils;
import com.google.firebase.database.Query;

public final class ChaletActivity extends KidsActivity {

    private static final String TAG = ChaletActivity.class.getSimpleName();

    @Override
    protected void setContentView() {
        setContentView(R.layout.activity_chalet);
    }

    @Override
    protected KidsListAdapter getKidsListAdapter() {
        return new KidsListAdapter(FirebaseUtils.getInstance().getChaletKids());
    }

    @Override
    protected ClassDateListAdapter getClassDateListAdapter() {
        return new ClassDateListAdapter(FirebaseUtils.getInstance().getDatesChalet(),
                Constants.CHALET);
    }

    @Override
    protected void setView() {
        add.setOnClickListener(view -> {
            Intent intent = new Intent(ChaletActivity.this.getApplicationContext(),
                    AddKidActivity.class);
            startActivityForResult(intent, Constants.INSERT);
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout_chalet);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view_chalet);
        Menu menu = navigationView.getMenu();

        MenuItem chalet3pm = menu.findItem(R.id.nav_chalet3);
        SpannableString chalet3pmSS = new SpannableString(chalet3pm.getTitle());
        chalet3pmSS.setSpan(new TextAppearanceSpan(this, R.style.TextAppearancePurple), 0, chalet3pmSS.length(), 0);
        chalet3pm.setTitle(chalet3pmSS);

        MenuItem chalet4pm = menu.findItem(R.id.nav_chalet4);
        SpannableString chalet4pmSS = new SpannableString(chalet4pm.getTitle());
        chalet4pmSS.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceFuchsia), 0, chalet4pmSS.length(), 0);
        chalet4pm.setTitle(chalet4pmSS);

        MenuItem chalet7pm = menu.findItem(R.id.nav_chalet7);
        SpannableString chalet7pmSS = new SpannableString(chalet7pm.getTitle());
        chalet7pmSS.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceYellow), 0, chalet7pmSS.length(), 0);
        chalet7pm.setTitle(chalet7pmSS);

        MenuItem chalet8pm = menu.findItem(R.id.nav_chalet8);
        SpannableString chalet8pmSS = new SpannableString(chalet8pm.getTitle());
        chalet8pmSS.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceBlue), 0, chalet8pmSS.length(), 0);
        chalet8pm.setTitle(chalet8pmSS);

        MenuItem chalet9pm = menu.findItem(R.id.nav_chalet9);
        SpannableString chalet9pmSS = new SpannableString(chalet9pm.getTitle());
        chalet9pmSS.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceGreen), 0, chalet9pmSS.length(), 0);
        chalet9pm.setTitle(chalet9pmSS);

        MenuItem chalet10pm = menu.findItem(R.id.nav_chalet10);
        SpannableString chalet10pmSS = new SpannableString(chalet10pm.getTitle());
        chalet10pmSS.setSpan(new TextAppearanceSpan(this, R.style.TextAppearanceRed), 0, chalet10pmSS.length(), 0);
        chalet10pm.setTitle(chalet10pmSS);

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
            } else if (id == R.id.nav_chalet3) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_3);
            } else if (id == R.id.nav_chalet4) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_4);
            } else if (id == R.id.nav_chalet5) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_5);
            } else if (id == R.id.nav_chalet6) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_6);
            } else if (id == R.id.nav_chalet7) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_7);
            } else if (id == R.id.nav_chalet8) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_8);
            } else if (id == R.id.nav_chalet9) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_9);
            } else if (id == R.id.nav_chalet10) {
                query = FirebaseUtils.getInstance().getChaletKids().orderByChild(Constants.CLASS_ROOM).equalTo(Constants.CHALET_10);
            }

            if (query != null) {
                mKidsListAdapter = new KidsListAdapter(FirebaseUtils.getInstance().getChaletKids(),
                        query);
            } else {
                mKidsListAdapter = new KidsListAdapter(FirebaseUtils.getInstance().getChaletKids());
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

        final DrawerLayout drawer = findViewById(R.id.drawer_layout_chalet);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
