package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorViewAppointments;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;

import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.ChannelMediaOptions;





public class Activity_Agora_AudioConference extends AppCompatActivity
{

    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS =
            {
                    Manifest.permission.RECORD_AUDIO
            };

    public boolean checkSelfPermission()
    {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) !=  PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }

        return true;
    }


    private void showMessage(String message)
    {
        runOnUiThread(() ->
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }



    // Fill the App ID of your project generated on Agora Console.
    private String appId;
    // Fill the channel name.
    private String channelName;
    // Fill the temp token generated on Agora Console.
    private String token;
    // An integer that identifies the local user.
    private int uid = 2;
    private String userType = "Doctor";

    // Track the status of your connection
    private boolean isJoined = false;

    // Agora engine instance
    private RtcEngine agoraEngine;
    // UI elements
    private TextView infoText;
    private Button joinLeaveButton;
    private ImageView backButtonAgoraAudioConference;
    private String strData;


    /**
     * Setup Agora Engine
     */
    public void setupVoiceSDKEngine()
    {
        try
        {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = appId;
            config.mEventHandler = mRtcEventHandler;
            agoraEngine = RtcEngine.create(config);
        }
        catch (Exception e)
        {
            throw new RuntimeException("Check the error.");
        }
    }


    /**
     * Handle and respond to Agora Engine events
     *
     */


    private final IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler()
    {
        @Override
        // Listen for the remote user joining the channel.
        public void onUserJoined(int uid, int elapsed)
        {
            runOnUiThread(()->infoText.setText("Remote user joined: " + uid));
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed)
        {
            // Successfully joined a channel
            isJoined = true;
            showMessage( userType + "Joined Channel " + channel);
            runOnUiThread(()->infoText.setText("Waiting for a remote user to join"));
        }

        @Override
        public void onUserOffline(int uid, int reason)
        {
            // Listen for remote users leaving the channel
            showMessage("Remote user offline " + uid + " " + reason);
            if (isJoined) runOnUiThread(()->infoText.setText("Waiting for a remote user to join"));
        }

        @Override
        public void onLeaveChannel(RtcStats 	stats)
        {
            // Listen for the local user leaving the channel
            runOnUiThread(()->infoText.setText("Press the button to join a channel"));
            isJoined = false;
        }
    };


    /**
     * Configure the channel before joining
     */
    private void joinChannel()
    {
        ChannelMediaOptions options = new ChannelMediaOptions();
        options.autoSubscribeAudio = true;
        // Set both clients as the BROADCASTER.
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
        // Set the channel profile as BROADCASTING.
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING;

        // Join the channel with a temp token.
        // You need to specify the user ID yourself, and ensure that it is unique in the channel.
        agoraEngine.joinChannel(token, channelName, uid, options);
    }

    /**
     * Join and leave a channel
     */

    public void joinLeaveChannel(View view)
    {
        if (isJoined)
        {
            agoraEngine.leaveChannel();
            joinLeaveButton.setText("Join");
        }
        else
        {
            joinChannel();
            joinLeaveButton.setText("Leave");
        }
    }


    /**
     * Check that the app has the correct permissions and initiate Agora Engine
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_audio_conference);

        backButtonAgoraAudioConference = findViewById(R.id.backButtonAgoraAudioConference);

        appId = getResources().getString(R.string.appId);
        // Fill the channel name.
        channelName = getResources().getString(R.string.channel);
        // Fill the temp token generated on Agora Console.
        token = getResources().getString(R.string.token);

        // Retrieve data from the Intent
        Intent intent = getIntent();
        if (intent != null)
        {
            strData = intent.getStringExtra("userType");

            // Now, userType contains the value sent from the previous activity
            if (strData != null)
            {

                if(strData.equals("Patient"))
                {
                    uid = 1;
                    userType = "Patient";
                }


            }
        }


        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (!checkSelfPermission())
        {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }

        setupVoiceSDKEngine();

        // Set up access to the UI elements
        joinLeaveButton = findViewById(R.id.joinLeaveButton);
        infoText = findViewById(R.id.infoText);


        backButtonAgoraAudioConference.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                if(strData.equals("Patient"))
//                {
//                    FragmentManager fm = getSupportFragmentManager();
//                    ViewAppointments viewAppointments = new ViewAppointments();
//                    fm.beginTransaction().replace(R.id.fragmentContainerView, viewAppointments).commit();
//                }
//                else // back button for Doctors
//                {
//                    FragmentManager fm = getSupportFragmentManager();
//                    DoctorViewAppointments doctorViewAppointments = new DoctorViewAppointments();
//                    fm.beginTransaction().replace(R.id.fragmentContainerView, doctorViewAppointments).commit();
//                }

                onBackPressed();
            }
        });



    }



    @Override
    public void onBackPressed()
    {
        // Handle the back key press event here
        // You can perform custom actions or call super.onBackPressed() to use the default behavior
        super.onBackPressed();
    }




    /**
     * Clean up the resources used by your app
     */

    protected void onDestroy()
    {
        super.onDestroy();
        agoraEngine.leaveChannel();

        // Destroy the engine in a sub-thread to avoid congestion
        new Thread(() -> {
            RtcEngine.destroy();
            agoraEngine = null;
        }).start();
    }


}