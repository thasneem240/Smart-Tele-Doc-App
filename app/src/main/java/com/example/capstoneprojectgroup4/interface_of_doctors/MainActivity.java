package com.example.capstoneprojectgroup4.interface_of_doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.StartUpFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        FragmentManager fm = getSupportFragmentManager();
        DoctorLoginPage startupPage = (DoctorLoginPage) fm.findFragmentById(R.id.DoctorfragmentContainerView2);

        if (startupPage == null)
        {
            startupPage = new DoctorLoginPage();
            fm.beginTransaction().add(R.id.DoctorfragmentContainerView2, startupPage).commit();
        }
    }
}