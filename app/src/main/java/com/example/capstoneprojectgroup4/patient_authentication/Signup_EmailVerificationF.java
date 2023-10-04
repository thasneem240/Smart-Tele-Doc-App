package com.example.capstoneprojectgroup4.patient_authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Signup_EmailVerificationF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Signup_EmailVerificationF extends Fragment {

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

    public Signup_EmailVerificationF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Signup_EmailVerificationF.
     */
    // TODO: Rename and change types and number of parameters
    public static Signup_EmailVerificationF newInstance(String param1, String param2) {
        Signup_EmailVerificationF fragment = new Signup_EmailVerificationF();
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
        View v = inflater.inflate(R.layout.fragment_signup__email_verification, container, false);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        Button emailVarificationButton = v.findViewById(R.id.email_verification_button);
        Button next = v.findViewById(R.id.next_button2);


        emailVarificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(), "The email has been sent successfully. Please verify your email.", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentUser.reload().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        if(currentUser.isEmailVerified()){

                            addingEmailToTheUserDetails();

                        }
                        else{
                            Toast.makeText(getActivity(), "Please verify your email", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return v;
    }
    private void addingEmailToTheUserDetails(){
        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PatientObject patientObject = new PatientObject();

                patientObject.setEmail(currentUser.getEmail());

                DatabaseReference myRef = database.getReference("Users").child(currentUser.getUid());

                myRef.setValue(patientObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Your email is verified.", Toast.LENGTH_SHORT).show();
                        userDetailsOrMainMenu(); // Check whether user details filled and open the Account settings

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Updating cannot be completed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error while loading the user details.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void userDetailsOrMainMenu(){
        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PatientObject patientObject = snapshot.getValue(PatientObject.class);

                if(patientObject.isCompleted()){
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