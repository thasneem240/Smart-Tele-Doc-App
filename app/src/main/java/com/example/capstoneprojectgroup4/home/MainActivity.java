package com.example.capstoneprojectgroup4.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;
import com.example.capstoneprojectgroup4.front_end.StartUpFragment;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity
{// 123
    private static PatientObject patientObject;

    public static void setFirebaseDatabase(FirebaseDatabase mockDatabase) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fm = getSupportFragmentManager();
        StartUpFragment startupPage = (StartUpFragment) fm.findFragmentById(R.id.FragmentContainer_MainActivity);

        if (startupPage == null)
        {
            startupPage = new StartUpFragment();
            fm.beginTransaction().add(R.id.FragmentContainer_MainActivity, startupPage).commit();
        }
    }

    public static void setPatientObject(PatientObject patientObjectP){
        patientObject = patientObjectP;
    }
    public static PatientObject getPatientObject(){
        return patientObject;
    }
}