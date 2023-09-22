package com.example.capstoneprojectgroup4.front_end;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.authentication.PatientObject;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountSettings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountSettings extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextInputEditText firstNameEditText, lastNameEditText,
    nicEditText, dobEditText, genderEditText, mobileEditText,
    heightEditText, weightEditText, countryEditText, cityEditText, addressEditText;
    Button logoutButton;
    Button updateButton;
    String firstName, lastName, nic, dob, gender, mobileNumber, height, weight, country, city, address;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;

    public AccountSettings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountSettings.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountSettings newInstance(String param1, String param2) {
        AccountSettings fragment = new AccountSettings();
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
        View v = inflater.inflate(R.layout.fragment_account_settings, container, false);

        firstNameEditText = v.findViewById(R.id.EditText_FirstName);
        lastNameEditText = v.findViewById(R.id.EditText_LastName);
        nicEditText = v.findViewById(R.id.EditText_Nic);
        dobEditText = v.findViewById(R.id.EditText_Dob);
        genderEditText = v.findViewById(R.id.EditText_Gender);
        mobileEditText = v.findViewById(R.id.EditText_MobileNumber);
        heightEditText = v.findViewById(R.id.EditText_Height);
        weightEditText = v.findViewById(R.id.EditText_Weight);
        countryEditText = v.findViewById(R.id.EditText_Country);
        cityEditText = v.findViewById(R.id.EditText_City);
        addressEditText = v.findViewById(R.id.EditText_Address);
        updateButton = v.findViewById(R.id.Button_Update);
        logoutButton = v.findViewById(R.id.Button_Logout);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                PatientObject patientObject = snapshot.getValue(PatientObject.class);

                firstNameEditText.setText(patientObject.getFirstName());
                lastNameEditText.setText(patientObject.getLastName());
                nicEditText.setText(patientObject.getNic());
                dobEditText.setText(patientObject.getDob());
                genderEditText.setText(patientObject.getGender());
                mobileEditText.setText(patientObject.getMobile());
                heightEditText.setText(patientObject.getHeight());
                weightEditText.setText(patientObject.getWeight());
                countryEditText.setText(patientObject.getCountry());
                cityEditText.setText(patientObject.getCity());
                countryEditText.setText(patientObject.getCountry());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error while loading the user details.", Toast.LENGTH_SHORT).show();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setEditTextEnable(false);

                PatientObject patientObject = new PatientObject();
                patientObject.setFirstName(firstNameEditText.getText().toString());
                patientObject.setLastName(lastNameEditText.getText().toString());
                patientObject.setNic(nicEditText.getText().toString());
                patientObject.setDob(dobEditText.getText().toString());
                patientObject.setGender(genderEditText.getText().toString());
                patientObject.setMobile(mobileEditText.getText().toString());
                patientObject.setHeight(heightEditText.getText().toString());
                patientObject.setWeight(weightEditText.getText().toString());
                patientObject.setCountry(countryEditText.getText().toString());
                patientObject.setCity(cityEditText.getText().toString());
                patientObject.setAddress(addressEditText.getText().toString());

                setEditTextEnable(true);

                DatabaseReference myRef = database.getReference("Users").child(currentUser.getUid());

                myRef.setValue(patientObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Updating cannot be completed.", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            private void setEditTextEnable(boolean enable){
                firstNameEditText.setEnabled(enable);
                lastNameEditText.setEnabled(enable);
                nicEditText.setEnabled(enable);
                dobEditText.setEnabled(enable);
                genderEditText.setEnabled(enable);
                mobileEditText.setEnabled(enable);
                heightEditText.setEnabled(enable);
                weightEditText.setEnabled(enable);
                countryEditText.setEnabled(enable);
                cityEditText.setEnabled(enable);
                addressEditText.setEnabled(enable);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                if(mAuth.getCurrentUser() == null){
                    Toast.makeText(getActivity(), "Successfully Logged-out", Toast.LENGTH_SHORT).show();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    PatientLogin patientLogin = new PatientLogin();
                    fm.beginTransaction().replace(R.id.fragmentContainerView, patientLogin).commit();
                }
                else{
                    Toast.makeText(getActivity(), "Logging-out failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}