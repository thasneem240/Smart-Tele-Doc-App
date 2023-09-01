package com.example.capstoneprojectgroup4.authentication.signup;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.AuthenticationHomeF;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignupF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignupF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;

    public SignupF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SignupF.
     */
    // TODO: Rename and change types and number of parameters
    public static SignupF newInstance(String param1, String param2) {
        SignupF fragment = new SignupF();
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
        View v = inflater.inflate(R.layout.fragment_signup, container, false);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        TextView email_ = (TextView) v.findViewById(R.id.email_edittext);
        TextView password_ = (TextView) v.findViewById(R.id.password_edittext);
        TextView rePassword_ = (TextView) v.findViewById(R.id.repassword_edittext);
        Button nextButton = v.findViewById(R.id.next_button);
        Button homeButton = v.findViewById(R.id.home_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_.getText().toString();
                String password = password_.getText().toString();
                String reEnterPassword = rePassword_.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(reEnterPassword)){
                    Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Authentication successful.", Toast.LENGTH_SHORT).show();

                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    Signup_EmailVerificationF signup_emailVerificationF = new Signup_EmailVerificationF();
                                    fm.beginTransaction().replace(R.id.fragment_container, signup_emailVerificationF).commit();

                                } else {
                                    Toast.makeText(getActivity(), "Authentication failed.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
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

        return v;
    }
}