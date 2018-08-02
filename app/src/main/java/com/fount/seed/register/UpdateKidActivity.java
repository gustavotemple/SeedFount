package com.fount.seed.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fount.seed.R;
import com.fount.seed.utils.Constants;
import com.fount.seed.wrappers.KidWrapper;

public final class UpdateKidActivity extends KidRegisterActivity {

    @Override
    protected void setUI(Bundle savedInstanceState) {
        final Bundle state = savedInstanceState != null ? savedInstanceState : getIntent().getExtras();
        kidWrapper = state != null ? (KidWrapper) state.getParcelable(Constants.EXTRA_KEY_KID) : null;
        parent = getIntent().getStringExtra(Constants.EXTRA_KEY_PARENT);

        loadKid();
        setTitle(R.string.title_update);
        fab.setVisibility(View.VISIBLE);
        button.setText(R.string.action_update);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        KidWrapper kid = submitAttempt();

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

    private void loadKid() {
        mKidName.setText(kidWrapper.getKidName());
        mBirthDate.setText(kidWrapper.getBirthDate());
        mClassRoom.setText(kidWrapper.getClassRoom());
        mSponsorName.setText(kidWrapper.getSponsorName());
        mSponsorEmail.setText(kidWrapper.getSponsorEmail());
        mCity.setText(kidWrapper.getCityName());
        mAddress.setText(kidWrapper.getKidAddress());
        mAllergy.setText(kidWrapper.getAllergy());
        mCellPhone.setText(kidWrapper.getCellPhone());
        mChurchName.setText(kidWrapper.getChurchName());

        if (kidWrapper.getGender() == KidWrapper.BOY) {
            mBoy.setChecked(true);
        } else if (kidWrapper.getGender() == KidWrapper.GIRL) {
            mGirl.setChecked(true);
        }

        mChurch.setChecked(kidWrapper.isChurch());
        mWillReturn.setChecked(kidWrapper.isWillReturn());
        mCanLeave.setChecked(kidWrapper.isCanLeave());
    }
}
