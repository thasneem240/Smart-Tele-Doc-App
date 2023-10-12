package com.example.capstoneprojectgroup4;

import androidx.fragment.app.FragmentManager;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.patient_authentication.PatientSignUp;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class PatientSignUpInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Launch the fragment for testing
        FragmentManager fragmentManager = activityTestRule.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer_MainActivity, new PatientSignUp())
                .commit();    }

    @Test
    public void testSignUpWithValidInput() {
        // Enter valid email and password

        onView(ViewMatchers.withId(R.id.EditText_EnterEmail))
                .perform(ViewActions.typeText("20688651@student.curtin.edu.au"), ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.EditText_Doctor_Enter_Password))
                .perform(ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.EditText_ReEnterPassword))
                .perform(ViewActions.typeText("123456"), ViewActions.closeSoftKeyboard());

        // Check the checkbox
        onView(withId(R.id.CheckBox_Terms)).perform(click());
        try {
            Thread.sleep(2000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Click the sign-up button
        onView(withId(R.id.sign_up_button)).perform(click());

        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Assert that the email verification fragment is displayed
        onView(withId(R.id.email_verification_button)).check(matches(isDisplayed()));
    }

    @Test
    public void testSignUpWithInvalidInput() {

        // Click the sign-up button without entering any data
        onView(withId(R.id.sign_up_button)).perform(click());
        // Assert that appropriate error messages are displayed
        Espresso.onView(ViewMatchers.withText("Please enter the email"));


    }

}
