package com.fount.seed.attendance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fount.seed.R;
import com.fount.seed.database.firebase.FirebaseUtils;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.wrappers.ClassDate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentActivity extends AppCompatActivity {

    @BindView(R.id.comment)
    public EditText comment;

    @BindView(R.id.save_comment)
    public Button saveComment;

    private ClassDate classDate;
    private String database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        final Intent intent = getIntent();
        classDate = intent != null ? (ClassDate) intent.getSerializableExtra(Constants.EXTRA_KEY_DATE) : null;
        database = intent != null ? intent.getStringExtra(Constants.EXTRA_KEY_DATABASE) : null;

        if (classDate == null
                || database == null) {
            return;
        }

        comment.setText(classDate.getComment());

        saveComment.setOnClickListener(view -> {
            classDate.setComment(comment.getText().toString());
            FirebaseUtils.getInstance().saveComment(classDate, database);

            final Intent i = new Intent();
            i.putExtra(Constants.EXTRA_KEY_DATE, classDate);
            setResult(Constants.INSERT, i);
            finish();
        });
    }
}
