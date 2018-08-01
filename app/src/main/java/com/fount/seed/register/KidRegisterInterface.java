package com.fount.seed.register;

import android.graphics.Typeface;

import com.fount.seed.wrappers.KidWrapper;

interface KidRegisterInterface {
    void setTypeface(final Typeface type);
    KidWrapper submitAttempt();
    boolean isFieldInvalid(String field);
    boolean isEmailInvalid(String email);
}
