package com.example.capstoneprojectgroup4.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.StartUpFragment;


public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FragmentManager fm = getSupportFragmentManager();
        StartUpFragment startupPage = (StartUpFragment) fm.findFragmentById(R.id.FragmentContainer_MainActivity);

        if (startupPage == null)
        {
            startupPage = new StartUpFragment();
            fm.beginTransaction().add(R.id.FragmentContainer_MainActivity, startupPage).commit();
        }

    }
}