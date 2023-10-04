package com.example.capstoneprojectgroup4;

import android.app.Application;
import com.google.firebase.FirebaseApp;

public class TestApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize Firebase here
        FirebaseApp.initializeApp(this);
    }
}
