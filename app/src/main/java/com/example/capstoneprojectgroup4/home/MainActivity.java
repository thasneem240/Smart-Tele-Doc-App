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
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.front_end.AccountSettings;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.front_end.StartUpFragment;
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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        FragmentManager fm = getSupportFragmentManager();
        StartUpFragment startupPage = (StartUpFragment) fm.findFragmentById(R.id.fragmentContainerView2);

        if (startupPage == null)
        {
            startupPage = new StartUpFragment();
            fm.beginTransaction().add(R.id.fragmentContainerView2, startupPage).commit();
        }
    }

    public void setPatientObject(PatientObject patientObject) {
//        this.patientObject = patientObject;
//        Log.d(TAG, patientObject.toString());

        try{
            InputStream stream = this.getResources().openRawResource(R.raw.credential);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();
            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            Log.d("WhereAreYou", "I'm here4");
            SessionsClient sessionsClient = SessionsClient.create(sessionsSettings);
        }catch (Exception e){

        }
    }
}