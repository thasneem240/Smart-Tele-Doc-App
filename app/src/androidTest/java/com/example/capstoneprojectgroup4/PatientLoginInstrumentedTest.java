package com.example.capstoneprojectgroup4;

import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.front_end.PatientLogin;
import com.example.capstoneprojectgroup4.home.MainActivity;
import androidx.test.espresso.Root;
import androidx.test.espresso.matcher.BoundedMatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PatientLoginInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() {
        // Initialize fragment manager
        FragmentManager fragmentManager = activityRule.getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.FragmentContainer_MainActivity, new PatientLogin())
                .commit();
    }

    @After
    public void tearDown() {
        // Clean up resources if needed
    }



    @Test
    public void A_testPatientLoginWithInvalidCredentials() {
        // Enter invalid email and password
        onView(ViewMatchers.withId(R.id.EditText_Doctor_Enter_Reg_Layout))
                .perform(ViewActions.typeText("invalidemail@gmail.com"), ViewActions.closeSoftKeyboard());
        onView(ViewMatchers.withId(R.id.EditText_Doctor_Enter_Password))
                .perform(ViewActions.typeText("987456"), ViewActions.closeSoftKeyboard());

        // Click the login button
        onView(ViewMatchers.withId(R.id.login_button)).perform(ViewActions.click());
        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
       // Espresso.onView(ViewMatchers.withText("Please verify your email"));
        Espresso.onView(ViewMatchers.withText("Login failed"));
    }

    @Test
    public void B_testPatientLoginWithValidCredentials() {
        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.EditText_Doctor_Enter_Reg_Layout))
                .perform(typeText("nethmi.nawanga@gmail.com")); // Type a valid email
        onView(withId(R.id.EditText_Doctor_Enter_Password))
                .perform(typeText("123456"), closeSoftKeyboard()); // Type a valid password and close the keyboard

        onView(withId(R.id.login_button))
                .perform(click());
        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Check if it navigates to MainActivity2 and displays the MainMenu
        onView(withId(R.id.fragmentContainerView)) // Assuming you have a View with this ID in MainActivity2 layout
                .check(matches(isDisplayed()));
    }
}
