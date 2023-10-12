package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorViewPatientProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorViewPatientProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorViewPatientProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorViewPatientProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorViewPatientProfile newInstance(String param1, String param2) {
        DoctorViewPatientProfile fragment = new DoctorViewPatientProfile();
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

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        View v = inflater.inflate(R.layout.fragment_doctor_view_patient_profile, container, false);

        TextView firstName = v.findViewById(R.id.textView_firstName_dpp);
        TextView lastName = v.findViewById(R.id.textView_lastName_dpp);
        TextView dateOfBirth = v.findViewById(R.id.textView_dob_dpp);
        TextView gender = v.findViewById(R.id.textView_gender_dpp);
        TextView height = v.findViewById(R.id.textView_height_dpp);
        TextView weight = v.findViewById(R.id.textView_weight_dpp);
        ProgressBar progressBar = v.findViewById(R.id.progressBar_dpp);
        ImageView backButton = v.findViewById(R.id.button_back_dpp);
        Button goBack = v.findViewById(R.id.button_goBack);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(DoctorsActivity.getAppointmentObject().getPatientUserId());

        FragmentManager fm = getActivity().getSupportFragmentManager();

        progressBar.setVisibility(View.VISIBLE);

        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.INVISIBLE);

                PatientObject patientObject = dataSnapshot.getValue(PatientObject.class);

                firstName.setText(patientObject.getFirstName());
                lastName.setText(patientObject.getLastName());
                dateOfBirth.setText(patientObject.getDob());
                gender.setText(patientObject.getGender());
                height.setText(patientObject.getHeight());
                weight.setText(patientObject.getWeight());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error in loading database. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();
            }
        });

        return v;
    }
}