package com.fount.seed.attendance;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.fount.seed.R;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.wrappers.ClassDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudentAttendanceActivity extends AppCompatActivity {

    private static final String TAG = StudentAttendanceActivity.class.getSimpleName();

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.event_note)
    public FloatingActionButton eventNote;

    private StudentAttendanceListAdapter mStudentAttendanceListAdapter;
    private Snackbar snackbar;
    private ClassDate classDate;
    private String database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        ButterKnife.bind(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStudentAttendanceListAdapter = new StudentAttendanceListAdapter();
        recyclerView.setAdapter(mStudentAttendanceListAdapter);

        final Intent intent = getIntent();
        classDate = intent != null ? (ClassDate) intent.getSerializableExtra(Constants.EXTRA_KEY_DATE) : null;
        database = intent != null ? intent.getStringExtra(Constants.EXTRA_KEY_DATABASE) : null;

        if (classDate == null
                || database == null) {
            return;
        }

        setTitle(classDate.getDate());

        eventNote.setOnClickListener(view -> {
            final Intent i = new Intent(StudentAttendanceActivity.this.getApplicationContext(),
                    CommentActivity.class);
            i.putExtra(Constants.EXTRA_KEY_DATE, classDate);
            i.putExtra(Constants.EXTRA_KEY_DATABASE, database);
            startActivityForResult(i, Constants.INSERT);
        });

        if (classDate.getStudentAttendance() == null) {
            return;
        }

        mStudentAttendanceListAdapter.init(classDate.getStudentAttendance());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult");

        final ClassDate classDate = data != null ? (ClassDate) data
                .getSerializableExtra(Constants.EXTRA_KEY_DATE) : null;
        if (data == null
                || classDate == null) {
            Log.i(TAG, "No classDate set in intent extra: " +
                    Constants.EXTRA_KEY_DATE);
            return;
        } else {
            this.classDate.setComment(classDate.getComment());
            Log.i(TAG, "Comment: " + this.classDate.getComment());
        }

        switch (resultCode) {
            case Constants.INSERT:
                showMsg(R.string.comment_added);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_attendance, menu);
        final MenuItem searchMenuItem = menu.findItem(R.id.menu_search);
        final SearchView mSearchView = (SearchView) searchMenuItem.getActionView();
        mSearchView.setOnQueryTextListener(listener);
        return true;
    }

    final private SearchView.OnQueryTextListener listener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mStudentAttendanceListAdapter.getFilter().filter(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            mStudentAttendanceListAdapter.getFilter().filter(newText);
            return false;
        }
    };
}
