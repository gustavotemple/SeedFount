package com.fount.seed.register;

import android.graphics.Typeface;
import android.view.View;

import com.fount.seed.wrappers.KidWrapper;

interface KidRegisterInterface {
    void setTypeface(final Typeface type);
    KidWrapper submitAttempt();
    boolean isFieldInvalid(String field);
    boolean isEmailInvalid(String email);
    boolean isBirthDateInvalid(String email);
    void onClick(View v);
}
