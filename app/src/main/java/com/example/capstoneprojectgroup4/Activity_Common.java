package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Activity_Common extends AppCompatActivity
{

    /* Initialize Variables */

    private Button button_PatientDetails;
    private Button button_MedicalHistory;
    private Button button_LabReports;
    private Button button_ConsNotes;
    private Button button_Prescriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common);

        /* Grab the  UI Variables from Layout file*/

        button_PatientDetails = findViewById(R.id.button_PatientDetails);
        button_MedicalHistory = findViewById(R.id.button_MedicalHistory);
        button_LabReports = findViewById(R.id.button_LabReports);
        button_ConsNotes = findViewById(R.id.button_ConsNotes);
        button_Prescriptions = findViewById(R.id.button_Prescriptions);
    }
}