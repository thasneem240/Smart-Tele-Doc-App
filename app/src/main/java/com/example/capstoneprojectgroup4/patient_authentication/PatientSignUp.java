package com.example.capstoneprojectgroup4.patient_authentication;

import static com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF.TAG;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientSignUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientSignUp extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String email;
    String password;
    String passwordRepeat;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    public PatientSignUp() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientSignUp.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientSignUp newInstance(String param1, String param2) {
        PatientSignUp fragment = new PatientSignUp();
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
        View v = inflater.inflate(R.layout.fragment_patient_sign_up, container, false);
        Button signUp = v.findViewById(R.id.sign_up_button);
        TextView login = v.findViewById(R.id.login_link);
        ImageView backButton = v.findViewById(R.id.ImageView_SignupBack);
        EditText enterEmail = v.findViewById(R.id.EditText_EnterEmail);
        EditText enterPassword = v.findViewById(R.id.EditText_EnterPassword);
        EditText reEnterPassword = v.findViewById(R.id.EditText_ReEnterPassword);
        CheckBox termsConditions = v.findViewById(R.id.CheckBox_Terms);

        mAuth = FirebaseAuth.getInstance();

        enterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        reEnterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PatientLogin patientLogin = new PatientLogin();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, patientLogin).commit();
            }
        });


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = enterEmail.getText().toString();
                password = enterPassword.getText().toString();
                passwordRepeat = reEnterPassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length() < 6){
                    Toast.makeText(getActivity(), "Length of the password should be more than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(passwordRepeat)){
                    Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!termsConditions.isChecked()){
                    Toast.makeText(getActivity(), "Please indicate that you accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getActivity(), "Please verify your email.", Toast.LENGTH_SHORT).show();

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        Signup_EmailVerificationF signup_emailVerificationF = new Signup_EmailVerificationF();
                        fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, signup_emailVerificationF).commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PatientLogin patientLogin = new PatientLogin();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, patientLogin).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PatientLogin patientLogin = new PatientLogin();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, patientLogin).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getActivity(), callback);


        return v;
    }
}