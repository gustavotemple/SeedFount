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
import android.view.View;
import android.widget.TextView;

import com.fount.seed.R;
import com.fount.seed.database.room.RoomEntity;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.vicmikhailau.maskededittext.MaskedEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ManagementActivity extends AppCompatActivity {

    private SchoolViewModel schoolViewModel;

    @BindView(R.id.roomTitle0)
    public TextView roomTitle0;
    @BindView(R.id.roomTitle1)
    public TextView roomTitle1;
    @BindView(R.id.roomTitle2)
    public TextView roomTitle2;

    @BindView(R.id.chaletTitle0)
    public TextView chaletTitle0;
    @BindView(R.id.chaletTitle1)
    public TextView chaletTitle1;
    @BindView(R.id.chaletTitle2)
    public TextView chaletTitle2;

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

    @BindView(R.id.chaletFromLayout0)
    public TextInputLayout chaletFromLayout0;
    @BindView(R.id.chaletFromText0)
    public MaskedEditText chaletFromText0;
    @BindView(R.id.chaletToLayout0)
    public TextInputLayout chaletToLayout0;
    @BindView(R.id.chaletToText0)
    public MaskedEditText chaletToText0;
    @BindView(R.id.chaletNumberLayout0)
    public TextInputLayout chaletNumberLayout0;
    @BindView(R.id.chaletNumberText0)
    public MaskedEditText chaletNumberText0;

    @BindView(R.id.chaletFromLayout1)
    public TextInputLayout chaletFromLayout1;
    @BindView(R.id.chaletFromText1)
    public MaskedEditText chaletFromText1;
    @BindView(R.id.chaletToLayout1)
    public TextInputLayout chaletToLayout1;
    @BindView(R.id.chaletToText1)
    public MaskedEditText chaletToText1;
    @BindView(R.id.chaletNumberLayout1)
    public TextInputLayout chaletNumberLayout1;
    @BindView(R.id.chaletNumberText1)
    public MaskedEditText chaletNumberText1;

    @BindView(R.id.chaletFromLayout2)
    public TextInputLayout chaletFromLayout2;
    @BindView(R.id.chaletFromText2)
    public MaskedEditText chaletFromText2;
    @BindView(R.id.chaletToLayout2)
    public TextInputLayout chaletToLayout2;
    @BindView(R.id.chaletToText2)
    public MaskedEditText chaletToText2;
    @BindView(R.id.chaletNumberLayout2)
    public TextInputLayout chaletNumberLayout2;
    @BindView(R.id.chaletNumberText2)
    public MaskedEditText chaletNumberText2;

    @BindView(R.id.chaletFromLayout3)
    public TextInputLayout chaletFromLayout3;
    @BindView(R.id.chaletFromText3)
    public MaskedEditText chaletFromText3;
    @BindView(R.id.chaletToLayout3)
    public TextInputLayout chaletToLayout3;
    @BindView(R.id.chaletToText3)
    public MaskedEditText chaletToText3;
    @BindView(R.id.chaletNumberLayout3)
    public TextInputLayout chaletNumberLayout3;
    @BindView(R.id.chaletNumberText3)
    public MaskedEditText chaletNumberText3;

    @BindView(R.id.chaletFromLayout4)
    public TextInputLayout chaletFromLayout4;
    @BindView(R.id.chaletFromText4)
    public MaskedEditText chaletFromText4;
    @BindView(R.id.chaletToLayout4)
    public TextInputLayout chaletToLayout4;
    @BindView(R.id.chaletToText4)
    public MaskedEditText chaletToText4;
    @BindView(R.id.chaletNumberLayout4)
    public TextInputLayout chaletNumberLayout4;
    @BindView(R.id.chaletNumberText4)
    public MaskedEditText chaletNumberText4;

    @BindView(R.id.chaletFromLayout5)
    public TextInputLayout chaletFromLayout5;
    @BindView(R.id.chaletFromText5)
    public MaskedEditText chaletFromText5;
    @BindView(R.id.chaletToLayout5)
    public TextInputLayout chaletToLayout5;
    @BindView(R.id.chaletToText5)
    public MaskedEditText chaletToText5;
    @BindView(R.id.chaletNumberLayout5)
    public TextInputLayout chaletNumberLayout5;
    @BindView(R.id.chaletNumberText5)
    public MaskedEditText chaletNumberText5;

    @BindView(R.id.chaletFromLayout6)
    public TextInputLayout chaletFromLayout6;
    @BindView(R.id.chaletFromText6)
    public MaskedEditText chaletFromText6;
    @BindView(R.id.chaletToLayout6)
    public TextInputLayout chaletToLayout6;
    @BindView(R.id.chaletToText6)
    public MaskedEditText chaletToText6;
    @BindView(R.id.chaletNumberLayout6)
    public TextInputLayout chaletNumberLayout6;
    @BindView(R.id.chaletNumberText6)
    public MaskedEditText chaletNumberText6;

    @BindView(R.id.chaletFromLayout7)
    public TextInputLayout chaletFromLayout7;
    @BindView(R.id.chaletFromText7)
    public MaskedEditText chaletFromText7;
    @BindView(R.id.chaletToLayout7)
    public TextInputLayout chaletToLayout7;
    @BindView(R.id.chaletToText7)
    public MaskedEditText chaletToText7;
    @BindView(R.id.chaletNumberLayout7)
    public TextInputLayout chaletNumberLayout7;
    @BindView(R.id.chaletNumberText7)
    public MaskedEditText chaletNumberText7;

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
            if (rooms == null || rooms.isEmpty()) {
                return;
            }

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
        });

        schoolViewModel.getAllChalets().observe(this, rooms -> {
            if (rooms == null || rooms.isEmpty()) {
                return;
            }

            for (final RoomEntity room : rooms) {
                if (room.getRow() == 0) {
                    chaletFromText0.setText(room.getFrom());
                    chaletToText0.setText(room.getTo());
                    chaletNumberText0.setText(room.getNumber());
                } else if (room.getRow() == 1) {
                    chaletFromText1.setText(room.getFrom());
                    chaletToText1.setText(room.getTo());
                    chaletNumberText1.setText(room.getNumber());
                } else if (room.getRow() == 2) {
                    chaletFromText2.setText(room.getFrom());
                    chaletToText2.setText(room.getTo());
                    chaletNumberText2.setText(room.getNumber());
                } else if (room.getRow() == 3) {
                    chaletFromText3.setText(room.getFrom());
                    chaletToText3.setText(room.getTo());
                    chaletNumberText3.setText(room.getNumber());
                } else if (room.getRow() == 4) {
                    chaletFromText4.setText(room.getFrom());
                    chaletToText4.setText(room.getTo());
                    chaletNumberText4.setText(room.getNumber());
                } else if (room.getRow() == 5) {
                    chaletFromText5.setText(room.getFrom());
                    chaletToText5.setText(room.getTo());
                    chaletNumberText5.setText(room.getNumber());
                } else if (room.getRow() == 6) {
                    chaletFromText6.setText(room.getFrom());
                    chaletToText6.setText(room.getTo());
                    chaletNumberText6.setText(room.getNumber());
                } else if (room.getRow() == 7) {
                    chaletFromText7.setText(room.getFrom());
                    chaletToText7.setText(room.getTo());
                    chaletNumberText7.setText(room.getNumber());
                }
            }
        });

        FloatingActionButton cloud = findViewById(R.id.cloud);
        cloud.setOnClickListener(view -> Snackbar.make(view,
                "Clicked", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }

    private void setTypeface(final Typeface typeface) {
        roomTitle0.setTypeface(typeface);
        roomTitle1.setTypeface(typeface);
        roomTitle2.setTypeface(typeface);

        chaletTitle0.setTypeface(typeface);
        chaletTitle1.setTypeface(typeface);
        chaletTitle2.setTypeface(typeface);

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

        chaletFromLayout0.setTypeface(typeface);
        chaletFromText0.setTypeface(typeface);
        chaletToLayout0.setTypeface(typeface);
        chaletToText0.setTypeface(typeface);
        chaletNumberLayout0.setTypeface(typeface);
        chaletNumberText0.setTypeface(typeface);

        chaletFromLayout1.setTypeface(typeface);
        chaletFromText1.setTypeface(typeface);
        chaletToLayout1.setTypeface(typeface);
        chaletToText1.setTypeface(typeface);
        chaletNumberLayout1.setTypeface(typeface);
        chaletNumberText1.setTypeface(typeface);

        chaletFromLayout2.setTypeface(typeface);
        chaletFromText2.setTypeface(typeface);
        chaletToLayout2.setTypeface(typeface);
        chaletToText2.setTypeface(typeface);
        chaletNumberLayout2.setTypeface(typeface);
        chaletNumberText2.setTypeface(typeface);

        chaletFromLayout3.setTypeface(typeface);
        chaletFromText3.setTypeface(typeface);
        chaletToLayout3.setTypeface(typeface);
        chaletToText3.setTypeface(typeface);
        chaletNumberLayout3.setTypeface(typeface);
        chaletNumberText3.setTypeface(typeface);

        chaletFromLayout4.setTypeface(typeface);
        chaletFromText4.setTypeface(typeface);
        chaletToLayout4.setTypeface(typeface);
        chaletToText4.setTypeface(typeface);
        chaletNumberLayout4.setTypeface(typeface);
        chaletNumberText4.setTypeface(typeface);

        chaletFromLayout5.setTypeface(typeface);
        chaletFromText5.setTypeface(typeface);
        chaletToLayout5.setTypeface(typeface);
        chaletToText5.setTypeface(typeface);
        chaletNumberLayout5.setTypeface(typeface);
        chaletNumberText5.setTypeface(typeface);

        chaletFromLayout6.setTypeface(typeface);
        chaletFromText6.setTypeface(typeface);
        chaletToLayout6.setTypeface(typeface);
        chaletToText6.setTypeface(typeface);
        chaletNumberLayout6.setTypeface(typeface);
        chaletNumberText6.setTypeface(typeface);

        chaletFromLayout7.setTypeface(typeface);
        chaletFromText7.setTypeface(typeface);
        chaletToLayout7.setTypeface(typeface);
        chaletToText7.setTypeface(typeface);
        chaletNumberLayout7.setTypeface(typeface);
        chaletNumberText7.setTypeface(typeface);
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

        chaletFromText0.setError(null);
        chaletToText0.setError(null);
        chaletNumberText0.setError(null);
        chaletFromText1.setError(null);
        chaletToText1.setError(null);
        chaletNumberText1.setError(null);
        chaletFromText2.setError(null);
        chaletToText2.setError(null);
        chaletNumberText2.setError(null);
        chaletFromText3.setError(null);
        chaletToText3.setError(null);
        chaletNumberText3.setError(null);
        chaletFromText4.setError(null);
        chaletToText4.setError(null);
        chaletNumberText4.setError(null);
        chaletFromText5.setError(null);
        chaletToText5.setError(null);
        chaletNumberText5.setError(null);
        chaletFromText6.setError(null);
        chaletToText6.setError(null);
        chaletNumberText6.setError(null);
        chaletFromText7.setError(null);
        chaletToText7.setError(null);
        chaletNumberText7.setError(null);

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

        String chaletFromText0s = chaletFromText0.getText().toString();
        String chaletToText0s = chaletToText0.getText().toString();
        String chaletNumberText0s = chaletNumberText0.getText().toString();
        String chaletFromText1s = chaletFromText1.getText().toString();
        String chaletToText1s = chaletToText1.getText().toString();
        String chaletNumberText1s = chaletNumberText1.getText().toString();
        String chaletFromText2s = chaletFromText2.getText().toString();
        String chaletToText2s = chaletToText2.getText().toString();
        String chaletNumberText2s = chaletNumberText2.getText().toString();
        String chaletFromText3s = chaletFromText3.getText().toString();
        String chaletToText3s = chaletToText3.getText().toString();
        String chaletNumberText3s = chaletNumberText3.getText().toString();
        String chaletFromText4s = chaletFromText4.getText().toString();
        String chaletToText4s = chaletToText4.getText().toString();
        String chaletNumberText4s = chaletNumberText4.getText().toString();
        String chaletFromText5s = chaletFromText5.getText().toString();
        String chaletToText5s = chaletToText5.getText().toString();
        String chaletNumberText5s = chaletNumberText5.getText().toString();
        String chaletFromText6s = chaletFromText6.getText().toString();
        String chaletToText6s = chaletToText6.getText().toString();
        String chaletNumberText6s = chaletNumberText6.getText().toString();
        String chaletFromText7s = chaletFromText7.getText().toString();
        String chaletToText7s = chaletToText7.getText().toString();
        String chaletNumberText7s = chaletNumberText7.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(roomFromText0s)) {
            roomFromText0.setError(getString(R.string.error_field_required));
            focusView = roomFromText0;
            cancel = true;
        } else if (isDateInvalid(roomFromText0s)) {
            roomFromText0.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText0;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText0s)) {
            roomToText0.setError(getString(R.string.error_field_required));
            focusView = roomToText0;
            cancel = true;
        } else if (isDateInvalid(roomToText0s)) {
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
        } else if (isDateInvalid(roomFromText1s)) {
            roomFromText1.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText1;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText1s)) {
            roomToText1.setError(getString(R.string.error_field_required));
            focusView = roomToText1;
            cancel = true;
        } else if (isDateInvalid(roomToText1s)) {
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
        } else if (isDateInvalid(roomFromText2s)) {
            roomFromText2.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText2;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText2s)) {
            roomToText2.setError(getString(R.string.error_field_required));
            focusView = roomToText2;
            cancel = true;
        } else if (isDateInvalid(roomToText2s)) {
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
        } else if (isDateInvalid(roomFromText3s)) {
            roomFromText3.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText3;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText3s)) {
            roomToText3.setError(getString(R.string.error_field_required));
            focusView = roomToText3;
            cancel = true;
        } else if (isDateInvalid(roomToText3s)) {
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
        } else if (isDateInvalid(roomFromText4s)) {
            roomFromText4.setError(getString(R.string.error_field_invalid));
            focusView = roomFromText4;
            cancel = true;
        }

        if (TextUtils.isEmpty(roomToText4s)) {
            roomToText4.setError(getString(R.string.error_field_required));
            focusView = roomToText4;
            cancel = true;
        } else if (isDateInvalid(roomToText4s)) {
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

        if (TextUtils.isEmpty(chaletFromText0s)) {
            chaletFromText0.setError(getString(R.string.error_field_required));
            focusView = chaletFromText0;
            cancel = true;
        } else if (isDateInvalid(chaletFromText0s)) {
            chaletFromText0.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText0;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText0s)) {
            chaletToText0.setError(getString(R.string.error_field_required));
            focusView = chaletToText0;
            cancel = true;
        } else if (isDateInvalid(chaletToText0s)) {
            chaletToText0.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText0;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText0s)) {
            chaletNumberText0.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText0;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText0s)) {
            chaletNumberText0.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText0;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletFromText1s)) {
            chaletFromText1.setError(getString(R.string.error_field_required));
            focusView = chaletFromText1;
            cancel = true;
        } else if (isDateInvalid(chaletFromText1s)) {
            chaletFromText1.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText1;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText1s)) {
            chaletToText1.setError(getString(R.string.error_field_required));
            focusView = chaletToText1;
            cancel = true;
        } else if (isDateInvalid(chaletToText1s)) {
            chaletToText1.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText1;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText1s)) {
            chaletNumberText1.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText1;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText1s)) {
            chaletNumberText1.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText1;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletFromText2s)) {
            chaletFromText2.setError(getString(R.string.error_field_required));
            focusView = chaletFromText2;
            cancel = true;
        } else if (isDateInvalid(chaletFromText2s)) {
            chaletFromText2.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText2;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText2s)) {
            chaletToText2.setError(getString(R.string.error_field_required));
            focusView = chaletToText2;
            cancel = true;
        } else if (isDateInvalid(chaletToText2s)) {
            chaletToText2.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText2;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText2s)) {
            chaletNumberText2.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText2;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText2s)) {
            chaletNumberText2.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText2;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletFromText3s)) {
            chaletFromText3.setError(getString(R.string.error_field_required));
            focusView = chaletFromText3;
            cancel = true;
        } else if (isDateInvalid(chaletFromText3s)) {
            chaletFromText3.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText3;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText3s)) {
            chaletToText3.setError(getString(R.string.error_field_required));
            focusView = chaletToText3;
            cancel = true;
        } else if (isDateInvalid(chaletToText3s)) {
            chaletToText3.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText3;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText3s)) {
            chaletNumberText3.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText3;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText3s)) {
            chaletNumberText3.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText3;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletFromText4s)) {
            chaletFromText4.setError(getString(R.string.error_field_required));
            focusView = chaletFromText4;
            cancel = true;
        } else if (isDateInvalid(chaletFromText4s)) {
            chaletFromText4.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText4;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText4s)) {
            chaletToText4.setError(getString(R.string.error_field_required));
            focusView = chaletToText4;
            cancel = true;
        } else if (isDateInvalid(chaletToText4s)) {
            chaletToText4.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText4;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText4s)) {
            chaletNumberText4.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText4;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText4s)) {
            chaletNumberText4.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText4;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletFromText5s)) {
            chaletFromText5.setError(getString(R.string.error_field_required));
            focusView = chaletFromText5;
            cancel = true;
        } else if (isDateInvalid(chaletFromText5s)) {
            chaletFromText5.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText5;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText5s)) {
            chaletToText5.setError(getString(R.string.error_field_required));
            focusView = chaletToText5;
            cancel = true;
        } else if (isDateInvalid(chaletToText5s)) {
            chaletToText5.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText5;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText5s)) {
            chaletNumberText5.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText5;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText5s)) {
            chaletNumberText5.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText5;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletFromText6s)) {
            chaletFromText6.setError(getString(R.string.error_field_required));
            focusView = chaletFromText6;
            cancel = true;
        } else if (isDateInvalid(chaletFromText6s)) {
            chaletFromText6.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText6;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText6s)) {
            chaletToText6.setError(getString(R.string.error_field_required));
            focusView = chaletToText6;
            cancel = true;
        } else if (isDateInvalid(chaletToText6s)) {
            chaletToText6.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText6;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText6s)) {
            chaletNumberText6.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText6;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText6s)) {
            chaletNumberText6.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText6;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletFromText7s)) {
            chaletFromText7.setError(getString(R.string.error_field_required));
            focusView = chaletFromText7;
            cancel = true;
        } else if (isDateInvalid(chaletFromText7s)) {
            chaletFromText7.setError(getString(R.string.error_field_invalid));
            focusView = chaletFromText7;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletToText7s)) {
            chaletToText7.setError(getString(R.string.error_field_required));
            focusView = chaletToText7;
            cancel = true;
        } else if (isDateInvalid(chaletToText7s)) {
            chaletToText7.setError(getString(R.string.error_field_invalid));
            focusView = chaletToText7;
            cancel = true;
        }

        if (TextUtils.isEmpty(chaletNumberText7s)) {
            chaletNumberText7.setError(getString(R.string.error_field_required));
            focusView = chaletNumberText7;
            cancel = true;
        } else if (isFieldInvalid(chaletNumberText7s)) {
            chaletNumberText7.setError(getString(R.string.error_field_invalid));
            focusView = chaletNumberText7;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        }

        return cancel;
    }

    private boolean isFieldInvalid(@NonNull String field) {
        return field.length() < 2;
    }

    private boolean isDateInvalid(@NonNull String field) {
        if (field.length() < 7) {
            return true;
        }

        final SimpleDateFormat format = new SimpleDateFormat(Constants.ROOM_DATE_FORMAT,
                Locale.getDefault());
        format.setLenient(false);
        try {
            format.parse(field);
            return false;
        } catch (ParseException e) {
            return true;
        }
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

        String chaletFromText0s = chaletFromText0.getText().toString();
        String chaletToText0s = chaletToText0.getText().toString();
        String chaletNumberText0s = chaletNumberText0.getText().toString();
        String chaletFromText1s = chaletFromText1.getText().toString();
        String chaletToText1s = chaletToText1.getText().toString();
        String chaletNumberText1s = chaletNumberText1.getText().toString();
        String chaletFromText2s = chaletFromText2.getText().toString();
        String chaletToText2s = chaletToText2.getText().toString();
        String chaletNumberText2s = chaletNumberText2.getText().toString();
        String chaletFromText3s = chaletFromText3.getText().toString();
        String chaletToText3s = chaletToText3.getText().toString();
        String chaletNumberText3s = chaletNumberText3.getText().toString();
        String chaletFromText4s = chaletFromText4.getText().toString();
        String chaletToText4s = chaletToText4.getText().toString();
        String chaletNumberText4s = chaletNumberText4.getText().toString();
        String chaletFromText5s = chaletFromText5.getText().toString();
        String chaletToText5s = chaletToText5.getText().toString();
        String chaletNumberText5s = chaletNumberText5.getText().toString();
        String chaletFromText6s = chaletFromText6.getText().toString();
        String chaletToText6s = chaletToText6.getText().toString();
        String chaletNumberText6s = chaletNumberText6.getText().toString();
        String chaletFromText7s = chaletFromText7.getText().toString();
        String chaletToText7s = chaletToText7.getText().toString();
        String chaletNumberText7s = chaletNumberText7.getText().toString();

        RoomEntity roomEntity0 = new RoomEntity(0, roomNumberText0s, roomFromText0s, roomToText0s);
        RoomEntity roomEntity1 = new RoomEntity(1, roomNumberText1s, roomFromText1s, roomToText1s);
        RoomEntity roomEntity2 = new RoomEntity(2, roomNumberText2s, roomFromText2s, roomToText2s);
        RoomEntity roomEntity3 = new RoomEntity(3, roomNumberText3s, roomFromText3s, roomToText3s);
        RoomEntity roomEntity4 = new RoomEntity(4, roomNumberText4s, roomFromText4s, roomToText4s);

        schoolViewModel.deleteAllRooms();
        schoolViewModel.insertRoom(roomEntity0);
        schoolViewModel.insertRoom(roomEntity1);
        schoolViewModel.insertRoom(roomEntity2);
        schoolViewModel.insertRoom(roomEntity3);
        schoolViewModel.insertRoom(roomEntity4);

        RoomEntity chaletEntity0 = new RoomEntity(0, chaletNumberText0s, chaletFromText0s, chaletToText0s);
        RoomEntity chaletEntity1 = new RoomEntity(1, chaletNumberText1s, chaletFromText1s, chaletToText1s);
        RoomEntity chaletEntity2 = new RoomEntity(2, chaletNumberText2s, chaletFromText2s, chaletToText2s);
        RoomEntity chaletEntity3 = new RoomEntity(3, chaletNumberText3s, chaletFromText3s, chaletToText3s);
        RoomEntity chaletEntity4 = new RoomEntity(4, chaletNumberText4s, chaletFromText4s, chaletToText4s);
        RoomEntity chaletEntity5 = new RoomEntity(5, chaletNumberText5s, chaletFromText5s, chaletToText5s);
        RoomEntity chaletEntity6 = new RoomEntity(6, chaletNumberText6s, chaletFromText6s, chaletToText6s);
        RoomEntity chaletEntity7 = new RoomEntity(7, chaletNumberText7s, chaletFromText7s, chaletToText7s);

        schoolViewModel.deleteAllChalets();
        schoolViewModel.insertChalet(chaletEntity0);
        schoolViewModel.insertChalet(chaletEntity1);
        schoolViewModel.insertChalet(chaletEntity2);
        schoolViewModel.insertChalet(chaletEntity3);
        schoolViewModel.insertChalet(chaletEntity4);
        schoolViewModel.insertChalet(chaletEntity5);
        schoolViewModel.insertChalet(chaletEntity6);
        schoolViewModel.insertChalet(chaletEntity7);

        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
