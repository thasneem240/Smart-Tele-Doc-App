package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorViewAppointments;
import com.example.capstoneprojectgroup4.medical_records_prescriptions.prescriptions_list.PrescriptionsListFragment;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;
import android.util.Log;
public class Activity_Agora_VideoConference extends AppCompatActivity
{

    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS =
            {
                    android.Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            };

    private int uid = 2;
    private String userType = "Doctor";

    private boolean isJoined = false;

    private RtcEngine agoraEngin;
    private SurfaceView localSurfaceView;
    private SurfaceView remoteSurfaceView;
    private ImageView backButtonAgoraVideoConference;

    private String strData;




    public boolean checkSelfPermission()
    {
        if (ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[0]) !=  PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, REQUESTED_PERMISSIONS[1]) !=  PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        return true;
    }

    void showMessage(String message)
    {
        runOnUiThread(()-> Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show());
    }



    public void setupVideoSDKEngine()
    {
        try
        {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getBaseContext();
            config.mAppId = getResources().getString(R.string.appId);
            config.mEventHandler = mRtcHandler;
            agoraEngin = RtcEngine.create(config);
            agoraEngin.enableVideo();

        }catch (Exception e)
        {
            showMessage(e.toString());
        }
    }

    private final IRtcEngineEventHandler mRtcHandler = new IRtcEngineEventHandler()
    {
        @Override
        public void onUserJoined(int uid, int elapsed)
        {
            super.onUserJoined(uid, elapsed);
            showMessage("Remote user joined "+uid);
//            showMessage( userType + " joined : "+uid);
            runOnUiThread(()->setupRemoteVideo(uid));
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed)
        {
            super.onJoinChannelSuccess(channel, uid, elapsed);
            isJoined = true;
            showMessage(userType + "Joined Channel " +channel);
        }

        @Override
        public void onUserOffline(int uid, int reason)
        {
            super.onUserOffline(uid, reason);

            showMessage("Remote user offline "+uid +" "+reason);
            runOnUiThread(()->remoteSurfaceView.setVisibility(View.GONE));
        }
    };

    private void setupRemoteVideo(int uid)
    {
        FrameLayout container = findViewById(R.id.remoteVideo);
        remoteSurfaceView = new SurfaceView(getBaseContext());
        remoteSurfaceView.setZOrderMediaOverlay(true);
        container.addView(remoteSurfaceView);
        agoraEngin.setupRemoteVideo(new VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        remoteSurfaceView.setVisibility(View.VISIBLE);
    }

    private void setupLocalVideo()
    {
        FrameLayout container = findViewById(R.id.localVideo);
        localSurfaceView = new SurfaceView(getBaseContext());
        container.addView(localSurfaceView);
        agoraEngin.setupLocalVideo(new VideoCanvas(localSurfaceView, VideoCanvas.RENDER_MODE_HIDDEN, 0));
    }

    public void joinChannel(View view)
    {
        if(checkSelfPermission())
        {
            ChannelMediaOptions options = new ChannelMediaOptions();
            options.channelProfile = Constants.CHANNEL_PROFILE_COMMUNICATION;
            options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER;
            setupLocalVideo();
            localSurfaceView.setVisibility(View.VISIBLE);
            agoraEngin.startPreview();
            agoraEngin.joinChannel(getResources().getString(R.string.token),getResources().getString(R.string.channel),uid,options);

        }else
        {
            showMessage("Permission was not granted");
        }
    }

    public void leaveChannel(View view)
    {
        if(!isJoined)
        {
            showMessage("Join a channel first");
        }
        else
        {
            agoraEngin.leaveChannel();
            showMessage("You left channel");
            if(remoteSurfaceView != null) remoteSurfaceView.setVisibility(View.GONE);
            if(localSurfaceView != null) localSurfaceView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agora_video_conference);

        backButtonAgoraVideoConference = findViewById(R.id.backButtonAgoraVideoConference);

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

        if(!checkSelfPermission()){
            ActivityCompat.requestPermissions(this,REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        setupVideoSDKEngine();


        backButtonAgoraVideoConference.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                if (strData != null)
//                {
//                    Log.d("FragmentTransaction", "Before transaction");
//
//                    if(strData.equals("Patient"))
//                    {
//                        FragmentManager fm = getSupportFragmentManager();
//                        ViewAppointments viewAppointments = new ViewAppointments();
//                        fm.beginTransaction().replace(R.id.fragmentContainerView, viewAppointments).commit();
//                    }
//                    else // back button for Doctors
//                    {
//                        FragmentManager fm = getSupportFragmentManager();
//                        DoctorViewAppointments doctorViewAppointments = new DoctorViewAppointments();
//                        fm.beginTransaction().replace(R.id.fragmentContainerView, doctorViewAppointments).commit();
//                    }
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




    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        agoraEngin.stopPreview();
        agoraEngin.leaveChannel();

        new Thread(()->{
            RtcEngine.destroy();
            agoraEngin = null;
        }).start();
    }
}