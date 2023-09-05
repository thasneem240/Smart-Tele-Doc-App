package com.example.capstoneprojectgroup4.home;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.PatientObject;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.common.collect.Lists;
import com.google.firebase.auth.FirebaseUser;

import org.apache.commons.lang3.builder.Builder;

import java.io.InputStream;


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