package com.example.capstoneprojectgroup4;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.example.capstoneprojectgroup4.home.A_Patient_Or_A_Doctor;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.search_doctors.AppointmentItem;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_Remote_Consultation#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Remote_Consultation extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText patientNameEditText;
    private ImageView backButtonRemoteCons;
    private AppointmentItem appointmentItem;

    public Frag_Remote_Consultation()
    {
        // Required empty public constructor
    }

    public Frag_Remote_Consultation(AppointmentItem appointmentItem)
    {
        this.appointmentItem = appointmentItem;
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Remote_Consultation.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_Remote_Consultation newInstance(String param1, String param2) {
        Frag_Remote_Consultation fragment = new Frag_Remote_Consultation();
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
        View view = inflater.inflate(R.layout.fragment_remote_consultation, container, false);

        Button videoConferenceButton = view.findViewById(R.id.videoConferenceButton);
        patientNameEditText = view.findViewById(R.id.patientNameEditText);
        backButtonRemoteCons = view.findViewById(R.id.backButtonRemoteCons);


        String patientName = MainActivity.getPatientObject().getFirstName();
        String appointmentType = appointmentItem.getAppointmentType();

        // patientNameEditText.setText("A.S.M. Thasneem");
        patientNameEditText.setText(patientName);

        if(appointmentType.equalsIgnoreCase("Voice"))
        {
            videoConferenceButton.setText("Start Audio Conference");
        }


        videoConferenceButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Handle the video conference button click event here
                // You can start the video conference activity or initiate the call.

//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                Frag_VideoConference fragVideoConference = new Frag_VideoConference();
//                fm.beginTransaction().replace(R.id.fragmentContainerView, fragVideoConference).commit();


//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                Frag_AgoraVideoConference videoConference = new Frag_AgoraVideoConference(1);
//                fm.beginTransaction().replace(R.id.fragmentContainerView, videoConference).commit();


                // Audio Conference
                if(appointmentType.equalsIgnoreCase("Voice"))
                {
                    startAudioConference();
                }
                else
                {
                    startVideoConference();
                }

            }
        });








        backButtonRemoteCons.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                MedicalRecords medicalRecords = new MedicalRecords();
//                fm.beginTransaction().replace(R.id.fragmentContainerView, medicalRecords).commit();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                ViewAppointments viewAppointments = new ViewAppointments();
                fm.beginTransaction().replace(R.id.fragmentContainerView, viewAppointments).commit();

            }
        });



        return view;
    }


    private void startVideoConference()
    {
        // Create an Intent to specify the target activity
        Intent intent = new Intent(getActivity(), Activity_Agora_VideoConference.class);

        // Optionally, add data to the Intent using key-value pairs
        intent.putExtra("userType", "Patient");

        // Start the target activity using the Intent
        startActivity(intent);
    }



    private void startAudioConference()
    {
        // Create an Intent to specify the target activity
        Intent intent = new Intent(getActivity(), Activity_Agora_AudioConference.class);

        // Optionally, add data to the Intent using key-value pairs
        intent.putExtra("userType", "Patient");

        // Start the target activity using the Intent
        startActivity(intent);
    }






}