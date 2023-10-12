package com.example.capstoneprojectgroup4.interface_of_doctors.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorViewAppointments;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.ListOfPatients_writingPrescription.AppointmentObject;

public class DoctorsActivity extends AppCompatActivity {
    private static String doctorRegNumber;
    private static DoctorObject doctorObject;
    private static AppointmentObject appointmentObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);

        Button homePage = findViewById(R.id.homePageButton2);
        Button chatBot = findViewById(R.id.chatBotButton2);
        Button appointments = findViewById(R.id.appointmentButton2);
        Button userProfile = findViewById(R.id.userProfileButton2);

        FragmentManager fm = getSupportFragmentManager();
//        DoctorMainMenu doctorMainMenu = (DoctorMainMenu) fm.findFragmentById(R.id.fragmentContainerDoctorsActivity);
//
//        if (doctorMainMenu == null)
//        {
//            doctorMainMenu = new DoctorMainMenu();
//            fm.beginTransaction().add(R.id.fragmentContainerDoctorsActivity, doctorMainMenu).commit();
//        }

        DoctorMainMenu searchDoctors = new DoctorMainMenu();
        fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, searchDoctors).commit();

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                FragmentManager fm = getSupportFragmentManager();
                DoctorViewAppointments searchDoctors = new DoctorViewAppointments();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, searchDoctors).commit();
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorUserProfile doctorUserProfile = new DoctorUserProfile();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorUserProfile).commit();
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
    public static AppointmentObject getAppointmentObject() {
        return appointmentObject;
    }

    public static void setAppointmentObject(AppointmentObject appointmentObject) {
        DoctorsActivity.appointmentObject = appointmentObject;
    }
}