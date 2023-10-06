package com.example.capstoneprojectgroup4.interface_of_doctors.other;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorPatientProfiles;

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

        Button patientProf = v.findViewById(R.id.button12);
        Button presc = v.findViewById(R.id.button_ViewPrescriptionsDoc);


        ImageView back = v.findViewById(R.id.backDoc);

        presc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        patientProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMedicalRecords doctorAvailability = new DoctorMedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorAvailability).commit();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorPatientProfiles doctorMainMenu = new DoctorPatientProfiles();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMainMenu).commit();
            }
        });



        return v;

    }
}