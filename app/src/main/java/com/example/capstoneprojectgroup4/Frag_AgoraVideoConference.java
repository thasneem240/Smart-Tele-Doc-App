package com.example.capstoneprojectgroup4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import io.agora.rtc2.ChannelMediaOptions;
import io.agora.rtc2.Constants;
import io.agora.rtc2.IRtcEngineEventHandler;
import io.agora.rtc2.RtcEngine;
import io.agora.rtc2.RtcEngineConfig;
import io.agora.rtc2.video.VideoCanvas;




/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_AgoraVideoConference#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_AgoraVideoConference extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static final int PERMISSION_REQ_ID = 22;
    private static final String[] REQUESTED_PERMISSIONS =
            {
                    android.Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.CAMERA
            };

    private int uid = 1; // 1: for Patient, 2: for Doctor
    private String userType = "Patient";

    private boolean isJoined = false;

    private RtcEngine agoraEngin;
    private SurfaceView localSurfaceView;
    private SurfaceView remoteSurfaceView;



    public Frag_AgoraVideoConference()
    {
        // Required empty public constructor
    }



    public Frag_AgoraVideoConference(int uid)
    {

        this.uid = uid;

        if(uid != 1)
        {
            userType = "Doctor";
        }

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_AgoraVideoConference.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_AgoraVideoConference newInstance(String param1, String param2)
    {
        Frag_AgoraVideoConference fragment = new Frag_AgoraVideoConference();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    private boolean checkSelfPermission()
    {
        if (ContextCompat.checkSelfPermission(getActivity(), REQUESTED_PERMISSIONS[0]) !=  PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(getActivity(), REQUESTED_PERMISSIONS[1]) !=  PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        return true;
    }

    void showMessage(String message)
    {
        getActivity().runOnUiThread(()-> Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show());
    }



    private void setupVideoSDKEngine()
    {
        try
        {
            RtcEngineConfig config = new RtcEngineConfig();
            config.mContext = getActivity().getBaseContext();
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
            showMessage(userType + " joined "+uid);
            getActivity().runOnUiThread(()->setupRemoteVideo(uid));
        }

        @Override
        public void onJoinChannelSuccess(String channel, int uid, int elapsed)
        {
            super.onJoinChannelSuccess(channel, uid, elapsed);
            isJoined = true;
            showMessage("Joined Channel "+channel);
        }

        @Override
        public void onUserOffline(int uid, int reason)
        {
            super.onUserOffline(uid, reason);

            showMessage("Remote user offline "+uid +" "+reason);
            getActivity().runOnUiThread(()->remoteSurfaceView.setVisibility(View.GONE));
        }
    };

    private void setupRemoteVideo(int uid)
    {
        FrameLayout container = requireView().findViewById(R.id.remoteVideo);
        remoteSurfaceView = new SurfaceView(getActivity().getBaseContext());
        remoteSurfaceView.setZOrderMediaOverlay(true);
        container.addView(remoteSurfaceView);
        agoraEngin.setupRemoteVideo(new VideoCanvas(remoteSurfaceView, VideoCanvas.RENDER_MODE_FIT, uid));
        remoteSurfaceView.setVisibility(View.VISIBLE);
    }

    private void setupLocalVideo()
    {
        FrameLayout container = requireView().findViewById(R.id.localVideo);
        localSurfaceView = new SurfaceView(getActivity().getBaseContext());
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_agora_video_conference, container, false);



        if(!checkSelfPermission()){
            ActivityCompat.requestPermissions(getActivity(),REQUESTED_PERMISSIONS, PERMISSION_REQ_ID);
        }
        setupVideoSDKEngine();


        return view;
    }

    @Override
    public void onDestroy()
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