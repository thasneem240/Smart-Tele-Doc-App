package com.example.capstoneprojectgroup4.front_end;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.PatientObject;
import com.example.capstoneprojectgroup4.authentication.Signup_EmailVerificationF;
import com.example.capstoneprojectgroup4.home.A_Patient_Or_A_Doctor;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartUpFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;

    public StartUpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StartUpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StartUpFragment newInstance(String param1, String param2) {
        StartUpFragment fragment = new StartUpFragment();
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
        View v = inflater.inflate(R.layout.fragment_start_up, container, false);
        Button startButton = v.findViewById(R.id.Startbutton);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(currentUser == null){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    A_Patient_Or_A_Doctor a_patient_or_a_doctor = new A_Patient_Or_A_Doctor();
                    fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, a_patient_or_a_doctor).commit();
                }
                else{
                    if(currentUser.isEmailVerified()){
                        userDetailsOrMainMenu();
                    }
                    else {
                        Toast.makeText(getActivity(), "You have already signed-in using "+currentUser.getEmail()+"\nPlease verify your email.", Toast.LENGTH_SHORT).show();

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        Signup_EmailVerificationF signupEmailVerificationF = new Signup_EmailVerificationF();
                        fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, signupEmailVerificationF).commit();
                    }

                }
            }
        });

        return v;
    }
    private void userDetailsOrMainMenu(){
        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PatientObject patientObject = snapshot.getValue(PatientObject.class);


                if(patientObject.isCompleted()){
                    MainActivity.setPatientObject(patientObject);
                    startActivity(new Intent(getActivity(), MainActivity2.class));

                }
                else{
                    Toast.makeText(getActivity(), "Please fill in all the required details.", Toast.LENGTH_SHORT).show();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    AccountSettings accountSettings = new AccountSettings();
                    fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, accountSettings).commit();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error while loading the user details.", Toast.LENGTH_SHORT).show();
            }
        });}
}