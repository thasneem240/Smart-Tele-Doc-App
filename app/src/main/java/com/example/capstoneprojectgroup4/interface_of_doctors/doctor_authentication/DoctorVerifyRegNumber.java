package com.example.capstoneprojectgroup4.interface_of_doctors.doctor_authentication;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorVerifyRegNumber#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorVerifyRegNumber extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseDatabase database;

    public DoctorVerifyRegNumber() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Registratin_RegistrationNumber.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorVerifyRegNumber newInstance(String param1, String param2) {
        DoctorVerifyRegNumber fragment = new DoctorVerifyRegNumber();
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
        View v = inflater.inflate(R.layout.fragment_doctor_registration_number, container, false);

        DatabaseReference myRef;

        EditText regNum_edittext = v.findViewById(R.id.EditText_RegistrationNumber);
        Button signUpButton = v.findViewById(R.id.Button_SignUp);
        TextView loginLink = v.findViewById(R.id.login_link);
        ImageView backButton = v.findViewById(R.id.ImageView_SignupBack);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("doctors");

        FragmentManager fm = getActivity().getSupportFragmentManager();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regNumber = regNum_edittext.getText().toString();

                myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild(regNumber)){
                            checkAlreadyRegistered(regNumber);

                        }
                        else{
                            Toast.makeText(getActivity(), "Please check you registration number again "+regNumber, Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Database error. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorLoginPage doctorLoginPage = new DoctorLoginPage();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, doctorLoginPage).commit();            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorLoginPage doctorLoginPage = new DoctorLoginPage();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, doctorLoginPage).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                DoctorLoginPage doctorLoginPage = new DoctorLoginPage();
                fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, doctorLoginPage).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return v;
    }
    public void checkAlreadyRegistered(String regNumber){
        DatabaseReference myRef;

        myRef = database.getReference("doctors").child(regNumber);

        myRef.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild("Registered")){
                    Toast.makeText(getActivity(), "This ID has already been registered", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getActivity(), "Valid registration number", Toast.LENGTH_SHORT).show();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DoctorUserName doctorUserName = new DoctorUserName(regNumber);
                    fm.beginTransaction().replace(R.id.FragmentContainer_MainActivity, doctorUserName).commit();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Database error. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}