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
    private PatientObject patientObject;
    Button homePage;
    Button chatBot;
    Button appointments;
    Button userProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        homePage = findViewById(R.id.homePageButton);
        chatBot = findViewById(R.id.chatBotButton);
        appointments = findViewById(R.id.appointmentButton);
        userProfile = findViewById(R.id.userProfileButton);

        FragmentManager fm = getSupportFragmentManager();
        StartUpFragment startupPage = (StartUpFragment) fm.findFragmentById(R.id.fragmentContainerView);

        if (startupPage == null)
        {
            startupPage = new StartUpFragment();
            fm.beginTransaction().add(R.id.fragmentContainerView, startupPage).commit();
        }

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatbotActivity = new Intent(MainActivity.this, ChatbotActivity.class);
                startActivity(chatbotActivity);
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fm = getSupportFragmentManager();
//                AccountSettings searchDoctors = new AccountSettings();
//                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();

                FragmentManager fm = getSupportFragmentManager();
                PatientProfileF patientProfileF = new PatientProfileF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, patientProfileF).commit();
            }
        });
    }

    public void setPatientObject(PatientObject patientObject) {
        try{
            InputStream stream = this.getResources().openRawResource(R.raw.credential);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();
            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            SessionsClient sessionsClient = SessionsClient.create(sessionsSettings);
        }catch (Exception e){

        }
    }
}