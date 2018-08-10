package com.fount.seed;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.fount.seed.register.AddKidActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class InstrumentedTest {

    private String kidName;
    private AddKidActivity addKidActivity;

    @Rule
    public ActivityTestRule<AddKidActivity> mActivityRule =
            new ActivityTestRule<>(AddKidActivity.class,
                    true, true);

    @Before
    public void setUp() {
        kidName = "John";

        addKidActivity = mActivityRule.getActivity();
    }

    @Test
    public void submitAttemptFail1() {
        onView(withId(R.id.kid_name))
                .perform(clearText(), typeText(kidName), closeSoftKeyboard())
                .check(matches(withText(kidName)));

        addKidActivity.runOnUiThread(() -> assertNull(addKidActivity.submitAttempt()));
    }

    @Test
    public void submitAttemptFail2() {
        onView(withId(R.id.boy))
                .perform(click())
                .check(matches(isChecked()));

        onView(withId(R.id.girl))
                .check(matches(isNotChecked()));

        addKidActivity.runOnUiThread(() -> assertNull(addKidActivity.submitAttempt()));
    }

    @Test
    public void submitAttemptOK() {
        onView(withId(R.id.kid_name))
                .perform(clearText(), typeText(kidName));

        onView(withId(R.id.birth_date))
                .perform(clearText(), typeText("01/01/2001"));

        onView(withId(R.id.class_room))
                .perform(clearText(), typeText("Chalet 3 AM"), closeSoftKeyboard());

        onView(withId(R.id.girl))
                .perform(click());

        addKidActivity.runOnUiThread(() -> assertNotNull(addKidActivity.submitAttempt()));
    }
}
