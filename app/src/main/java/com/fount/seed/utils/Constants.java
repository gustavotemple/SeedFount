package com.fount.seed.utils;

import com.fount.seed.BuildConfig;

public final class Constants {

    private Constants() {
    }

    public static final String EXTRA_KEY_KID = BuildConfig.APPLICATION_ID + ".kid";

    public static final String EXTRA_KEY_DATE = BuildConfig.APPLICATION_ID + ".date";

    public static final String EXTRA_KEY_OPERATION = BuildConfig.APPLICATION_ID + ".op";

    public static final String CLASS_ROOM = "classRoom";

    public static final String P = "P";

    public static final String L = "L";

    public static final String V = "V";

    public static final String HOUR = "HOUR";

    public static final String AM = "AM";

    public static final String PM = "PM";

    public static final int NO_OP = 0;

    public static final int INSERT = 1;

    public static final int UPDATE = 2;

    public static final int DELETE = 3;

    public static final String FONT = "font.otf";

}
