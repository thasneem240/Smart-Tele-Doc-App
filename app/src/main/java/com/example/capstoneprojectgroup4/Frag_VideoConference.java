package com.example.capstoneprojectgroup4;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_VideoConference#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_VideoConference extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private static final int CAMERA_PERMISSION_REQUEST_CODE = 100;

    private SurfaceView localVideoView;
    private SurfaceView remoteVideoView;
    private Button addNoteButton;
    private TextView patientNameTextView;
    private TextView patientMedicalHistoryTextView;

    private ImageView backButtonVideoConference;



    public Frag_VideoConference() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_VideoConference.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_VideoConference newInstance(String param1, String param2) {
        Frag_VideoConference fragment = new Frag_VideoConference();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_conference, container, false);



        localVideoView = view.findViewById(R.id.localVideoView);
        remoteVideoView = view.findViewById(R.id.remoteVideoView);
        addNoteButton = view.findViewById(R.id.addNoteButton);
        patientNameTextView = view.findViewById(R.id.patientNameTextView);
        patientMedicalHistoryTextView = view.findViewById(R.id.patientMedicalHistoryTextView);
        backButtonVideoConference = view.findViewById(R.id.backButtonVideoConference);

        // Check and request camera permission
        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        } else
        {
            // Initialize video conferencing components
            initializeVideoConference();
        }


        addNoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(),Activity_NoteTaking.class);
                startActivity(intent);
            }
        });

        backButtonVideoConference.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Frag_Remote_Consultation fragRemoteConsultation = new Frag_Remote_Consultation();
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragRemoteConsultation).commit();
            }
        });


        return view;
    }



    // Initialize your video conferencing components here
    private void initializeVideoConference()
    {
        // Initialize camera, connect to video streams, etc.
    }





    // Handle permission request results
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                initializeVideoConference();
            } else
            {
                // Handle permission denial
            }
        }
    }



}