package com.example.capstoneprojectgroup4.interface_of_doctors.other;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorUserProfile#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorUserProfile extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorUserProfile() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorUserProfile.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorUserProfile newInstance(String param1, String param2) {
        DoctorUserProfile fragment = new DoctorUserProfile();
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
        View v = inflater.inflate(R.layout.fragment_doctor_user_profile, container, false);

        TextView textViewGreetings = v.findViewById(R.id.TextView_Greetings);
        TextInputEditText editTextRegNumber = v.findViewById(R.id.EditText_Email);
        TextInputEditText editTextName = v.findViewById(R.id.EditText_DoctorName);
        TextInputEditText editTextSpecialization = v.findViewById(R.id.EditText_MobileNumber);
        ImageView backButton = v.findViewById(R.id.ImageView_BackButton2);
        Button logoutButton = v.findViewById(R.id.button_Logout);

        textViewGreetings.setText("Hi, "+DoctorsActivity.getDoctorObject().getName());
        editTextName.setText(DoctorsActivity.getDoctorObject().getName());
        editTextRegNumber.setText(DoctorsActivity.getDoctorRegNumber());
        editTextSpecialization.setText(DoctorsActivity.getDoctorObject().getSpecialization());

        editTextName.setEnabled(false);
        editTextRegNumber.setEnabled(false);
        editTextSpecialization.setEnabled(false);

        FragmentManager fm = getActivity().getSupportFragmentManager();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(mainActivity);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DoctorMainMenu doctorMainMenu = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMainMenu).commit();
            }
        });

        return v;
    }
}