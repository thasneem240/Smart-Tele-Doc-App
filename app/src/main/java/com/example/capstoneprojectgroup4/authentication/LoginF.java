package com.example.capstoneprojectgroup4.authentication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private FirebaseAuth auth;


    public LoginF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Login.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginF newInstance(String param1, String param2) {
        LoginF fragment = new LoginF();
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
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        TextView email_name = (TextView) v.findViewById(R.id.email_name);
        TextView password_name = (TextView) v.findViewById(R.id.password_name);
        TextView email_ = (TextView) v.findViewById(R.id.email_edittext);
        TextView password_ = (TextView) v.findViewById(R.id.password_edittext);
        TextView signUpButton = (TextView) v.findViewById(R.id.signUp);

        Button login_button = (Button) v.findViewById(R.id.login_button);
        Button homeButton = (Button) v.findViewById(R.id.home_button);

        email_name.setText("Email");
        password_name.setText("Password");
        signUpButton.setText("Sign Up");

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                auth = FirebaseAuth.getInstance();


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


                                } else {
                                    Toast.makeText(getActivity(), "Login failed. "+task.getException(), Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SignupF signupF = new SignupF();
                fm.beginTransaction().replace(R.id.fragment_container, signupF).commit();
            }
        });

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                StartupPageTwoF startupPageTwoF = new StartupPageTwoF();
                fm.beginTransaction().replace(R.id.fragment_container, startupPageTwoF).commit();
            }
        });

        return v;
    }
}