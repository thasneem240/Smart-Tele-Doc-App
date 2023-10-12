package com.example.capstoneprojectgroup4.interface_of_doctors.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.Frag_LabReports;
import com.example.capstoneprojectgroup4.Frag_MedicalHistory;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsFragment;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorViewAppointments;
import com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_writingPrescription.AppointmentObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_writingPrescription.ListOfPatientsFragment;
import com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOfPatients_patientProfile.ListOfPatientsFragment2;
import com.example.capstoneprojectgroup4.patient_authentication.AccountSettings;
import com.example.capstoneprojectgroup4.search_doctors.SearchDocF;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;

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
        DoctorMainMenu doctorMainMenu = (DoctorMainMenu) fm.findFragmentById(R.id.fragmentContainerDoctorsActivity);
        Intent recive = getIntent();
        if (doctorMainMenu == null)
        {
            doctorMainMenu = new DoctorMainMenu();
            fm.beginTransaction().add(R.id.fragmentContainerDoctorsActivity, doctorMainMenu).commit();
        }


        DoctorMainMenu searchDoctors = new DoctorMainMenu();
        fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, searchDoctors).commit();


        if (recive.getStringExtra("Page") != null){
            if (recive.getStringExtra("Page").equals("Appointmentspage")) {
                DoctorViewAppointments Appointmentspage = new DoctorViewAppointments();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, Appointmentspage).commit();
            }
            if (recive.getStringExtra("Page").equals("WritePrescriptionpage")) {
                ListOfPatientsFragment listOfPatientsFragment = new ListOfPatientsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment).commit();
            }
            if (recive.getStringExtra("Page").equals("ListofPatientspage")) {
                ListOfPatientsFragment2 listOfPatientsFragment2 = new ListOfPatientsFragment2();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment2).commit();
            }
            if (recive.getStringExtra("Page").equals("DoctorProfilepage")) {
                DoctorUserProfile doctorUserProfile = new DoctorUserProfile();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorUserProfile).commit();
            }
            /*if (recive.getStringExtra("Page").equals("Conferencingpage")) {
                DoctorViewAppointments Conferencingpage = new DoctorViewAppointments();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, Conferencingpage).commit();
            }*/
        }

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
                Intent chatbotActivity = new Intent(DoctorsActivity.this, ChatbotActivity.class);
                chatbotActivity.putExtra("DOCINT","true");
                startActivity(chatbotActivity);
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