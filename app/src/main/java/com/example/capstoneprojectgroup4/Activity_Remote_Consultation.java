package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Activity_Remote_Consultation extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_consultation);

        Button videoConferenceButton = findViewById(R.id.videoConferenceButton);
        videoConferenceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Handle the video conference button click event here
                // You can start the video conference activity or initiate the call.
            }
        });
    }
}