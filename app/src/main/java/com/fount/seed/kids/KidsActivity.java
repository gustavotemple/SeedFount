package com.fount.seed.kids;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fount.seed.attendance.ClassDateListAdapter;
import com.fount.seed.R;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.database.firebase.FirebaseUtils;
import com.fount.seed.wrappers.KidWrapper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class KidsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = KidsActivity.class.getSimpleName();
    private Snackbar snackbar;

    @BindView(R.id.no_kids_layout)
    public RelativeLayout noKidsLayout;
    @BindView(R.id.no_kids)
    public TextView noKids;
    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.add)
    public FloatingActionButton add;

    protected KidsListAdapter mKidsListAdapter;
    protected ClassDateListAdapter mClassDateListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        ButterKnife.bind(this);

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        final Typeface type = Typeface.createFromAsset(getAssets(), Constants.FONT);
        noKids.setTypeface(type);
        setSupportActionBar(toolbar);
        add.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setView();

        mClassDateListAdapter = getClassDateListAdapter();
        mKidsListAdapter = getKidsListAdapter();
        recyclerView.setAdapter(mKidsListAdapter);

        // Make sure new events are visible
        mKidsListAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeChanged(int positionStart, int itemCount) {
                recyclerView.smoothScrollToPosition(mKidsListAdapter.getItemCount());
            }
        });

    }

    protected abstract void setContentView();

    protected abstract void setView();

    protected abstract KidsListAdapter getKidsListAdapter();

    protected abstract ClassDateListAdapter getClassDateListAdapter();

    @Override
    protected void onResume() {
        super.onResume();

        setTitle(FirebaseUtils.DateGenerator.formatDate(FirebaseUtils.DateGenerator.getPeriod()));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");

        final KidWrapper kid = data != null ? (KidWrapper) data.getParcelableExtra(Constants.EXTRA_KEY_KID) : null;
        if (data == null
                || kid == null) {
            Log.i(TAG, "No kid set in intent extra: " +
                    Constants.EXTRA_KEY_KID);
            return;
        } else {
            Log.i(TAG, "Kid: " + kid.getKidName());
        }

        switch (resultCode) {
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

        runOnUiThread(() -> {
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
        });
    }

    /**
     * addKid
     *
     * @param kid KidWrapper
     */
    private void addKid(final KidWrapper kid) {
        runOnUiThread(() -> {
            noKids.setVisibility(View.GONE);
            noKidsLayout.setVisibility(View.GONE);

            mKidsListAdapter.add(kid);
        });
    }

    protected final ValueEventListener initializeKid = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            Log.i(TAG, "onDataChange");

            if (dataSnapshot.getValue() != null) {
                noKids.setVisibility(View.GONE);
                noKidsLayout.setVisibility(View.GONE);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e(TAG, "onCancelled: " + databaseError);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(@NonNull final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_kids, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                super.recreate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
