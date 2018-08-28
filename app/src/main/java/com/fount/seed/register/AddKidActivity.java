package com.fount.seed.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.fount.seed.R;
import com.fount.seed.utils.Constants;
import com.fount.seed.wrappers.KidWrapper;

public final class AddKidActivity extends KidRegisterActivity {

    @Override
    public void setUI(Bundle savedInstanceState) {
        setTitle(R.string.title_register);
        button.setText(R.string.action_register);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        KidWrapper kid = submitAttempt();

        if (kid == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(Constants.EXTRA_KEY_KID, kid);
        setResult(Constants.INSERT, intent);
        finish();
    }
}
