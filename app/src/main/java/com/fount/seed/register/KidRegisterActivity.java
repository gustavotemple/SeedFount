package com.fount.seed.register;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.fount.seed.R;
import com.fount.seed.utils.Constants;
import com.fount.seed.utils.CustomExceptionHandler;
import com.fount.seed.wrappers.KidWrapper;
import com.vicmikhailau.maskededittext.MaskedEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A register screen
 */
abstract class KidRegisterActivity
        extends AppCompatActivity
        implements KidRegisterInterface, View.OnClickListener {

    public KidWrapper kidWrapper;

    @BindView(R.id.kid_name)
    public TextInputEditText mKidName;
    @BindView(R.id.kid_gender)
    public RadioGroup mKidGender;
    @BindView(R.id.boy)
    public RadioButton mBoy;
    @BindView(R.id.girl)
    public RadioButton mGirl;
    @BindView(R.id.birth_date)
    public MaskedEditText mBirthDate;
    @BindView(R.id.class_room)
    public TextInputEditText mClassRoom;
    @BindView(R.id.sponsor_name)
    public TextInputEditText mSponsorName;
    @BindView(R.id.sponsor_email)
    public TextInputEditText mSponsorEmail;
    @BindView(R.id.city)
    public TextInputEditText mCity;
    @BindView(R.id.address)
    public TextInputEditText mAddress;
    @BindView(R.id.cell_phone)
    public MaskedEditText mCellPhone;
    @BindView(R.id.church)
    public Switch mChurch;
    @BindView(R.id.church_name)
    public TextInputEditText mChurchName;
    @BindView(R.id.allergy)
    public TextInputEditText mAllergy;
    @BindView(R.id.will_return)
    public Switch mWillReturn;
    @BindView(R.id.can_leave)
    public Switch mCanLeave;
    @BindView(R.id.form_button)
    public Button button;
    @BindView(R.id.delete_kid)
    public FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        if (!(Thread.getDefaultUncaughtExceptionHandler() instanceof CustomExceptionHandler)) {
            Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(this));
        }

        final Typeface typeface = Typeface.createFromAsset(getApplicationContext().getAssets(), Constants.FONT);
        setTypeface(typeface);

        button.setTypeface(typeface);

        setUI(savedInstanceState);
    }

    @Override
    @OnClick(R.id.delete_kid)
    public void deleteKid() {
        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_KEY_KID, kidWrapper);
        setResult(Constants.DELETE, intent);
        finish();
    }

    abstract void setUI(Bundle savedInstanceState);

    @Override
    public void setTypeface(final Typeface type) {
        TextInputLayout mKidNameLayout = findViewById(R.id.kid_name_layout);
        mKidNameLayout.setTypeface(type);
        mKidName.setTypeface(type);
        TextInputLayout mBirthDateLayout = findViewById(R.id.birth_date_layout);
        mBirthDateLayout.setTypeface(type);
        mBirthDate.setTypeface(type);
        TextInputLayout mClassRoomLayout = findViewById(R.id.class_room_layout);
        mClassRoomLayout.setTypeface(type);
        mClassRoom.setTypeface(type);
        TextInputLayout mSponsorNameLayout = findViewById(R.id.sponsor_name_layout);
        mSponsorNameLayout.setTypeface(type);
        mSponsorName.setTypeface(type);
        TextInputLayout mSponsorEmailLayout = findViewById(R.id.sponsor_email_layout);
        mSponsorEmailLayout.setTypeface(type);
        mSponsorEmail.setTypeface(type);
        TextInputLayout mCityNameLayout = findViewById(R.id.city_layout);
        mCityNameLayout.setTypeface(type);
        mCity.setTypeface(type);
        TextInputLayout mKidAddressLayout = findViewById(R.id.address_layout);
        mKidAddressLayout.setTypeface(type);
        mAddress.setTypeface(type);
        TextInputLayout mCellPhoneLayout = findViewById(R.id.cell_phone_layout);
        mCellPhoneLayout.setTypeface(type);
        mCellPhone.setTypeface(type);
        TextInputLayout mAllergyLayout = findViewById(R.id.allergy_layout);
        mAllergyLayout.setTypeface(type);
        mAllergy.setTypeface(type);
        TextInputLayout mChurchNameLayout = findViewById(R.id.church_name_layout);
        mChurchNameLayout.setTypeface(type);
        mChurchName.setTypeface(type);
        mChurch.setTypeface(type);
        mBoy.setTypeface(type);
        mGirl.setTypeface(type);
        mWillReturn.setTypeface(type);
        mCanLeave.setTypeface(type);
    }

    @Override
    public KidWrapper submitAttempt() {
        // Reset errors.
        mKidName.setError(null);
        mClassRoom.setError(null);
        mSponsorName.setError(null);
        mSponsorEmail.setError(null);
        mCity.setError(null);
        mAddress.setError(null);
        mAllergy.setError(null);
        mChurchName.setError(null);

        // Store values at the time of the register attempt.
        String kidName = mKidName.getText().toString();
        String birthDate = mBirthDate.getText().toString();
        String classRoom = mClassRoom.getText().toString();
        String sponsorName = mSponsorName.getText().toString();
        String sponsorEmail = mSponsorEmail.getText().toString();
        String city = mCity.getText().toString();
        String address = mAddress.getText().toString();
        String cellPhone = mCellPhone.getText().toString();
        String allergy = mAllergy.getText().toString();
        String churchName = mChurchName.getText().toString();
        int kidGender = mKidGender.getCheckedRadioButtonId();
        boolean church = mChurch.isChecked();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid kidName.
        if (TextUtils.isEmpty(kidName)) {
            mKidName.setError(getString(R.string.error_field_required));
            focusView = mKidName;
            cancel = true;
        } else if (isFieldInvalid(kidName)) {
            mKidName.setError(getString(R.string.error_field_invalid));
            focusView = mKidName;
            cancel = true;
        }

        // Check for a valid kid classRoom.
        if (TextUtils.isEmpty(classRoom)) {
            mClassRoom.setError(getString(R.string.error_field_required));
            focusView = mClassRoom;
            cancel = true;
        } else if (isFieldInvalid(classRoom)) {
            mClassRoom.setError(getString(R.string.error_field_invalid));
            focusView = mClassRoom;
            cancel = true;
        }

        // Check for a valid kid birthDate.
        if (TextUtils.isEmpty(birthDate)) {
            mBirthDate.setError(getString(R.string.error_field_required));
            focusView = mBirthDate;
            cancel = true;
        } else if (isFieldInvalid(birthDate)) {
            mBirthDate.setError(getString(R.string.error_field_invalid));
            focusView = mBirthDate;
            cancel = true;
        }

        // Check for a valid sponsorName.
        if (!TextUtils.isEmpty(sponsorName) && isFieldInvalid(sponsorName)) {
            mSponsorName.setError(getString(R.string.error_field_invalid));
            focusView = mSponsorName;
            cancel = true;
        }

        // Check for a valid sponsorEmail.
        if (!TextUtils.isEmpty(sponsorEmail) && isEmailInvalid(sponsorEmail)) {
            mSponsorEmail.setError(getString(R.string.error_field_invalid));
            focusView = mSponsorEmail;
            cancel = true;
        }

        // Check for a valid allergy.
        if (!TextUtils.isEmpty(allergy) && isFieldInvalid(allergy)) {
            mAllergy.setError(getString(R.string.error_field_invalid));
            focusView = mAllergy;
            cancel = true;
        }

        // Check for a valid city.
        if (!TextUtils.isEmpty(city) && isFieldInvalid(city)) {
            mCity.setError(getString(R.string.error_field_invalid));
            focusView = mCity;
            cancel = true;
        }

        // Check for a valid address.
        if (!TextUtils.isEmpty(address) && isFieldInvalid(address)) {
            mAddress.setError(getString(R.string.error_field_invalid));
            focusView = mAddress;
            cancel = true;
        }

        // Check for a valid churchName.
        if ((!TextUtils.isEmpty(churchName) && isFieldInvalid(churchName))
                || (church && (TextUtils.isEmpty(churchName) || isFieldInvalid(churchName)))) {
            mChurchName.setError(getString(R.string.error_field_invalid));
            focusView = mChurchName;
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

        return new KidWrapper(kidName, sponsorName,
                sponsorEmail, city,
                address, classRoom,
                churchName, cellPhone,
                gender, mCanLeave.isChecked(),
                church, mWillReturn.isChecked(),
                birthDate, allergy);
    }

    @Override
    public boolean isFieldInvalid(@NonNull String field) {
        return field.length() < 2;
    }

    @Override
    public boolean isEmailInvalid(@NonNull String email) {
        return !email.contains("@");
    }

}
