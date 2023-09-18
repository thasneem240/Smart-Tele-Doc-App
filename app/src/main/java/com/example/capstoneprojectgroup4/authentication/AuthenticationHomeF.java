package com.example.capstoneprojectgroup4.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.signup.SignupF;
import com.example.capstoneprojectgroup4.authentication.signup.Signup_EmailVerificationF;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthenticationHomeF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthenticationHomeF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FragmentManager fm;
    Button patientButton;
    Button loginButton;
    Button signoutButton;
    Button patientProfileButton;
    Button emailVerification;
    FirebaseAuth mAuth;


    public AuthenticationHomeF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment startup_page.
     */
    // TODO: Rename and change types and number of parameters
    public static AuthenticationHomeF newInstance(String param1, String param2) {
        AuthenticationHomeF fragment = new AuthenticationHomeF();
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
        View v = inflater.inflate(R.layout.fragment_startup_page_two, container, false);

        patientButton = v.findViewById(R.id.patient_button);
        loginButton = v.findViewById(R.id.login_button);
        signoutButton = v.findViewById(R.id.signout_button);
        patientProfileButton = v.findViewById(R.id.patient_profile_button);
        emailVerification = v.findViewById(R.id.email_verification_button2);

        mAuth = FirebaseAuth.getInstance();

        patientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();

                FirebaseUser currentUser = mAuth.getCurrentUser();
                if(currentUser != null){
                    Toast.makeText(getActivity(), mAuth.getCurrentUser().getEmail()+" already signed in.", Toast.LENGTH_SHORT).show();
                    return;
                }

                fm = getActivity().getSupportFragmentManager();
                SignupF signupF = new SignupF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, signupF).commit();

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                LoginF LoginF = new LoginF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, LoginF).commit();
            }
        });

        signoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                SignoutF signoutF = new SignoutF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, signoutF).commit();
            }
        });

        patientProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                PatientProfileF patientProfileF = new PatientProfileF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, patientProfileF).commit();
            }
        });

        emailVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Signup_EmailVerificationF signup_emailVerificationF = new Signup_EmailVerificationF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, signup_emailVerificationF).commit();
            }
        });

        return v;
    }
}