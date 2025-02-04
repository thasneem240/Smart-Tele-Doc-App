package com.example.capstoneprojectgroup4.front_end;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.Activity_Remote_Consultation;
import com.example.capstoneprojectgroup4.Frag_LabReports;
import com.example.capstoneprojectgroup4.Frag_MedicalHistory;
import com.example.capstoneprojectgroup4.Frag_Remote_Consultation;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.medical_records_prescriptions.prescriptions_list.PrescriptionsListFragment;
import com.example.capstoneprojectgroup4.patient_authentication.AccountSettings;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsFragment;

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

        ImageView backButton = v.findViewById(R.id.backButtonMedicalRecords);

        Button patientDetailButton = v.findViewById(R.id.button1);
        ImageView patientDetailsImageView = v.findViewById(R.id.ImageView_PatientDetails);
        ImageView medicalHistoryImageView = v.findViewById(R.id.medicalHistoryImageView);
        Button medicalHistoryButton = v.findViewById(R.id.button2);
        ImageView labReportsImageView = v.findViewById(R.id.labReportsImageView);
        Button labReportsButton = v.findViewById(R.id.button3);
        ImageView prescriptionsImageView = v.findViewById(R.id.ImageView_ViewPrescriptions);
        Button prescriptionsButton = v.findViewById(R.id.Button_ViewPrescriptions);
//        ImageView imageView_RemoteConsultation = v.findViewById(R.id.imageView_RemoteConsultation);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        patientDetailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AccountSettings accountSettings = new AccountSettings();

                fm.beginTransaction().replace(R.id.fragmentContainerView, accountSettings).commit();
            }
        });

        patientDetailsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AccountSettings accountSettings = new AccountSettings();

                fm.beginTransaction().replace(R.id.fragmentContainerView, accountSettings).commit();

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
                PrescriptionsListFragment prescriptionsListFragment = new PrescriptionsListFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, prescriptionsListFragment).commit();
            }
        });
        prescriptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PrescriptionsListFragment prescriptionsListFragment = new PrescriptionsListFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, prescriptionsListFragment).commit();
            }
        });


//        imageView_RemoteConsultation.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
////                Intent intent = new Intent(getActivity(), Activity_Remote_Consultation.class);
////                startActivity(intent);
//
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                Frag_Remote_Consultation fragRemoteConsultation = new Frag_Remote_Consultation();
//                fm.beginTransaction().replace(R.id.fragmentContainerView, fragRemoteConsultation).commit();
//
//
//
//            }
//        });




        // Inflate the layout for this fragment
        return v;
    }
}