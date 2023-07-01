package com.example.capstoneprojectgroup4.authentication;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

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
    private FirebaseAuth mAuth; // comment 1
    FirebaseFirestore db;
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
        db = FirebaseFirestore.getInstance();
        currentUser = mAuth.getCurrentUser();

        TextView fullName_ = (TextView) v.findViewById(R.id.fullname_edittext);
        TextView nic_ = (TextView) v.findViewById(R.id.nic_edittext);
        TextView email_ = (TextView) v.findViewById(R.id.email_edittext);
        TextView mobileNumber_ = (TextView) v.findViewById(R.id.mobilenumber_edittext);
        TextView password_ = (TextView) v.findViewById(R.id.password_edittext);
        TextView rePassword_ = (TextView) v.findViewById(R.id.repassword_edittext);
        CheckBox checkBox = v.findViewById(R.id.terms);
        ProgressBar progressBar = v.findViewById(R.id.prgressbar);
        Button register = v.findViewById(R.id.register_button);
        Button homeButton = v.findViewById(R.id.home_button);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()){
                    Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getActivity(), "Unchecked ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = email_.getText().toString();
                String password = password_.getText().toString();
                String fullName = fullName_.getText().toString();
                String nic = nic_.getText().toString();
                String mobileNumber = mobileNumber_.getText().toString();
//                progressBar.setVisibility(View.VISIBLE);

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(fullName)){
                    Toast.makeText(getActivity(), "Please enter the Full name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(nic)){
                    Toast.makeText(getActivity(), "Please enter the nic", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(mobileNumber)){
                    Toast.makeText(getActivity(), "Please enter the mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(checkBox.isChecked()){

                }
                else{
                    Toast.makeText(getActivity(), "Please indicate that you accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("Full name", fullName);
                user.put("NIC", nic);
                user.put("Mobile number", mobileNumber);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

//                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Authentication successful.", Toast.LENGTH_SHORT).show();
                                    // Add a new document with a generated ID
                                    db.collection("users")
//                        .document(currentUser.getEmail())
                                            .document(email)
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getActivity(), "Successfully registered.", Toast.LENGTH_LONG).show();

                                                }
                                            });

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
                StartupPageTwoF startupPageTwoF = new StartupPageTwoF();
                fm.beginTransaction().replace(R.id.fragment_container, startupPageTwoF).commit();

            }
        });

        return v;
    }
}