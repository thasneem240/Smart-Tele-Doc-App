package com.example.capstoneprojectgroup4.interface_of_doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

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

        FragmentManager fm = getSupportFragmentManager();
        DoctorHomePage doctorHomePage = (DoctorHomePage) fm.findFragmentById(R.id.fragmentContainerDoctorsActivity);

        if (doctorHomePage == null)
        {
            doctorHomePage = new DoctorHomePage();
            fm.beginTransaction().add(R.id.fragmentContainerDoctorsActivity, doctorHomePage).commit();
        }
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