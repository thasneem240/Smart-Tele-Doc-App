package com.example.capstoneprojectgroup4.patient_authentication;

import static com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
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
    private static final String PREFS_NAME = "ProfilePrefs";
    private static final String PROFILE_IMAGE_KEY = "profileImageResource";

    private int currentProfileImageResource = R.drawable.female_avatar; // Initialize with the default image

    TextInputEditText emailEditText, firstNameEditText, lastNameEditText,
    nicEditText, dobEditText, mobileEditText,
    heightEditText, weightEditText, countryEditText, cityEditText, addressEditText;
    Spinner genderSpinner;
    String selectedGender;
    ImageView profileImage;
    ImageView backButton;
    Button logoutButton;
    Button updateButton;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    PatientObject patientObjectOnline;

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
        profileImage =  v.findViewById(R.id.ProfileImage);
        emailEditText = v.findViewById(R.id.EditText_RegNumber);
        firstNameEditText = v.findViewById(R.id.EditText_DoctorName);
        lastNameEditText = v.findViewById(R.id.EditText_LastName);
        nicEditText = v.findViewById(R.id.EditText_Nic);
        dobEditText = v.findViewById(R.id.EditText_Dob);
        genderSpinner = v.findViewById(R.id.Spinner_Gender);
        mobileEditText = v.findViewById(R.id.EditText_Specialization);
        heightEditText = v.findViewById(R.id.EditText_Height);
        weightEditText = v.findViewById(R.id.EditText_Weight);
        countryEditText = v.findViewById(R.id.EditText_Country);
        cityEditText = v.findViewById(R.id.EditText_City);
        addressEditText = v.findViewById(R.id.EditText_Address);
        updateButton = v.findViewById(R.id.Button_Update);
        logoutButton = v.findViewById(R.id.Button_Logout);
        backButton = v.findViewById(R.id.ImageView_AccountSettings_backbutton);
        TextView greetings_textView = v.findViewById(R.id.textView4);

        greetings_textView.setText("Hi, " + MainActivity.getPatientObject().getFirstName());

        emailEditText.setEnabled(false);

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if(currentUser != null){
            database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    patientObjectOnline = snapshot.getValue(PatientObject.class);

                    if (patientObjectOnline.getGender().equals("Male")){
                        setProfilePicture("Male");
                    }
                    else {
                        setProfilePicture("Female");
                    }

                    emailEditText.setText(currentUser.getEmail());
                    firstNameEditText.setText(patientObjectOnline.getFirstName());
                    lastNameEditText.setText(patientObjectOnline.getLastName());
                    nicEditText.setText(patientObjectOnline.getNic());
                    dobEditText.setText(patientObjectOnline.getDob());
                    if(patientObjectOnline.getGender().equals("Male")){
                        String[] male = {"Male", "Female"};

                        ArrayAdapter<String> arrayAdapterBrands = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, male);
                        arrayAdapterBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genderSpinner.setAdapter(arrayAdapterBrands);
                    } else if (patientObjectOnline.getGender().equals("Female")) {
                        String[] female = {"Female", "Male"};

                        ArrayAdapter<String> arrayAdapterBrands = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, female);
                        arrayAdapterBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genderSpinner.setAdapter(arrayAdapterBrands);
                    }
                    else{
                        String[] gender = {"Select", "Male", "Female"};

                        ArrayAdapter<String> arrayAdapterBrands = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gender);
                        arrayAdapterBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        genderSpinner.setAdapter(arrayAdapterBrands);
                    }
                    mobileEditText.setText(patientObjectOnline.getMobile());
                    heightEditText.setText(patientObjectOnline.getHeight());
                    weightEditText.setText(patientObjectOnline.getWeight());
                    countryEditText.setText(patientObjectOnline.getCountry());
                    cityEditText.setText(patientObjectOnline.getCity());
                    countryEditText.setText(patientObjectOnline.getCountry());
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getActivity(), "Error while loading the user details.", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            String[] gender = {"Select", "Male", "Female"};

            ArrayAdapter<String> arrayAdapterBrands = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, gender);
            arrayAdapterBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            genderSpinner.setAdapter(arrayAdapterBrands);

        }

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedGender = adapterView.getItemAtPosition(i).toString();

                if (selectedGender.equals("Male")){
                    setProfilePicture("Male");
                }
                else {
                    setProfilePicture("Female");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setEditTextEnable(false);

                PatientObject patientObject = new PatientObject();
                patientObject.setUid(currentUser.getUid());
                patientObject.setEmail(emailEditText.getText().toString());
                patientObject.setFirstName(firstNameEditText.getText().toString());
                patientObject.setLastName(lastNameEditText.getText().toString());
                patientObject.setNic(nicEditText.getText().toString());
                patientObject.setDob(dobEditText.getText().toString());
                patientObject.setGender(selectedGender);
                patientObject.setMobile(mobileEditText.getText().toString());
                patientObject.setHeight(heightEditText.getText().toString());
                patientObject.setWeight(weightEditText.getText().toString());
                patientObject.setCountry(countryEditText.getText().toString());
                patientObject.setCity(cityEditText.getText().toString());
                patientObject.setAddress(addressEditText.getText().toString());

                setEditTextEnable(true);

                if(patientObject.getCountry().equals("Sri Lanka")){
                    patientObject.setCompleted(true);
                }
                else{
                    patientObject.setCompleted(false);

                }

                if(patientObject.isCompleted()){
                    DatabaseReference myRef = database.getReference("Users").child(currentUser.getUid());

                    myRef.setValue(patientObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            MainActivity.setPatientObject(patientObject);

                            Toast.makeText(getActivity(), "Successfully updated", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getActivity(), MainActivity2.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(), "Updating cannot be completed.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(getActivity(), "Please fill in all the required details.", Toast.LENGTH_SHORT).show();
                }
            }

            private void setEditTextEnable(boolean enable){
                firstNameEditText.setEnabled(enable);
                lastNameEditText.setEnabled(enable);
                nicEditText.setEnabled(enable);
                dobEditText.setEnabled(enable);
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

                    startActivity(new Intent(getActivity(), MainActivity.class));
                }
                else{
                    Toast.makeText(getActivity(), "Logging-out failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(patientObjectOnline.isCompleted()){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    MainMenu mainMenu = new MainMenu();
                    fm.beginTransaction().replace(R.id.fragmentContainerView, mainMenu).commit();
                }
                else{
                        Toast.makeText(getActivity(), "Please fill in and update the details.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if(patientObjectOnline.isCompleted()){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    MainMenu mainMenu = new MainMenu();
                    fm.beginTransaction().replace(R.id.fragmentContainerView, mainMenu).commit();
                }
                else{
                    Toast.makeText(getActivity(), "Please fill in and update the details.", Toast.LENGTH_SHORT).show();
                }
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getActivity(), callback);

        return v;
    }

    private void saveProfileImageResource(int resource) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putInt(PROFILE_IMAGE_KEY, resource);
        editor.apply();
    }

    private int getSavedProfileImageResource() {
        SharedPreferences prefs = getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(PROFILE_IMAGE_KEY, R.drawable.female_avatar); // Default to female_avatar if not found
    }

    private void setProfilePicture(String patientGender){

        int savedProfileImageResource = getSavedProfileImageResource();

        // Set the profile image based on the saved resource
        profileImage.setImageResource(savedProfileImageResource);


        int newProfileImageResource;
        if ("Male".equalsIgnoreCase(patientGender)) {
            newProfileImageResource = R.drawable.male_avatar;
        } else if ("Female".equalsIgnoreCase(patientGender)) {
            newProfileImageResource = R.drawable.female_avatar;
        } else {
            newProfileImageResource = R.drawable.female_avatar; // Set a default image
        }

        // Check if the gender has changed before updating the profile image
        if (newProfileImageResource != savedProfileImageResource) {
            // Update the profile image
            profileImage.setImageResource(newProfileImageResource);

            // Save the new profile image resource using SharedPreferences
            saveProfileImageResource(newProfileImageResource);
        }
    }

}