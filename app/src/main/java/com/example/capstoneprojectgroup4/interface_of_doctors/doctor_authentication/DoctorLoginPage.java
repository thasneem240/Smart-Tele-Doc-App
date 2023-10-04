package com.example.capstoneprojectgroup4.interface_of_doctors.doctor_authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.A_Patient_Or_A_Doctor;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorSignupObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorLoginPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorLoginPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorLoginPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorLoginPage.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorLoginPage newInstance(String param1, String param2) {
        DoctorLoginPage fragment = new DoctorLoginPage();
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
        View v = inflater.inflate(R.layout.fragment_doctor_login_page, container, false);

        EditText regNumber_edittext = v.findViewById(R.id.EditText_RegNumber);
        EditText password_edittext = v.findViewById(R.id.EditText_EnterPassword);
        Button login_button = v.findViewById(R.id.Button_Login);
        ImageView back_button = v.findViewById(R.id.ImageView_backButton);
        TextView signup_textview = v.findViewById(R.id.sign_up_link);

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("doctors");

        FragmentManager fm = getActivity().getSupportFragmentManager();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regNumber = regNumber_edittext.getText().toString();
                String password = password_edittext.getText().toString();

                if(TextUtils.isEmpty(regNumber)){
                    Toast.makeText(getActivity(), "Please enter the registration number", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }

                databaseReference.child(regNumber).child("Registered").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() == null){
                            Toast.makeText(getActivity(), "This ID has not yet been signed up. Please sign up first.", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            DoctorSignupObject doctorSignupObject = dataSnapshot.getValue(DoctorSignupObject.class);

                            byte[] decodedBytes = Base64.decode(doctorSignupObject.getPassword(), Base64.DEFAULT);
                            String decodedString = new String(decodedBytes);

                            if(password.equals(decodedString)){
                                Toast.makeText(getActivity(), "You have logged in successfully.", Toast.LENGTH_SHORT).show();

                                openMainMenu(regNumber);

                            }
                            else{
                                Toast.makeText(getActivity(), "Incorrect password.", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error in the database. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorVerifyRegNumber doctorVerifyRegNumber = new DoctorVerifyRegNumber();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, doctorVerifyRegNumber).commit();
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                A_Patient_Or_A_Doctor patientOrADoctor = new A_Patient_Or_A_Doctor();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, patientOrADoctor).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                A_Patient_Or_A_Doctor patientOrADoctor = new A_Patient_Or_A_Doctor();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, patientOrADoctor).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return v;
    }

    private void openMainMenu(String regNumber){
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("doctors").child(regNumber);

        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                DoctorObject doctorObject = dataSnapshot.getValue(DoctorObject.class);

                DoctorsActivity.setDoctorRegNumber(regNumber);
                DoctorsActivity.setDoctorObject(doctorObject);

                Intent doctorsActivity = new Intent(getActivity(), DoctorsActivity.class);
                startActivity(doctorsActivity);
            }
        }). addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error in the database. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}