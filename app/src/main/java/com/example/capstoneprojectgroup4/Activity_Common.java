package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Activity_Common extends AppCompatActivity
{

    private static final String FRAGMENT_NAME = "Name_Of_The_Fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);


        Intent intent = getIntent();

        String fragmentName = intent.getStringExtra(FRAGMENT_NAME);

        selectFragment(fragmentName);

    }


    public static Intent getIntent(Context context, String fragName)
    {
        Intent intent = new Intent(context, Activity_Common.class);
        intent.putExtra(FRAGMENT_NAME,fragName);
        return intent;
    }


/* Select an Appropriate Fragment */
    public void selectFragment(String fragmentName)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();

        if (fragmentName == null)
        {
            /* Attach Frag_Manage_patient_Record Fragment into this Activity */
            Frag_Manage_Patient_Record fragManagePatientRecord = (Frag_Manage_Patient_Record) fragmentManager.findFragmentById(R.id.commonContainer);

            // It might already be there!
            if (fragManagePatientRecord == null)
            {
                fragManagePatientRecord = new Frag_Manage_Patient_Record();
                fragmentManager.beginTransaction().add(R.id.commonContainer, fragManagePatientRecord).commit();
            }

        }
    }



}