package com.example.capstoneprojectgroup4;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import androidx.fragment.app.FragmentManager;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.CreatePrescription;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.WritingPrescriptionActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CreatePrescriptionTest {

    @Before
    public void launchFragment() {
        Intent intent = new Intent();
        ActivityScenario<WritingPrescriptionActivity> scenario = ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {
            FragmentManager fm = activity.getSupportFragmentManager();
            CreatePrescription fragment = new CreatePrescription();
            fm.beginTransaction().add(R.id.fragmentContainerPrescription, fragment).commit();
        });
    }

    @Test
    public void testSelectDrugsButton() {
        Espresso.onView(ViewMatchers.withId(R.id.Button_SelectedDrugs)).perform(ViewActions.click());
    }

    @Test
    public void testSubmitPrescriptionButton() {
        Espresso.onView(ViewMatchers.withId(R.id.Button_Submit)).perform(ViewActions.click());
    }

    @Test
    public void testBackButton() {
        Espresso.onView(ViewMatchers.withId(R.id.ImageView_BackButton)).perform(ViewActions.click());
    }

}
