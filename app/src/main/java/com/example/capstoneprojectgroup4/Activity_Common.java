package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

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
        else
        {
            if(fragmentName.equals("Frag_PatientDetails"))
            {
                Frag_PatientDetails fragPatientDetails = new Frag_PatientDetails();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.commonContainer,fragPatientDetails);
                fragmentTransaction.commit();


            }
            else
            {
                if(fragmentName.equals("Frag_MedicalHistory"))
                {
                    Frag_MedicalHistory fragMedicalHistory = new Frag_MedicalHistory();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.commonContainer,fragMedicalHistory);
                    fragmentTransaction.commit();
                }
                else
                {
                    if(fragmentName.equals("Frag_LabReports"))
                    {
                        Frag_LabReports fragLabReports = new Frag_LabReports();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.commonContainer,fragLabReports);
                        fragmentTransaction.commit();
                    }
                    else
                    {
                        if(fragmentName.equals("Frag_ConsNotes"))
                        {
                            Frag_ConsNotes fragConsNotes = new Frag_ConsNotes();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.commonContainer,fragConsNotes);
                            fragmentTransaction.commit();
                        }
                        else
                        {
                            if(fragmentName.equals("Frag_Prescriptions"))
                            {
                                Frag_Prescriptions fragPrescriptions = new Frag_Prescriptions();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                fragmentTransaction.replace(R.id.commonContainer,fragPrescriptions);
                                fragmentTransaction.commit();
                            }

                        }

                    }

                }

            }

        }
    }



}