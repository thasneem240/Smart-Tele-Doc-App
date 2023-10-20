package com.example.capstoneprojectgroup4;

import org.junit.Before;
import org.junit.Test;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class Frag_Manage_Patient_RecordTest
{



    @Before
    public void setUp() {
        // Launch the activity that contains the fragment
        ActivityScenario.launch(Activity_Common.class);
    }





    @Test
    public void testButtonClicks()
    {
        // Click on the Patient Details button
        Espresso.onView(ViewMatchers.withId(R.id.button_PatientDetails))
                .perform(ViewActions.click());

        // Add assertions or verifications as needed based on the behavior of the fragment.
        // For example, you can check if a new activity or fragment is started.

        // Similar tests for other buttons (Medical History, Lab Reports, etc.)
    }
}

