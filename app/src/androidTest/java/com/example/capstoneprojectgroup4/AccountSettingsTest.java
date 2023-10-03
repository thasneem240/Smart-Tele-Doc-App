package com.example.capstoneprojectgroup4;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.front_end.AccountSettings;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;

@RunWith(AndroidJUnit4ClassRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AccountSettingsTest {

    @Rule
    public ActivityScenarioRule<MainActivity2> activityScenarioRule = new ActivityScenarioRule<>(MainActivity2.class);

    @Test
    public void A_testAccountSettingsUIElementsandUpdate() {
        // Launch the AccountSettings fragment
        activityScenarioRule.getScenario().onActivity(activity -> {
            AccountSettings accountSettingsFragment = new AccountSettings();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, accountSettingsFragment)
                    .commit();
        });

        // Wait for a short period to allow any animations to complete
        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the EditText fields become enabled for editing
        Espresso.onView(ViewMatchers.withId(R.id.EditText_FirstName))
                .check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Type something into an EditText and close the keyboard
        Espresso.onView(ViewMatchers.withId(R.id.EditText_FirstName))
                .perform(typeText("John"), closeSoftKeyboard());

        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Scroll within the ScrollView to bring the elements into view
        Espresso.onView(ViewMatchers.withId(R.id.scrollView))
                .perform(ViewActions.swipeUp()); // You can use swipeUp() to scroll down
        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform a click on the edit button
        Espresso.onView(ViewMatchers.withId(R.id.Button_Update)).perform(ViewActions.click());
        Espresso.onView(ViewMatchers.withText("Successfully updated"));

    }


    @Test
    public void B_testAccountSettingsLogout() {

        activityScenarioRule.getScenario().onActivity(activity -> {
            AccountSettings accountSettingsFragment = new AccountSettings();
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, accountSettingsFragment)
                    .commit();
        });

        // Wait for a short period to allow any animations to complete
        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        // Scroll within the ScrollView to bring the Logout button into view
        Espresso.onView(ViewMatchers.withId(R.id.scrollView))
                .perform(ViewActions.swipeUp()); // You can use swipeUp() to scroll down

        // Wait for a short period to allow any animations to complete
        try {
            Thread.sleep(2000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform a click on the Logout button
        Espresso.onView(ViewMatchers.withId(R.id.Button_Logout)).perform(ViewActions.click());

        // Wait for a short period to allow any animations to complete
        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the user is logged out and the MainActivity is displayed
        Espresso.onView(ViewMatchers.withId(R.id.Patient_login_button))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));


    }





}
