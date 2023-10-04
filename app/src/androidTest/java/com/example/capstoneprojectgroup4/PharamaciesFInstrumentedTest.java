package com.example.capstoneprojectgroup4;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PharamaciesFInstrumentedTest {

    private Context context;
    private FragmentManager fragmentManager;

    @Before
    public void setUp() {
        // Initialize context and fragment manager
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        fragmentManager = null; // Initialize to null
    }

    @After
    public void tearDown() {
        // Clean up resources if needed
    }

    @Test
    public void testSearchPharmacyByName() {
        // Launch the MainActivity and navigate to the PharmaciesF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new PharmaciesF()).commit();
        });

        // Replace with your specific test case steps
        Espresso.onView(ViewMatchers.withId(R.id.searchPharmName))
                .perform(ViewActions.typeText("Union")); // Type the pharmacy name
        Espresso.onView(ViewMatchers.withId(R.id.pharmsearchButton)).check(matches(isDisplayed()));

        // Perform click
        Espresso.onView(ViewMatchers.withId(R.id.pharmsearchButton))
                .perform(ViewActions.closeSoftKeyboard(), ViewActions.click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(1000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Espresso.onView(ViewMatchers.withId(R.id.pharmsearchButton))
                                .perform(ViewActions.click());
                    }
                });

    }

    @Test
    public void testSearchPharmacyByLocation() {

        // Launch the MainActivity and navigate to the PharmaciesF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new PharmaciesF()).commit();
        });
        Espresso.onView(ViewMatchers.withId(R.id.searchPharmLoc))
                .perform(ViewActions.typeText("Kandy")); // Type the pharmacy location

        // Check visibility
        Espresso.onView(ViewMatchers.withId(R.id.pharmsearchButton)).check(matches(isDisplayed()));

        // Perform click
        Espresso.onView(ViewMatchers.withId(R.id.pharmsearchButton))
                .perform(ViewActions.closeSoftKeyboard(), ViewActions.click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(1000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Espresso.onView(ViewMatchers.withId(R.id.pharmsearchButton))
                                .perform(ViewActions.click());
                    }
                });

    }

}
