package com.example.capstoneprojectgroup4.interface_of_doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.front_end.AccountSettings;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        FragmentManager fm = getSupportFragmentManager();


        Button  homePage = findViewById(R.id.homePageButton2);
        Button chatBot = findViewById(R.id.chatBotButton2);
        Button appointments = findViewById(R.id.appointmentButton2);
        Button userProfile = findViewById(R.id.userProfileButton2);

        DoctorMainMenu mainMenu = (DoctorMainMenu) fm.findFragmentById(R.id.docmenufragmentContainer);

        if (mainMenu == null)
        {
            mainMenu = new DoctorMainMenu();
            fm.beginTransaction().add(R.id.docmenufragmentContainer, mainMenu).commit();
        }

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DoctorMainMenu searchDoctors = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.docmenufragmentContainer, searchDoctors).commit();
            }
        });

        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DoctorViewAppointments searchDoctors = new DoctorViewAppointments();
                fm.beginTransaction().replace(R.id.docmenufragmentContainer, searchDoctors).commit();
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                Doctor_User_Profile searchDoctors = new Doctor_User_Profile();
                fm.beginTransaction().replace(R.id.docmenufragmentContainer, searchDoctors).commit();

//                FragmentManager fm = getSupportFragmentManager();
//                PatientProfileF patientProfileF = new PatientProfileF();
//                fm.beginTransaction().replace(R.id.fragmentContainerView, patientProfileF).commit();
            }
        });
    }
}