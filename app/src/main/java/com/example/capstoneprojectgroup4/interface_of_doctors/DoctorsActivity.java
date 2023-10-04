package com.example.capstoneprojectgroup4.interface_of_doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.home.MainActivity;

public class DoctorsActivity extends AppCompatActivity {
    private static String doctorRegNumber;
    private static DoctorObject doctorObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        Button homePage = findViewById(R.id.homePageButton2);
        Button chatBot = findViewById(R.id.chatBotButton2);
        Button appointments = findViewById(R.id.appointmentButton2);
        Button userProfile = findViewById(R.id.userProfileButton2);

        FragmentManager fm = getSupportFragmentManager();
        DoctorMainMenu doctorMainMenu = (DoctorMainMenu) fm.findFragmentById(R.id.fragmentContainerDoctorsActivity);

        if (doctorMainMenu == null)
        {
            doctorMainMenu = new DoctorMainMenu();
            fm.beginTransaction().add(R.id.fragmentContainerDoctorsActivity, doctorMainMenu).commit();
        }

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                DoctorMainMenu searchDoctors = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, searchDoctors).commit();
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

            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }

    public static DoctorObject getDoctorObject() {
        return doctorObject;
    }

    public static void setDoctorObject(DoctorObject doctorObject) {
        DoctorsActivity.doctorObject = doctorObject;
    }
    public static String getDoctorRegNumber() {
        return doctorRegNumber;
    }

    public static void setDoctorRegNumber(String doctorRegNumber) {
        DoctorsActivity.doctorRegNumber = doctorRegNumber;
    }
}