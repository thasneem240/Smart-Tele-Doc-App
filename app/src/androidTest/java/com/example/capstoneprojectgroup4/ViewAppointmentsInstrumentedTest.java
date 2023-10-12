package com.example.capstoneprojectgroup4;

import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ViewAppointmentsInstrumentedTest {

    private FragmentManager fragmentManager;


    // Example: Mocking PatientObject for tests
    @Before
    public void setUp() {
        // Mocking PatientObject for tests
        PatientObject mockPatient = new PatientObject();
        mockPatient.setUid("mockPatientUid");
        mockPatient.setFirstName("MockFirstName");

        // Set the mockPatient in MainActivity for the test
        MainActivity.setPatientObject(mockPatient);

        // Initialize fragment manager
        fragmentManager = null; // Initialize to null
    }

    @After
    public void tearDown() {
        // Clean up resources if needed
    }

    @Test
    public void testViewAppointmentsUI() {
        // Launch the MainActivity and navigate to the ViewAppointments fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new ViewAppointments()).commit();
        });

        try {
            // Sleep for 2 seconds (adjust the sleep duration as needed)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the UI components are displayed
        onView(withId(R.id.patientNameViewApp)).check(matches(isDisplayed()));
        onView(withId(R.id.recyclerAppView)).check(matches(isDisplayed()));
        onView(withId(R.id.backButtonViewApp)).check(matches(isDisplayed()));
        onView(withId(R.id.viewhistory)).check(matches(isDisplayed()));


        onView(withId(R.id.patientNameViewApp)).check(matches(withText("MockFirstName")));
    }

    @Test
    public void testClickBackButton() {
        // Launch the MainActivity and navigate to the ViewAppointments fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new ViewAppointments()).commit();
        });

        try {
            // Sleep for 2 seconds (adjust the sleep duration as needed)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform click on the back button
        onView(withId(R.id.backButtonViewApp)).perform(click());

        // Check if the MainMenu fragment is displayed after clicking the back button
        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));
    }

    @Test
    public void testClickViewHistoryButton() {
        // Launch the MainActivity and navigate to the ViewAppointments fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new ViewAppointments()).commit();
        });
        try {
            // Sleep for 2 seconds (adjust the sleep duration as needed)
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform click on the "View History" button
        onView(withId(R.id.viewhistory)).perform(click());

        // Check if the AppointmentHistory fragment is displayed after clicking the "View History" button
        onView(withId(R.id.fragmentContainerView)).check(matches(isDisplayed()));
    }




}
