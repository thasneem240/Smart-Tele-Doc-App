package com.example.capstoneprojectgroup4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.TextView;


public class Activity_VideoConference extends AppCompatActivity
{
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private SurfaceView localVideoView;
    private SurfaceView remoteVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conference);

        localVideoView = findViewById(R.id.localVideoView);
        remoteVideoView = findViewById(R.id.remoteVideoView);

        // Check and request camera permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else
        {
            // Initialize video conferencing components
            initializeVideoConference();
        }


        Patient patient = getPatientData("A.S.M. Thasneem"); // Fetch patient data by name
        TextView patientNameTextView = findViewById(R.id.patientNameTextView);
        TextView patientMedicalHistoryTextView = findViewById(R.id.patientMedicalHistoryTextView);

        if (patient != null) {
            patientNameTextView.setText("Patient Name: " + patient.getPatientName());
            //patientMedicalHistoryTextView.setText("Medical History: " + patient.getMedicalHistory());
        }







    }



    // Initialize your video conferencing components here
    private void initializeVideoConference()
    {
        // Initialize camera, connect to video streams, etc.
    }


    private Patient getPatientData(String patientName)
    {
        // Query the database or API to retrieve patient data by name
        // Return a Patient object with the fetched data
    }


    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                initializeVideoConference();
            } else {
                // Handle permission denial
            }
        }
    }
}