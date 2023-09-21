package com.example.capstoneprojectgroup4.front_end;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.StartupF;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    FirebaseAuth auth;

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
        View v = inflater.inflate(R.layout.fragment_patient_login, container, false); //these lines give errors
        Button login = v.findViewById(R.id.login_button); //these lines give errors
        TextView signup = v.findViewById(R.id.sign_up_link); //these lines give errors
        EditText email_ = v.findViewById(R.id.loginenter_email); //these lines give errors
        EditText password_ = v.findViewById(R.id.enter_password); //these lines give errors

        auth = FirebaseAuth.getInstance();
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

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Login successful.", Toast.LENGTH_SHORT).show();

                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    MainMenu mainMenu = new MainMenu();
                                    fm.beginTransaction().replace(R.id.fragmentContainerView, mainMenu).commit();


                                } else {
                                    Toast.makeText(getActivity(), "Login failed. "+task.getException(), Toast.LENGTH_LONG).show();

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
                fm.beginTransaction().replace(R.id.fragmentContainerView, patientSignUp).commit();
            }
        });

        return v;
    }
}