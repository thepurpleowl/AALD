package com.example.myapplication;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class SendActivityTest {
    private static final String EMAIL = "johnhoe@.iisc.ac.in";
    private static final String PHONE = "789324";
    private static final String PACKAGE_NAME = "com.example.myapplication";
    public static final String REGEX_PHONE = "^(0|\\+91|91)?[-\\s]?\\d{10}";
    public static final String REGEX_EMAIL = "^([\\w\\.]+)@([\\w]+)((\\.(\\w){2,3})+)$";

    @Rule
    public ActivityScenarioRule<SendActivity> sendActivityRule = new ActivityScenarioRule<>(SendActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

//    @Test
//    public void useAppContext() {
//        ActivityScenario scenario = sendActivityRule.getScenario();
//        scenario.moveToState(Lifecycle.State.RESUMED);
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals(PACKAGE_NAME, appContext.getPackageName());
//    }

    @Test
    public void verifyPhoneContactInSendActivity() {
        ActivityScenario scenario = sendActivityRule.getScenario();
        scenario.moveToState(Lifecycle.State.RESUMED);

        // Types a message into a EditText element.
        if(!isValidMobile(PHONE)) {
            onView(withId(R.id.contact_field))
                    .perform(typeText(PHONE), closeSoftKeyboard());

            onView(withId(R.id.confirm_button)).perform(click());
            onView(withId(R.id.contact_field)).check(matches(withText(R.string.contact_error)));
        }

        scenario.close();
    }

    @Test
    public void verifyMailContactInSendActivity() {
        ActivityScenario scenario = sendActivityRule.getScenario();
        scenario.moveToState(Lifecycle.State.RESUMED);

        // Types a message into a EditText element.
        if(!isValidMail(EMAIL)) {
            onView(withId(R.id.contact_field))
                    .perform(typeText(EMAIL), closeSoftKeyboard());

            onView(withId(R.id.confirm_button)).perform(click());
            onView(withId(R.id.contact_field)).check(matches(withText(R.string.contact_error)));
        }

        scenario.close();
    }

    private boolean isValidMail(String email) {
        return (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && Pattern.matches(REGEX_EMAIL, email));
    }

    private boolean isValidMobile(String phone) {
        return (android.util.Patterns.PHONE.matcher(phone).matches()
                && Pattern.matches(REGEX_PHONE, phone));
    }

}