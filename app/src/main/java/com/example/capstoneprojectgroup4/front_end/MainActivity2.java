package com.example.capstoneprojectgroup4.front_end;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.Frag_LabReports;
import com.example.capstoneprojectgroup4.Frag_MedicalHistory;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.PatientObject;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;
import com.example.capstoneprojectgroup4.prescriptions.view_prescriptions.ViewPrescriptionsFragment;
import com.example.capstoneprojectgroup4.search_doctors.SearchDocF;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.common.collect.Lists;

import java.io.InputStream;

public class MainActivity2 extends AppCompatActivity {

    private PatientObject patientObject;
    Button homePage;
    Button chatBot;
    Button appointments;
    Button userProfile;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent recive = getIntent();

        homePage = findViewById(R.id.homePageButton);
        chatBot = findViewById(R.id.chatBotButton);
        appointments = findViewById(R.id.appointmentButton);
        userProfile = findViewById(R.id.userProfileButton);

        FragmentManager fm = getSupportFragmentManager();
        MainMenu mainMenu = (MainMenu) fm.findFragmentById(R.id.fragmentContainerView);

        if (mainMenu == null)
        {
            mainMenu = new MainMenu();
            fm.beginTransaction().add(R.id.fragmentContainerView, mainMenu).commit();
        }
        if (recive.getStringExtra("Page") != null){
            if (recive.getStringExtra("Page").equals("searchDoctor")){
                fm = getSupportFragmentManager();
                SearchDocF searchDoctors = new SearchDocF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
            if (recive.getStringExtra("Page").equals("searchPharm")){
                fm = getSupportFragmentManager();
                PharmaciesF pharmacies = new PharmaciesF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, pharmacies).commit();
            }
            if (recive.getStringExtra("Page").equals("patientRecords")){
                fm = getSupportFragmentManager();
                MedicalRecords medicalRecords = new MedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerView, medicalRecords).commit();
            }
            if (recive.getStringExtra("Page").equals("patientDetails")){
                fm = getSupportFragmentManager();
                AccountSettings accountSettings = new AccountSettings();
                fm.beginTransaction().replace(R.id.fragmentContainerView, accountSettings).commit();
            }

            if (recive.getStringExtra("Page").equals("medicalHistory")){
                fm = getSupportFragmentManager();
                Frag_MedicalHistory fragMedicalHistory = new Frag_MedicalHistory();
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragMedicalHistory).commit();
            }
            if (recive.getStringExtra("Page").equals("prescriptionsPage")){
                fm = getSupportFragmentManager();
                ViewPrescriptionsFragment viewPrescriptionsFragment = new ViewPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, viewPrescriptionsFragment).commit();
            }
            if (recive.getStringExtra("Page").equals("labReport")){
                fm = getSupportFragmentManager();
                Frag_LabReports fragLabReports = new Frag_LabReports();
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragLabReports).commit();
            }

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
                Intent chatbotActivity = new Intent(MainActivity2.this, ChatbotActivity.class);
                startActivity(chatbotActivity);
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                ViewAppointments searchDoctors = new ViewAppointments();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                AccountSettings searchDoctors = new AccountSettings();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();

//                FragmentManager fm = getSupportFragmentManager();
//                PatientProfileF patientProfileF = new PatientProfileF();
//                fm.beginTransaction().replace(R.id.fragmentContainerView, patientProfileF).commit();
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