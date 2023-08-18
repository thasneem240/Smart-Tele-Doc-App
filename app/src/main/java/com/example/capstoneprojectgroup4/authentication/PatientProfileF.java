package com.example.capstoneprojectgroup4.authentication;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.prescriptions.PrescriptionObject;
import com.example.capstoneprojectgroup4.prescriptions.view_prescriptions.ViewPrescriptionsAdapter;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.WritingPrescriptionActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientProfileF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientProfileF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser currentUser;
    EditText fullName_, nic_, mobileNumber_, dob_, gender_;
    TextView edit, done;
    Button homeButton, updateButton;
    public PatientProfileF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientProfileF.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientProfileF newInstance(String param1, String param2) {
        PatientProfileF fragment = new PatientProfileF();
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
        View v = inflater.inflate(R.layout.fragment_patient_profile, container, false);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();
        edit = v.findViewById(R.id.p_edit);
        done = v.findViewById(R.id.p_done);
        done.setVisibility(v.GONE);

        fullName_ = v.findViewById(R.id.p_fullname_edittext);
        nic_ = v.findViewById(R.id.p_nic_edittext);
        mobileNumber_ = v.findViewById(R.id.p_mobilenumber_edittext);
        dob_ = v.findViewById(R.id.p_dob_edittext);
        gender_ = v.findViewById(R.id.p_gender_edittext);
        homeButton = v.findViewById(R.id.p_home);
        updateButton = v.findViewById(R.id.p_update);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Patient").child(currentUser.getUid());
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PatientObject patientObject = snapshot.getValue(PatientObject.class);

                fullName_.setText(patientObject.getFullName());
                nic_.setText(patientObject.getNic());
                mobileNumber_.setText(patientObject.getMobileNumber());
                dob_.setText(patientObject.getDob());
                gender_.setText(patientObject.getGender());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error adding document: "+ error);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName_.setEnabled(true);
                nic_.setEnabled(true);
                mobileNumber_.setEnabled(true);
                dob_.setEnabled(true);
                gender_.setEnabled(true);

                done.setVisibility(v.VISIBLE);
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fullName_.setEnabled(false);
                nic_.setEnabled(false);
                mobileNumber_.setEnabled(false);
                dob_.setEnabled(false);
                gender_.setEnabled(false);
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                AuthenticationHomeF startupFormF = new AuthenticationHomeF();
                fm.beginTransaction().replace(R.id.fragment_container, startupFormF).commit();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullName = fullName_.getText().toString();
                String nic = nic_.getText().toString();
                String mobileNumber = mobileNumber_.getText().toString();
                String dob = dob_.getText().toString();
                String gender = gender_.getText().toString();

                PatientObject patientObject = new PatientObject();
                patientObject.setFullName(fullName);
                patientObject.setNic(nic);
                patientObject.setMobileNumber(mobileNumber);
                patientObject.setDob(dob);
                patientObject.setGender(gender);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Patient").child(currentUser.getUid());

                myRef.setValue(patientObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        MainActivity mainActivity2 = (MainActivity) getActivity();
                        mainActivity2.setPatientObject(patientObject);

                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                        startActivity(mainActivity);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding data", e);
                    }
                });
            }
        });

        return v;
    }
}