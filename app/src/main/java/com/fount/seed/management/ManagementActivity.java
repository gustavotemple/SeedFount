package com.fount.seed.management;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fount.seed.R;
import com.fount.seed.database.room.ChaletEntity;
import com.fount.seed.database.room.RoomEntity;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.vicmikhailau.maskededittext.MaskedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagementActivity extends AppCompatActivity {

    private static final String TAG = ManagementActivity.class.getSimpleName();
    private SchoolViewModel schoolViewModel;

    @BindView(R.id.roomTitle0)
    public TextView roomTitle0;
    @BindView(R.id.roomTitle1)
    public TextView roomTitle1;
    @BindView(R.id.roomTitle2)
    public TextView roomTitle2;

    @BindView(R.id.roomFromLayout0)
    public TextInputLayout roomFromLayout0;
    @BindView(R.id.roomFromText0)
    public MaskedEditText roomFromText0;
    @BindView(R.id.roomToLayout0)
    public TextInputLayout roomToLayout0;
    @BindView(R.id.roomToText0)
    public MaskedEditText roomToText0;
    @BindView(R.id.roomNumberLayout0)
    public TextInputLayout roomNumberLayout0;
    @BindView(R.id.roomNumberText0)
    public MaskedEditText roomNumberText0;

    @BindView(R.id.roomFromLayout1)
    public TextInputLayout roomFromLayout1;
    @BindView(R.id.roomFromText1)
    public MaskedEditText roomFromText1;
    @BindView(R.id.roomToLayout1)
    public TextInputLayout roomToLayout1;
    @BindView(R.id.roomToText1)
    public MaskedEditText roomToText1;
    @BindView(R.id.roomNumberLayout1)
    public TextInputLayout roomNumberLayout1;
    @BindView(R.id.roomNumberText1)
    public MaskedEditText roomNumberText1;

    @BindView(R.id.roomFromLayout2)
    public TextInputLayout roomFromLayout2;
    @BindView(R.id.roomFromText2)
    public MaskedEditText roomFromText2;
    @BindView(R.id.roomToLayout2)
    public TextInputLayout roomToLayout2;
    @BindView(R.id.roomToText2)
    public MaskedEditText roomToText2;
    @BindView(R.id.roomNumberLayout2)
    public TextInputLayout roomNumberLayout2;
    @BindView(R.id.roomNumberText2)
    public MaskedEditText roomNumberText2;

    @BindView(R.id.roomFromLayout3)
    public TextInputLayout roomFromLayout3;
    @BindView(R.id.roomFromText3)
    public MaskedEditText roomFromText3;
    @BindView(R.id.roomToLayout3)
    public TextInputLayout roomToLayout3;
    @BindView(R.id.roomToText3)
    public MaskedEditText roomToText3;
    @BindView(R.id.roomNumberLayout3)
    public TextInputLayout roomNumberLayout3;
    @BindView(R.id.roomNumberText3)
    public MaskedEditText roomNumberText3;

    @BindView(R.id.roomFromLayout4)
    public TextInputLayout roomFromLayout4;
    @BindView(R.id.roomFromText4)
    public MaskedEditText roomFromText4;
    @BindView(R.id.roomToLayout4)
    public TextInputLayout roomToLayout4;
    @BindView(R.id.roomToText4)
    public MaskedEditText roomToText4;
    @BindView(R.id.roomNumberLayout4)
    public TextInputLayout roomNumberLayout4;
    @BindView(R.id.roomNumberText4)
    public MaskedEditText roomNumberText4;




















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

        final Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(),
                Constants.FONT);
        setTypeface(typeface);

        schoolViewModel = ViewModelProviders.of(this).get(SchoolViewModel.class);
        schoolViewModel.getAllRooms().observe(this, rooms -> {
            if (rooms != null && !rooms.isEmpty()) {
                for (final RoomEntity room : rooms) {
                    if (room.getRow() == 0) {
                        roomFromText0.setText(room.getFrom());
                        roomToText0.setText(room.getTo());
                        roomNumberText0.setText(room.getNumber());
                    } else if (room.getRow() == 1) {
                        roomFromText1.setText(room.getFrom());
                        roomToText1.setText(room.getTo());
                        roomNumberText1.setText(room.getNumber());
                    } else if (room.getRow() == 2) {
                        roomFromText2.setText(room.getFrom());
                        roomToText2.setText(room.getTo());
                        roomNumberText2.setText(room.getNumber());
                    } else if (room.getRow() == 3) {
                        roomFromText3.setText(room.getFrom());
                        roomToText3.setText(room.getTo());
                        roomNumberText3.setText(room.getNumber());
                    } else if (room.getRow() == 4) {
                        roomFromText4.setText(room.getFrom());
                        roomToText4.setText(room.getTo());
                        roomNumberText4.setText(room.getNumber());
                    }
                }
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
                "Clicked", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void setTypeface(final Typeface typeface) {
        roomTitle0.setTypeface(typeface);
        roomTitle1.setTypeface(typeface);
        roomTitle2.setTypeface(typeface);

        roomFromLayout0.setTypeface(typeface);
        roomFromText0.setTypeface(typeface);
        roomToLayout0.setTypeface(typeface);
        roomToText0.setTypeface(typeface);
        roomNumberLayout0.setTypeface(typeface);
        roomNumberText0.setTypeface(typeface);

        roomFromLayout1.setTypeface(typeface);
        roomFromText1.setTypeface(typeface);
        roomToLayout1.setTypeface(typeface);
        roomToText1.setTypeface(typeface);
        roomNumberLayout1.setTypeface(typeface);
        roomNumberText1.setTypeface(typeface);

        roomFromLayout2.setTypeface(typeface);
        roomFromText2.setTypeface(typeface);
        roomToLayout2.setTypeface(typeface);
        roomToText2.setTypeface(typeface);
        roomNumberLayout2.setTypeface(typeface);
        roomNumberText2.setTypeface(typeface);

        roomFromLayout3.setTypeface(typeface);
        roomFromText3.setTypeface(typeface);
        roomToLayout3.setTypeface(typeface);
        roomToText3.setTypeface(typeface);
        roomNumberLayout3.setTypeface(typeface);
        roomNumberText3.setTypeface(typeface);

        roomFromLayout4.setTypeface(typeface);
        roomFromText4.setTypeface(typeface);
        roomToLayout4.setTypeface(typeface);
        roomToText4.setTypeface(typeface);
        roomNumberLayout4.setTypeface(typeface);
        roomNumberText4.setTypeface(typeface);
    }

    public boolean isCancel() {

        // Reset errors.
        roomFromText0.setError(null);
        roomToText0.setError(null);
        roomNumberText0.setError(null);
        roomFromText1.setError(null);
        roomToText1.setError(null);
        roomNumberText1.setError(null);
        roomFromText2.setError(null);
        roomToText2.setError(null);
        roomNumberText2.setError(null);
        roomFromText3.setError(null);
        roomToText3.setError(null);
        roomNumberText3.setError(null);
        roomFromText4.setError(null);
        roomToText4.setError(null);
        roomNumberText4.setError(null);

        // Store values at the time of the register attempt.
        String roomFromText0s = roomFromText0.getText().toString();
        String roomToText0s = roomToText0.getText().toString();
        String roomNumberText0s = roomNumberText0.getText().toString();
        String roomFromText1s = roomFromText1.getText().toString();
        String roomToText1s = roomToText1.getText().toString();
        String roomNumberText1s = roomNumberText1.getText().toString();
        String roomFromText2s = roomFromText2.getText().toString();
        String roomToText2s = roomToText2.getText().toString();
        String roomNumberText2s = roomNumberText2.getText().toString();
        String roomFromText3s = roomFromText3.getText().toString();
        String roomToText3s = roomToText3.getText().toString();
        String roomNumberText3s = roomNumberText3.getText().toString();
        String roomFromText4s = roomFromText4.getText().toString();
        String roomToText4s = roomToText4.getText().toString();
        String roomNumberText4s = roomNumberText4.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(roomFromText0s)) {
            roomFromText0.setError(getString(R.string.error_field_required));
            focusView = roomFromText0;
            cancel = true;
        } else if (isFieldInvalid(roomFromText0s)) {
            roomFromText0.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText0;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText0s)) {
            roomToText0.setError(getString(R.string.error_field_required));
            focusView = roomToText0;
            cancel = true;
        } else if (isFieldInvalid(roomToText0s)) {
            roomToText0.setError(getString(R.string.error_field_invalid));
            focusView = roomToText0;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomNumberText0s)) {
            roomNumberText0.setError(getString(R.string.error_field_required));
            focusView = roomNumberText0;
            cancel = true;
        } else if (isFieldInvalid(roomNumberText0s)) {
            roomNumberText0.setError(getString(R.string.error_field_invalid));
            focusView = roomNumberText0;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomFromText1s)) {
            roomFromText1.setError(getString(R.string.error_field_required));
            focusView = roomFromText1;
            cancel = true;
        } else if (isFieldInvalid(roomFromText1s)) {
            roomFromText1.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText1;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText1s)) {
            roomToText1.setError(getString(R.string.error_field_required));
            focusView = roomToText1;
            cancel = true;
        } else if (isFieldInvalid(roomToText1s)) {
            roomToText1.setError(getString(R.string.error_field_invalid));
            focusView = roomToText1;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomNumberText1s)) {
            roomNumberText1.setError(getString(R.string.error_field_required));
            focusView = roomNumberText1;
            cancel = true;
        } else if (isFieldInvalid(roomNumberText1s)) {
            roomNumberText1.setError(getString(R.string.error_field_invalid));
            focusView = roomNumberText1;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomFromText2s)) {
            roomFromText2.setError(getString(R.string.error_field_required));
            focusView = roomFromText2;
            cancel = true;
        } else if (isFieldInvalid(roomFromText2s)) {
            roomFromText2.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText2;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText2s)) {
            roomToText2.setError(getString(R.string.error_field_required));
            focusView = roomToText2;
            cancel = true;
        } else if (isFieldInvalid(roomToText2s)) {
            roomToText2.setError(getString(R.string.error_field_invalid));
            focusView = roomToText2;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomNumberText2s)) {
            roomNumberText2.setError(getString(R.string.error_field_required));
            focusView = roomNumberText2;
            cancel = true;
        } else if (isFieldInvalid(roomNumberText2s)) {
            roomNumberText2.setError(getString(R.string.error_field_invalid));
            focusView = roomNumberText2;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomFromText3s)) {
            roomFromText3.setError(getString(R.string.error_field_required));
            focusView = roomFromText3;
            cancel = true;
        } else if (isFieldInvalid(roomFromText3s)) {
            roomFromText3.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText3;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText3s)) {
            roomToText3.setError(getString(R.string.error_field_required));
            focusView = roomToText3;
            cancel = true;
        } else if (isFieldInvalid(roomToText3s)) {
            roomToText3.setError(getString(R.string.error_field_invalid));
            focusView = roomToText3;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomNumberText3s)) {
            roomNumberText3.setError(getString(R.string.error_field_required));
            focusView = roomNumberText3;
            cancel = true;
        } else if (isFieldInvalid(roomNumberText3s)) {
            roomNumberText3.setError(getString(R.string.error_field_invalid));
            focusView = roomNumberText3;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomFromText4s)) {
            roomFromText4.setError(getString(R.string.error_field_required));
            focusView = roomFromText4;
            cancel = true;
        } else if (isFieldInvalid(roomFromText4s)) {
            roomFromText4.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText4;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText4s)) {
            roomToText4.setError(getString(R.string.error_field_required));
            focusView = roomToText4;
            cancel = true;
        } else if (isFieldInvalid(roomToText4s)) {
            roomToText4.setError(getString(R.string.error_field_invalid));
            focusView = roomToText4;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomNumberText4s)) {
            roomNumberText4.setError(getString(R.string.error_field_required));
            focusView = roomNumberText4;
            cancel = true;
        } else if (isFieldInvalid(roomNumberText4s)) {
            roomNumberText4.setError(getString(R.string.error_field_invalid));
            focusView = roomNumberText4;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return cancel;
    }

    public boolean isFieldInvalid(@NonNull String field) {
        return field.length() < 2;
    }

    @OnClick(R.id.save)
    public void save() {

        if (isCancel()) {
            return;
        }

        String roomFromText0s = roomFromText0.getText().toString();
        String roomToText0s = roomToText0.getText().toString();
        String roomNumberText0s = roomNumberText0.getText().toString();
        String roomFromText1s = roomFromText1.getText().toString();
        String roomToText1s = roomToText1.getText().toString();
        String roomNumberText1s = roomNumberText1.getText().toString();
        String roomFromText2s = roomFromText2.getText().toString();
        String roomToText2s = roomToText2.getText().toString();
        String roomNumberText2s = roomNumberText2.getText().toString();
        String roomFromText3s = roomFromText3.getText().toString();
        String roomToText3s = roomToText3.getText().toString();
        String roomNumberText3s = roomNumberText3.getText().toString();
        String roomFromText4s = roomFromText4.getText().toString();
        String roomToText4s = roomToText4.getText().toString();
        String roomNumberText4s = roomNumberText4.getText().toString();

        RoomEntity roomEntity0 = new RoomEntity(0,roomNumberText0s, roomFromText0s, roomToText0s);
        RoomEntity roomEntity1 = new RoomEntity(1,roomNumberText1s, roomFromText1s, roomToText1s);
        RoomEntity roomEntity2 = new RoomEntity(2,roomNumberText2s, roomFromText2s, roomToText2s);
        RoomEntity roomEntity3 = new RoomEntity(3,roomNumberText3s, roomFromText3s, roomToText3s);
        RoomEntity roomEntity4 = new RoomEntity(4,roomNumberText4s, roomFromText4s, roomToText4s);

        schoolViewModel.deleteAllRooms();
        schoolViewModel.insert(roomEntity0);
        schoolViewModel.insert(roomEntity1);
        schoolViewModel.insert(roomEntity2);
        schoolViewModel.insert(roomEntity3);
        schoolViewModel.insert(roomEntity4);

        ChaletEntity chaletEntity0 = new ChaletEntity("", "", "");
        ChaletEntity chaletEntity1 = new ChaletEntity("", "", "");
        ChaletEntity chaletEntity2 = new ChaletEntity("", "", "");
        ChaletEntity chaletEntity3 = new ChaletEntity("", "", "");
        ChaletEntity chaletEntity4 = new ChaletEntity("", "", "");

        schoolViewModel.deleteAllChalets();
        schoolViewModel.insert(chaletEntity0);
        schoolViewModel.insert(chaletEntity1);
        schoolViewModel.insert(chaletEntity2);
        schoolViewModel.insert(chaletEntity3);
        schoolViewModel.insert(chaletEntity4);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
