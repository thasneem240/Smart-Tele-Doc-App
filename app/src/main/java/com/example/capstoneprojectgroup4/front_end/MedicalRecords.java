package com.example.capstoneprojectgroup4.front_end;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.Frag_LabReports;
import com.example.capstoneprojectgroup4.Frag_MedicalHistory;
import com.example.capstoneprojectgroup4.Frag_PatientDetails;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.prescriptions.view_prescriptions.ViewPrescriptionsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MedicalRecords#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MedicalRecords extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public MedicalRecords() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MedicalRecords.
     */
    // TODO: Rename and change types and number of parameters
    public static MedicalRecords newInstance(String param1, String param2) {
        MedicalRecords fragment = new MedicalRecords();
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
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_medical_records, container, false);
        Button doctorButton = v.findViewById(R.id.Button_UploadPrescription);

        ImageView backButton = v.findViewById(R.id.backButtonMedicalRecords);

        Button patientDetailButton = v.findViewById(R.id.button1);
        ImageView medicalHistoryImageView = v.findViewById(R.id.medicalHistoryImageView);
        Button medicalHistoryButton = v.findViewById(R.id.button2);
        ImageView labReportsImageView = v.findViewById(R.id.labReportsImageView);
        Button labReportsButton = v.findViewById(R.id.button3);
        ImageView prescriptionsImageView = v.findViewById(R.id.square5);
        Button prescriptionsButton = v.findViewById(R.id.Button_UploadPrescription);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fm = getActivity().getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PrescriptionWriting searchDoctors = new PrescriptionWriting();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();            }
        });



        patientDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Frag_PatientDetails fragPatientDetails = new Frag_PatientDetails();

                fm.beginTransaction().replace(R.id.fragmentContainerView, fragPatientDetails).commit();
            }
        });


        medicalHistoryImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Frag_MedicalHistory fragMedicalHistory = new Frag_MedicalHistory();

                fm.beginTransaction().replace(R.id.fragmentContainerView, fragMedicalHistory).commit();
            }
        });
        medicalHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Frag_MedicalHistory fragMedicalHistory = new Frag_MedicalHistory();

                fm.beginTransaction().replace(R.id.fragmentContainerView, fragMedicalHistory).commit();
            }
        });


        labReportsImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Frag_LabReports fragLabReports = new Frag_LabReports();

                fm.beginTransaction().replace(R.id.fragmentContainerView, fragLabReports).commit();
            }
        });
        labReportsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Frag_LabReports fragLabReports = new Frag_LabReports();

                fm.beginTransaction().replace(R.id.fragmentContainerView, fragLabReports).commit();
            }
        });
        prescriptionsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ViewPrescriptionsFragment viewPrescriptionsFragment = new ViewPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, viewPrescriptionsFragment).commit();
            }
        });
        prescriptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ViewPrescriptionsFragment viewPrescriptionsFragment = new ViewPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, viewPrescriptionsFragment).commit();
            }
        });





        // Inflate the layout for this fragment
        return v;
    }
}