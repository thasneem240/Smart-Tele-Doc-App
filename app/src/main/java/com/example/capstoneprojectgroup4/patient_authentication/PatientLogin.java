package com.example.capstoneprojectgroup4.patient_authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.A_Patient_Or_A_Doctor;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientLogin#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientLogin extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentManager fm;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    public PatientLogin() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientLogin.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientLogin newInstance(String param1, String param2) {
        PatientLogin fragment = new PatientLogin();
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
        View v = inflater.inflate(R.layout.fragment_patient_login, container, false);
        Button login = v.findViewById(R.id.login_button);
        TextView signup = v.findViewById(R.id.sign_up_link);
        EditText email_ = v.findViewById(R.id.EditText_Doctor_Enter_Reg_Layout);
        EditText password_ = v.findViewById(R.id.EditText_Doctor_Enter_Password);
        ImageView backButton = v.findViewById(R.id.backButton);
     //   ImageView hidePassword = v.findViewById(R.id.ImageView_LoginEye);

        fm = getActivity().getSupportFragmentManager();
        mAuth = FirebaseAuth.getInstance();

        password_.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        password_.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_.getText().toString();
                String password = password_.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    currentUser = mAuth.getCurrentUser();

                                    if(currentUser.isEmailVerified()){
                                        isPatientProfileCompleted();
                                    }
                                    else{
                                        Toast.makeText(getActivity(), "Please verify your email", Toast.LENGTH_SHORT).show();

                                        Signup_EmailVerificationF signup_emailVerificationF = new Signup_EmailVerificationF();
                                        fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, signup_emailVerificationF).commit();
                                    }
                                } else {
                                    Toast.makeText(requireContext(), "Login failed. " + task.getException(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PatientSignUp patientSignUp = new PatientSignUp();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, patientSignUp).commit();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                A_Patient_Or_A_Doctor aPatientOrADoctor = new A_Patient_Or_A_Doctor();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, aPatientOrADoctor).commit();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                A_Patient_Or_A_Doctor aPatientOrADoctor = new A_Patient_Or_A_Doctor();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, aPatientOrADoctor).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return v;
    }

    public void isPatientProfileCompleted(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child(currentUser.getUid());

        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue(PatientObject.class).isCompleted()){
                    startActivity(new Intent(getActivity(), MainActivity2.class));
                }
                else{
                    Toast.makeText(getActivity(), "Please fill in all the required details.", Toast.LENGTH_SHORT).show();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    AccountSettings accountSettings = new AccountSettings();
                    fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, accountSettings).commit();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error while loading the database. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}