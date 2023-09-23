package com.example.capstoneprojectgroup4.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.signup.Signup_EmailVerificationF;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.front_end.PatientLogin;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.WritingPrescriptionActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link A_Patient_Or_A_Doctor#newInstance} factory method to
 * create an instance of this fragment.
 */
public class A_Patient_Or_A_Doctor extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public A_Patient_Or_A_Doctor() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment A_Patient_Or_A_Doctor.
     */
    // TODO: Rename and change types and number of parameters
    public static A_Patient_Or_A_Doctor newInstance(String param1, String param2) {
        A_Patient_Or_A_Doctor fragment = new A_Patient_Or_A_Doctor();
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

        View v = inflater.inflate(R.layout.fragment_a__patient__or__a__doctor, container, false);
        Button patientInterfaceButton = v.findViewById(R.id.Patient_login_button);
        Button doctorInterfaceButton = v.findViewById(R.id.Doctor_login_button);

        patientInterfaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PatientLogin patientLogin = new PatientLogin();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, patientLogin).commit();

            }
        });
        doctorInterfaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prescriptionActivity = new Intent(getActivity(), WritingPrescriptionActivity.class);
                startActivity(prescriptionActivity);
            }
        });

        return v;
    }
}