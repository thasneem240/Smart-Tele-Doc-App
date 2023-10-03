package com.example.capstoneprojectgroup4;

import androidx.annotation.IdRes;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.ViewInteraction;
import org.hamcrest.Description;
import androidx.test.espresso.contrib.RecyclerViewActions;

import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import com.example.capstoneprojectgroup4.authentication.PatientObject;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.search_doctors.BookAppointmentF;
import com.example.capstoneprojectgroup4.search_doctors.SearchDocF;
import androidx.documentfile.provider.DocumentFile;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.Mockito;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;

import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.google.common.base.CharMatcher.is;
import static com.google.common.base.Predicates.instanceOf;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class SearchDocFInstrumentedTest {

    private FragmentManager fragmentManager;

    @Before
    public void setUp() {
        // Initialize fragment manager
        fragmentManager = null; // Initialize to null
    }

    @After
    public void tearDown() {
        // Clean up resources if needed
    }

    public static class MyViewAction {

        public  ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }

                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }

    @Test
    public void testSearchAndBookAppointment() {



        // Launch the MainActivity and navigate to the SearchDocF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        // Type text into the EditText field
        onView(withId(R.id.PatsearchName))
                .perform(typeText("Noel"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));

        // Perform click on the search button
        onView(withId(R.id.searchDoctorsNSLDButton))
                .perform(click());

        // Wait for the Doctor search results to load
        try {
            Thread.sleep(1000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on "Dr. Noel Somasundaram" text within CardView1
        onView(withId(R.id.searchrv)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickButtonWithId(R.id.textName))
        );

        // Wait for the DocAvailF fragment to load
        try {
            Thread.sleep(25000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.backButtonDocAvail)).check(matches(isDisplayed()));

        onView(withId(R.id.recyclerView)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, clickButtonWithId(R.id.textDay)));




    }




    public static ViewAction clickButtonWithId(@IdRes final int buttonId) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(View.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "Click on a button with specified resource id";
            }

            @Override
            public void perform(UiController uiController, View view) {
                View button = view.findViewById(buttonId);
                if (button != null) {
                    button.performClick();
                }
            }
        };
    }





    @Test
    public void G_testSearchDoctorsName() {
        // Launch the MainActivity and navigate to the SearchDocF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        // Type text into the EditText field
        onView(withId(R.id.PatsearchName))
                .perform(typeText("Noel"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform click
        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                .perform(click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(5000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                                .perform(ViewActions.click());
                    }
                });
    }

    @Test
    public void A_testSearchDoctorsSpec() {
        // Launch the MainActivity and navigate to the SearchDocF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        // Type text into the EditText field

        onView(withId(R.id.searchSpec))
                .perform(typeText("Eye Surgeon"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform click
        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                .perform(click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(5000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                                .perform(ViewActions.click());
                    }
                });
    }


    @Test
    public void B_testSearchDoctorsSpecandLoc() {
        // Launch the MainActivity and navigate to the SearchDocF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        onView(withId(R.id.searchSpec))
                .perform(typeText("Eye Surgeon"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(withId(R.id.searchLoc))
                .perform(typeText("Nawaloka"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform click
        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                .perform(click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(5000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                                .perform(ViewActions.click());
                    }
                });
    }

    @Test
    public void C_testSearchDoctorsNameandLoc() {
        // Launch the MainActivity and navigate to the SearchDocF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        onView(withId(R.id.PatsearchName))
                .perform(typeText("Noel"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(withId(R.id.searchLoc))
                .perform(typeText("Nawaloka"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform click
        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                .perform(click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(5000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                                .perform(ViewActions.click());
                    }
                });
    }







    @Test
    public void D_testSearchDoctorsSpecandName() {
        // Launch the MainActivity and navigate to the SearchDocF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        onView(withId(R.id.PatsearchName))
                .perform(typeText("Saliya"), ViewActions.closeSoftKeyboard()); // Close the keyboard
        // Type text into the EditText field

        onView(withId(R.id.searchSpec))
                .perform(typeText("Eye Surgeon"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform click
        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                .perform(click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(5000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                                .perform(ViewActions.click());
                    }
                });
    }


    @Test
    public void E_testSearchDoctorsLocation() {
        // Launch the MainActivity and navigate to the SearchDocF fragment
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        // Type text into the EditText field

        onView(withId(R.id.searchLoc))
                .perform(typeText("Nawaloka"), ViewActions.closeSoftKeyboard()); // Close the keyboard

        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));

        try {
            Thread.sleep(5000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Perform click
        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                .perform(click())
                .withFailureHandler(new FailureHandler() {
                    @Override
                    public void handle(Throwable error, Matcher<View> viewMatcher) {
                        // Wait for a short period (e.g., 1 second) and then try clicking again
                        try {
                            Thread.sleep(5000); // Adjust the sleep duration as needed
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        onView(ViewMatchers.withId(R.id.searchDoctorsNSLDButton))
                                .perform(ViewActions.click());
                    }
                });
    }

    @Test
    public void F_testSearchDoctorDate() {
        ActivityScenario<MainActivity2> scenario = ActivityScenario.launch(MainActivity2.class);
        scenario.onActivity(activity -> {
            fragmentManager = activity.getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, new SearchDocF()).commit();
        });

        // Perform a double-click action to trigger the date picker dialog
        onView(withId(R.id.searchDate))
                .perform(doubleClick());

        // Wait for a second before proceeding with interactions (optional)
        try {
            Thread.sleep(1000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Set the desired date in the DatePicker widget
        onView(ViewMatchers.withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(setDateInDatePicker(2023, 11, 5));

        // Close the keyboard
        Espresso.closeSoftKeyboard();

        // Wait for a moment (optional)
        try {
            Thread.sleep(2000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.closeSoftKeyboard();

        // Click the "OK" button in the DatePicker dialog
        onView(withText("OK")) // Replace "OK" with the actual text on the OK button if it's not "OK"
                .perform(click());
        Espresso.closeSoftKeyboard();

        // Wait for a moment (optional)
        try {
            Thread.sleep(2000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Ensure the keyboard is closed before clicking the search button
        onView(withId(R.id.searchDoctorsNSLDButton)).check(matches(isDisplayed()));
        Espresso.closeSoftKeyboard();

        // Wait for a moment (optional)
        try {
            Thread.sleep(2000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Espresso.closeSoftKeyboard();
        try {
            Thread.sleep(2000); // Adjust the sleep duration as needed
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Perform click on the search button
        onView(withId(R.id.searchDoctorsNSLDButton))
                .perform(click());
    }




    /**
     * Custom ViewAction to set a date in a DatePicker widget.
     *
     * @param year  The desired year.
     * @param month The desired month (0-11).
     * @param day   The desired day of the month.
     * @return A ViewAction that sets the date in the DatePicker widget.
     */
    private static ViewAction setDateInDatePicker(final int year, final int month, final int day) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return allOf(isAssignableFrom(DatePicker.class), isDisplayed());
            }

            @Override
            public String getDescription() {
                return "set date in DatePicker";
            }

            @Override
            public void perform(UiController uiController, View view) {
                DatePicker datePicker = (DatePicker) view;

                // Set the desired date in the DatePicker widget
                datePicker.updateDate(year, month, day);
            }
        };
    }



}
