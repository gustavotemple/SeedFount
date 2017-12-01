package com.fount.seed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.wrappers.ClassDate;
import com.fount.seed.wrappers.StudentAttendance;

import java.util.HashMap;
import java.util.Map;

public class StudentAttendanceActivity extends AppCompatActivity {

    private StudentAttendanceListAdapter mStudentAttendanceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance);

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        final Intent intent = getIntent();
        final ClassDate classDate = intent != null ? (ClassDate) intent.getSerializableExtra(Constants.EXTRA_KEY_DATE) : null;

        if (classDate != null) {
            setTitle(classDate.getDate());
        }

        final RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mStudentAttendanceListAdapter = new StudentAttendanceListAdapter();
        recyclerView.setAdapter(mStudentAttendanceListAdapter);
        mStudentAttendanceListAdapter.clear();

        if (classDate == null
                || classDate.getStudentAttendance() == null) {
            return;
        }

        for (Map.Entry<String, HashMap<String, Boolean>> student : classDate.getStudentAttendance().entrySet()) {
            StudentAttendance studentAttendance = new StudentAttendance(student.getKey());
            studentAttendance.setLetters(student.getValue());
            mStudentAttendanceListAdapter.add(studentAttendance);
        }
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
