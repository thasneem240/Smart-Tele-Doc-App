package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorPatientProfiles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorPatientProfiles extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorPatientProfiles() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorPatientProfiles.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorPatientProfiles newInstance(String param1, String param2) {
        DoctorPatientProfiles fragment = new DoctorPatientProfiles();
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
        View v= inflater.inflate(R.layout.fragment_doctor_patient_profiles, container, false);

        Button test = v.findViewById(R.id.button6);
        ImageView back = v.findViewById(R.id.backButtonDocPatProf);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Create_or_View_Prescription doctorAvailability = new Create_or_View_Prescription();
                fm.beginTransaction().replace(R.id.docmenufragmentContainer, doctorAvailability).commit();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMainMenu doctorAvailability = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.docmenufragmentContainer, doctorAvailability).commit();
            }
        });

        return v;
    }
}