package com.example.capstoneprojectgroup4.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        StartupF startupPage = (StartupF) fm.findFragmentById(R.id.fragment_container);

        if (startupPage == null) {
            startupPage = new StartupF();
            fm.beginTransaction().add(R.id.fragment_container, startupPage).commit();
        }
    }
}