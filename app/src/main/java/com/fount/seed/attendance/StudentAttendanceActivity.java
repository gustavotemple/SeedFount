package com.fount.seed.attendance;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fount.seed.R;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.wrappers.ClassDate;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudentAttendanceActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    public RecyclerView recyclerView;

    @BindView(R.id.event_note)
    public FloatingActionButton eventNote;

    private StudentAttendanceListAdapter mStudentAttendanceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);
        ButterKnife.bind(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
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
        final ClassDate classDate = intent != null ? (ClassDate) intent.getSerializableExtra(Constants.EXTRA_KEY_DATE) : null;

        if (classDate == null
                || classDate.getStudentAttendance() == null) {
            return;
        }

        setTitle(classDate.getDate());
        mStudentAttendanceListAdapter.init(classDate.getStudentAttendance());
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.event_note)
    public void roomActivity(View view) {
        startActivity(new Intent(StudentAttendanceActivity.this.getApplicationContext(),
                CommentActivity.class));
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
