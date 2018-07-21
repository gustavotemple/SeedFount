package com.fount.seed;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.wrappers.KidWrapper;
import com.vicmikhailau.maskededittext.MaskedEditText;

/**
 * A register screen
 */
public class RegisterActivity extends AppCompatActivity {

    private KidWrapper kidWrapper;
    private String parent;

    private TextInputEditText mKidName;
    private RadioGroup mKidGender;
    private RadioButton mBoy;
    private RadioButton mGirl;
    private MaskedEditText mBirthDate;
    private TextInputEditText mClassRoom;
    private TextInputEditText mDadName;
    private TextInputEditText mMomName;
    private TextInputEditText mDadEmail;
    private TextInputEditText mMomEmail;
    private TextInputEditText mCityName;
    private TextInputEditText mKidAddress;
    private MaskedEditText mCellPhone;
    private Switch mChurch;
    private TextInputEditText mChurchName;
    private Switch mWillReturn;
    private Switch mCanLeave;
    private Button button;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        // Set up the register form.
        mKidName = findViewById(R.id.kid_name);
        mKidGender = findViewById(R.id.kid_gender);
        mBoy = findViewById(R.id.boy);
        mGirl = findViewById(R.id.girl);
        mBirthDate = findViewById(R.id.birth_date);
        mClassRoom = findViewById(R.id.class_room);
        mDadName = findViewById(R.id.dad_name);
        mMomName = findViewById(R.id.mom_name);
        mDadEmail = findViewById(R.id.dad_email);
        mMomEmail = findViewById(R.id.mom_email);
        mCityName = findViewById(R.id.city_name);
        mKidAddress = findViewById(R.id.kid_address);
        mCellPhone = findViewById(R.id.cell_phone);
        mChurch = findViewById(R.id.church);
        mChurchName = findViewById(R.id.church_name);
        mWillReturn = findViewById(R.id.will_return);
        mCanLeave = findViewById(R.id.can_leave);

        final Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), Constants.FONT);
        setTypeface(typeface);

        button = findViewById(R.id.form_button);
        button.setTypeface(typeface);

        fab = findViewById(R.id.delete_kid);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClassName(getApplicationContext(), parent);
                intent.putExtra(Constants.EXTRA_KEY_KID, kidWrapper);
                intent.putExtra(Constants.EXTRA_KEY_OPERATION, Constants.DELETE);
                startActivity(intent);
            }
        });

        setUI(savedInstanceState);
    }

    private void setUI(Bundle savedInstanceState) {
        final Bundle state = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        kidWrapper = state != null ? (KidWrapper) state.getParcelable(Constants.EXTRA_KEY_KID) : null;
        parent = getIntent().getStringExtra(Constants.EXTRA_KEY_PARENT);

        if (kidWrapper == null) {
            setTitle(R.string.title_register);
            fab.setVisibility(View.GONE);
            button.setText(R.string.action_register);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    KidWrapper kid = attemptRegister();

                    if (kid == null) {
                        return;
                    }

                    Intent intent = new Intent();
                    intent.setClassName(getApplicationContext(), parent);
                    intent.putExtra(Constants.EXTRA_KEY_KID, kid);
                    intent.putExtra(Constants.EXTRA_KEY_OPERATION, Constants.INSERT);
                    startActivity(intent);
                }
            });
        } else {
            loadKid();
            setTitle(R.string.title_update);
            fab.setVisibility(View.VISIBLE);
            button.setText(R.string.action_update);
            button.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    KidWrapper kid = attemptRegister();

                    if (kid == null) {
                        return;
                    }

                    kid.setUid(kidWrapper.getUid());

                    Intent intent = new Intent();
                    intent.setClassName(getApplicationContext(), parent);
                    intent.putExtra(Constants.EXTRA_KEY_KID, kid);
                    intent.putExtra(Constants.EXTRA_KEY_OPERATION, Constants.UPDATE);
                    startActivity(intent);
                }
            });
        }
    }

    private void loadKid() {
        mKidName.setText(kidWrapper.getKidName());
        mBirthDate.setText(kidWrapper.getBirthDate());
        mClassRoom.setText(kidWrapper.getClassRoom());
        mDadName.setText(kidWrapper.getDadName());
        mMomName.setText(kidWrapper.getMomName());
        mDadEmail.setText(kidWrapper.getDadEmail());
        mMomEmail.setText(kidWrapper.getMomEmail());
        mCityName.setText(kidWrapper.getCityName());
        mKidAddress.setText(kidWrapper.getKidAddress());
        mCellPhone.setText(kidWrapper.getCellPhone());

        if (kidWrapper.getChurchName() != null) {
            mChurchName.setText(kidWrapper.getChurchName());
        }

        if (kidWrapper.getGender() == KidWrapper.BOY) {
            mBoy.setChecked(true);
        } else if (kidWrapper.getGender() == KidWrapper.GIRL) {
            mGirl.setChecked(true);
        }

        mChurch.setChecked(kidWrapper.isChurch());
        mWillReturn.setChecked(kidWrapper.isWillReturn());
        mCanLeave.setChecked(kidWrapper.isCanLeave());
    }

    private void setTypeface(final Typeface type) {
        TextInputLayout mKidNameLayout = findViewById(R.id.kid_name_layout);
        mKidNameLayout.setTypeface(type);
        mKidName.setTypeface(type);
        TextInputLayout mBirthDateLayout = findViewById(R.id.birth_date_layout);
        mBirthDateLayout.setTypeface(type);
        mBirthDate.setTypeface(type);
        TextInputLayout mClassRoomLayout = findViewById(R.id.class_room_layout);
        mClassRoomLayout.setTypeface(type);
        mClassRoom.setTypeface(type);
        TextInputLayout mDadNameLayout = findViewById(R.id.dad_name_layout);
        mDadNameLayout.setTypeface(type);
        mDadName.setTypeface(type);
        TextInputLayout mMomNameLayout = findViewById(R.id.mom_name_layout);
        mMomNameLayout.setTypeface(type);
        mMomName.setTypeface(type);
        TextInputLayout mDadEmailLayout = findViewById(R.id.dad_email_layout);
        mDadEmailLayout.setTypeface(type);
        mDadEmail.setTypeface(type);
        TextInputLayout mMomEmailLayout = findViewById(R.id.mom_email_layout);
        mMomEmailLayout.setTypeface(type);
        mMomEmail.setTypeface(type);
        TextInputLayout mCityNameLayout = findViewById(R.id.city_name_layout);
        mCityNameLayout.setTypeface(type);
        mCityName.setTypeface(type);
        TextInputLayout mKidAddressLayout = findViewById(R.id.kid_address_layout);
        mKidAddressLayout.setTypeface(type);
        mKidAddress.setTypeface(type);
        TextInputLayout mCellPhoneLayout = findViewById(R.id.cell_phone_layout);
        mCellPhoneLayout.setTypeface(type);
        mCellPhone.setTypeface(type);
        TextInputLayout mChurchNameLayout = findViewById(R.id.church_name_layout);
        mChurchNameLayout.setTypeface(type);
        mChurch.setTypeface(type);

        mBoy.setTypeface(type);
        mGirl.setTypeface(type);
        mChurchName.setTypeface(type);
        mWillReturn.setTypeface(type);
        mCanLeave.setTypeface(type);
    }

    private KidWrapper attemptRegister() {
        // Reset errors.
        mKidName.setError(null);
        mBirthDate.setError(null);
        mClassRoom.setError(null);
        mDadName.setError(null);
        mMomName.setError(null);
        mDadEmail.setError(null);
        mMomEmail.setError(null);
        mCityName.setError(null);
        mKidAddress.setError(null);
        mCellPhone.setError(null);

        // Store values at the time of the register attempt.
        String kidName = mKidName.getText().toString();
        String birthDate = mBirthDate.getText().toString();
        String classRoom = mClassRoom.getText().toString();
        String dadName = mDadName.getText().toString();
        String momName = mMomName.getText().toString();
        String dadEmail = mDadEmail.getText().toString();
        String momEmail = mMomEmail.getText().toString();
        String cityName = mCityName.getText().toString();
        String kidAddress = mKidAddress.getText().toString();
        String cellPhone = mCellPhone.getText().toString();
        String churchName = mChurchName.getText().toString();
        int kidGender = mKidGender.getCheckedRadioButtonId();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid kidName.
        if (TextUtils.isEmpty(kidName)) {
            mKidName.setError(getString(R.string.error_field_required));
            focusView = mKidName;
            cancel = true;
        } else if (!isFieldValid(kidName)) {
            mKidName.setError(getString(R.string.error_field_invalid));
            focusView = mKidName;
            cancel = true;
        }

        // Check for a valid kid birthDate.
        if (TextUtils.isEmpty(birthDate)) {
            mBirthDate.setError(getString(R.string.error_field_required));
            focusView = mBirthDate;
            cancel = true;
        }

        // Check for a valid kid classRoom.
        if (TextUtils.isEmpty(classRoom)) {
            mClassRoom.setError(getString(R.string.error_field_required));
            focusView = mClassRoom;
            cancel = true;
        } else if (!isFieldValid(classRoom)) {
            mClassRoom.setError(getString(R.string.error_field_invalid));
            focusView = mClassRoom;
            cancel = true;
        }

        // Check for a valid kid dadName.
        if (TextUtils.isEmpty(dadName)) {
            mDadName.setError(getString(R.string.error_field_required));
            focusView = mDadName;
            cancel = true;
        } else if (!isFieldValid(dadName)) {
            mDadName.setError(getString(R.string.error_field_invalid));
            focusView = mDadName;
            cancel = true;
        }

        // Check for a valid kid momName.
        if (TextUtils.isEmpty(momName)) {
            mMomName.setError(getString(R.string.error_field_required));
            focusView = mMomName;
            cancel = true;
        } else if (!isFieldValid(momName)) {
            mMomName.setError(getString(R.string.error_field_invalid));
            focusView = mMomName;
            cancel = true;
        }

        // Check for a valid dadEmail.
        if (TextUtils.isEmpty(dadEmail)) {
            mDadEmail.setError(getString(R.string.error_field_required));
            focusView = mDadEmail;
            cancel = true;
        } else if (!isEmailValid(dadEmail)) {
            mDadEmail.setError(getString(R.string.error_field_invalid));
            focusView = mDadEmail;
            cancel = true;
        }

        // Check for a valid momEmail.
        if (TextUtils.isEmpty(momEmail)) {
            mMomEmail.setError(getString(R.string.error_field_required));
            focusView = mMomEmail;
            cancel = true;
        } else if (!isEmailValid(momEmail)) {
            mMomEmail.setError(getString(R.string.error_field_invalid));
            focusView = mMomEmail;
            cancel = true;
        }

        // Check for a valid cityName.
        if (TextUtils.isEmpty(cityName)) {
            mCityName.setError(getString(R.string.error_field_required));
            focusView = mCityName;
            cancel = true;
        } else if (!isFieldValid(cityName)) {
            mCityName.setError(getString(R.string.error_field_invalid));
            focusView = mCityName;
            cancel = true;
        }

        // Check for a valid kidAddress.
        if (TextUtils.isEmpty(kidAddress)) {
            mKidAddress.setError(getString(R.string.error_field_required));
            focusView = mKidAddress;
            cancel = true;
        } else if (!isFieldValid(kidAddress)) {
            mKidAddress.setError(getString(R.string.error_field_invalid));
            focusView = mKidAddress;
            cancel = true;
        }

        // Check for a valid cellPhone.
        if (TextUtils.isEmpty(cellPhone)) {
            mCellPhone.setError(getString(R.string.error_field_required));
            focusView = mCellPhone;
            cancel = true;
        } else if (!isFieldValid(cellPhone)) {
            mCellPhone.setError(getString(R.string.error_field_invalid));
            focusView = mCellPhone;
            cancel = true;
        }

        long gender = 0;
        if (kidGender <= 0) {
            mBoy.setError(getString(R.string.error_field_invalid));
            mGirl.setError(getString(R.string.error_field_invalid));
            focusView = mKidGender;
            cancel = true;
        } else if (kidGender == R.id.boy) {
            gender = KidWrapper.BOY;
        } else if (kidGender == R.id.girl) {
            gender = KidWrapper.GIRL;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
            return null;
        }

        return new KidWrapper(kidName, dadName,
                momName, dadEmail,
                momEmail, cityName,
                kidAddress, classRoom,
                churchName, cellPhone,
                gender, mCanLeave.isChecked(),
                mChurch.isChecked(), mWillReturn.isChecked(),
                birthDate);
    }

    private boolean isFieldValid(String name) {
        return name.length() > 2;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

}
