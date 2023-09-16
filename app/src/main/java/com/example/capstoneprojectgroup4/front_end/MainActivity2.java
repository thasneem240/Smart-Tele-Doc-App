package com.example.capstoneprojectgroup4.front_end;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;

public class MainActivity2 extends AppCompatActivity {

    Button homePage;
    Button chatBot;
    Button appointments;
    Button userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        homePage = findViewById(R.id.homePageButton);
        chatBot = findViewById(R.id.chatBotButton);
        appointments = findViewById(R.id.appointmentButton);
        userProfile = findViewById(R.id.userProfileButton);

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
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                AccountSettings searchDoctors = new AccountSettings();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });


        FragmentManager fm = getSupportFragmentManager();
        MainMenu startupPageFragment = new MainMenu();
        fm.beginTransaction().replace(R.id.fragmentContainerView, startupPageFragment).commit();

    }
}