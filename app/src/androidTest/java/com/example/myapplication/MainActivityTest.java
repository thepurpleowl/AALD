package com.example.myapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.ComponentNameMatchers.hasShortClassName;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class MainActivityTest {
    private static final String MESSAGE = "Hello World";
    private static final String UTTERANCE = "I am uttering.";
    private static final String PACKAGE_NAME = "com.example.myapplication";

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

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
//        ActivityScenario scenario = activityRule.getScenario();
//        scenario.moveToState(Lifecycle.State.RESUMED);
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
//        assertEquals("com.example.myapplication", appContext.getPackageName());
//    }

    @Test
    public void verifyMessageSenttoSendActivity() {
        ActivityScenario scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.RESUMED);
        // Types message into a EditText element.
        onView(withId(R.id.msg_field))
                .perform(typeText(MESSAGE), closeSoftKeyboard());

        // Clicks button to send the message to another activity through an explicit intent.
        onView(withId(R.id.send_button)).perform(click());

        // Verifies that the SendActivity received an intent with the correct package name and message.
        intended(allOf(
                hasComponent(hasShortClassName(".SendActivity")),
                toPackage(PACKAGE_NAME),
                hasExtra(MainActivity.EXTRA_MESSAGE, MESSAGE)));

        scenario.close();
    }

    @Test
    public void verifyMessageSetInMainActivity() {
        ActivityScenario scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.RESUMED);

        // Types message into a EditText element.
        onView(withId(R.id.msg_field))
                .perform(typeText(MESSAGE), closeSoftKeyboard());

        onView(withId(R.id.send_button)).perform(click());

        //Comes back from SendActivity.
        pressBack();

        onView(withId(R.id.msg_field)).check(matches(withText(R.string.test_msg)));

        scenario.close();
    }

    @Test
    public void clickInput_sendsSpeakIntentAndDisplaysResults() {
        ActivityScenario scenario = activityRule.getScenario();
        scenario.moveToState(Lifecycle.State.RESUMED);

        Intent resultData = new Intent();
        resultData.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

//         RecognizerIntent returns an ArrayList, so array of String; as following won't perform corretly.
//         resultData.putExtra(RecognizerIntent.EXTRA_RESULTS, new String[]{UTTERANCE});

        resultData.putExtra(RecognizerIntent.EXTRA_RESULTS, new ArrayList<String>(Arrays.asList(UTTERANCE)));
        Instrumentation.ActivityResult result =
                new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);

        // stub intent returning recognition results
        intending(hasAction(equalTo(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)))
                .respondWith(result);

        // when clicking on the buttonVoice, thus opening the dialog and starting the speechRecognizer
        onView(withId(R.id.voice_button))
                .perform(click());

        // expect the recognition results to be displayed
        onView(withId(R.id.msg_field)).check(matches(withText(UTTERANCE)));

        scenario.close();
    }
}