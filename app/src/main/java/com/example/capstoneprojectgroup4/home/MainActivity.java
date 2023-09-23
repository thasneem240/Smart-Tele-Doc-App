package com.example.capstoneprojectgroup4.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.PatientObject;
import com.example.capstoneprojectgroup4.authentication.PatientProfileF;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.front_end.AccountSettings;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.front_end.StartUpFragment;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorHomePage;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.SearchWordByWord;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.WritingPrescriptionActivity;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.common.collect.Lists;

import java.io.InputStream;


public class MainActivity extends AppCompatActivity
{
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
}