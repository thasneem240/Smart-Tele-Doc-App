package com.example.capstoneprojectgroup4;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.patient_authentication.AccountSettings;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;
import com.example.capstoneprojectgroup4.search_doctors.SearchDocF;

import org.junit.Before;
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

    private FragmentManager fragmentManager;

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


    @Test
    public void A_testAccountSettingsUIElementsandUpdate() {
        // Launch the AccountSettings fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new AccountSettings()).commit();
        });

        // Wait for a short period to allow any animations to complete
        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify that the EditText fields become enabled for editing
        Espresso.onView(ViewMatchers.withId(R.id.EditText_DoctorName))
                .check(ViewAssertions.matches(ViewMatchers.isEnabled()));
        try {
            Thread.sleep(3000); // Adjust the duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Type something into an EditText and close the keyboard
        Espresso.onView(ViewMatchers.withId(R.id.EditText_DoctorName))
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

        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new AccountSettings()).commit();
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
