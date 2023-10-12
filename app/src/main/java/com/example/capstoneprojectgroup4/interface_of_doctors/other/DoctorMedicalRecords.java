package com.example.capstoneprojectgroup4.interface_of_doctors.other;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.Frag_ListLabReports;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorViewPatientProfile;
import com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOfPatients_patientProfile.ListOfPatientsFragment2;
import com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOf_prescriptions.PrescriptionsListFragment2;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorMedicalRecords#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorMedicalRecords extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorMedicalRecords() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorMedicalRecords.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorMedicalRecords newInstance(String param1, String param2) {
        DoctorMedicalRecords fragment = new DoctorMedicalRecords();
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
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_doctor_medical_records, container, false);

        TextView patientName = v.findViewById(R.id.textView33);
        Button viewPrescriptionsButton = v.findViewById(R.id.button_ViewPrescriptionsDoc);
        ImageView viewPrescriptionsImage = v.findViewById(R.id.imageView_ViewPrescriptions);
        ImageView backButton = v.findViewById(R.id.backDoc);
        ImageView patientDetailsImage = v.findViewById(R.id.imageView_PatientDetails3);
        Button patientDetailsButton = v.findViewById(R.id.button12);


        patientName.setText(DoctorsActivity.getAppointmentObject().getPatientName()+"'s");

        FragmentManager fm = getActivity().getSupportFragmentManager();


        // Thasneem
        ImageView medicalHistoryImageView = v.findViewById(R.id.medicalHistoryImageView4);
        ImageView labReportsImageView = v.findViewById(R.id.labReportsImageView2);
        medicalHistoryImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });


        labReportsImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Frag_ListLabReports fragListLabReports = new Frag_ListLabReports("Doctor");

                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity,
                        fragListLabReports).commit();

            }
        });





        patientDetailsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorViewPatientProfile doctorViewPatientProfile = new DoctorViewPatientProfile();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorViewPatientProfile).commit();
            }
        });
        patientDetailsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorViewPatientProfile doctorViewPatientProfile = new DoctorViewPatientProfile();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorViewPatientProfile).commit();
            }
        });

        viewPrescriptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrescriptionsListFragment2 prescriptionsListFragment2 = new PrescriptionsListFragment2();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, prescriptionsListFragment2).commit();
            }
        });
        viewPrescriptionsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrescriptionsListFragment2 prescriptionsListFragment2 = new PrescriptionsListFragment2();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, prescriptionsListFragment2).commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListOfPatientsFragment2 listOfPatients = new ListOfPatientsFragment2();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatients).commit();
            }
        });



        return v;

    }



}