package com.example.capstoneprojectgroup4.home;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.PatientObject;


public class MainActivity extends AppCompatActivity
{
    private PatientObject patientObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getSupportFragmentManager();
        StartupF startupPage = (StartupF) fm.findFragmentById(R.id.fragment_container);

        if (startupPage == null)
        {
            startupPage = new StartupF();
            fm.beginTransaction().add(R.id.fragment_container, startupPage).commit();
        }
    }

    public void setPatientObject(PatientObject patientObject) {
        this.patientObject = patientObject;
        Log.d(TAG, patientObject.toString());
    }
}