package com.example.capstoneprojectgroup4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.SurfaceView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Activity_VideoConference extends AppCompatActivity
{
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private SurfaceView localVideoView;
    private SurfaceView remoteVideoView;
    private Button addNoteButton;
    private TextView patientNameTextView;
    private TextView patientMedicalHistoryTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_conference);

        localVideoView = findViewById(R.id.localVideoView);
        remoteVideoView = findViewById(R.id.remoteVideoView);
        addNoteButton = findViewById(R.id.addNoteButton);
        patientNameTextView = findViewById(R.id.patientNameTextView);
        patientMedicalHistoryTextView = findViewById(R.id.patientMedicalHistoryTextView);

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


        addNoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Activity_VideoConference.this,Activity_NoteTaking.class);
                startActivity(intent);
            }
        });




    }


    // Initialize your video conferencing components here
    private void initializeVideoConference()
    {
        // Initialize camera, connect to video streams, etc.
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
            } else
            {
                // Handle permission denial
            }
        }
    }



}