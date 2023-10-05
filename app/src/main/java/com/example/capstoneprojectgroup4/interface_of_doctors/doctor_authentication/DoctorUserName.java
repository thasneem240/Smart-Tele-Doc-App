package com.example.capstoneprojectgroup4.interface_of_doctors.doctor_authentication;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorSignupObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorUserName#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorUserName extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String regNumber;

    public DoctorUserName() {
        // Required empty public constructor
    }
    public DoctorUserName(String regNumber){
        this.regNumber = regNumber;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorUserName.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorUserName newInstance(String param1, String param2) {
        DoctorUserName fragment = new DoctorUserName();
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
        View v = inflater.inflate(R.layout.fragment_doctor_user_name, container, false);

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("doctors");

        EditText regNumber_edittext = v.findViewById(R.id.EditText_RegistrationNumber);
        EditText mobileNumber_edittext = v.findViewById(R.id.EditText_Specialization);
        EditText password_edittext = v.findViewById(R.id.EditText_EnterPassword);
        EditText reEnterPassword_edittext = v.findViewById(R.id.EditText_ReEnterPassword);
        CheckBox termsCheckBox = v.findViewById(R.id.CheckBox_Terms);
        Button signupButton = v.findViewById(R.id.Button_SignUp);
        ImageView backButton = v.findViewById(R.id.ImageView_SignupBack);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        regNumber_edittext.setText(regNumber);
        regNumber_edittext.setEnabled(false);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = password_edittext.getText().toString();
                String rePassword = reEnterPassword_edittext.getText().toString();

                if(password.length() < 6){
                    Toast.makeText(getActivity(), "Length of the password should be more than 6 characters", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(rePassword)){
                    Toast.makeText(getActivity(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!termsCheckBox.isChecked()){
                    Toast.makeText(getActivity(), "Please indicate that you accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                DoctorSignupObject doctorSignupObject = new DoctorSignupObject();

                byte[] encodedBytes = Base64.encode(password.getBytes(), Base64.DEFAULT);

                String encodedPassword = new String(encodedBytes);

                doctorSignupObject.setPassword(new String(encodedBytes));

//                Map<String, DoctorSignupObject> doctorSignupHashmap = new HashMap<>();
//                doctorSignupHashmap.put(regNumber, doctorSignupObject);

                databaseReference.child(regNumber).child("Registered").setValue(doctorSignupObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_SHORT).show();

                        openMainMenu(regNumber);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Database error. Try again.", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorVerifyRegNumber doctorVerifyRegNumber = new DoctorVerifyRegNumber();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, doctorVerifyRegNumber).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DoctorVerifyRegNumber doctorVerifyRegNumber = new DoctorVerifyRegNumber();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, doctorVerifyRegNumber).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return v;
    }

    private void openMainMenu(String regNumber){
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(regNumber);

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