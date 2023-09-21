package com.example.capstoneprojectgroup4;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.google.firebase.database.DatabaseReference;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_LabReports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_LabReports extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // Variable Declarations
    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private Button selectFileButton;
    private EditText selectedFileNameEditText;
    private Button uploadButton;


    // Firebase

   // StorageReference storageReference;
   // DatabaseReference databaseReference;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag_LabReports()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_LabReports.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_LabReports newInstance(String param1, String param2)
    {
        Frag_LabReports fragment = new Frag_LabReports();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lab_reports, container, false);


        selectFileButton = view.findViewById(R.id.selectFileButton);
        selectedFileNameEditText = view.findViewById(R.id.selectedFileNameEditText);
        uploadButton = view.findViewById(R.id.uploadButton);

        ImageView backButton = view.findViewById(R.id.backButtonLabReports);

        /* Grab the  UI Variables from Layout file */

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MedicalRecords searchDoctors = new MedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        selectFileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                openFilePicker();
            }
        });


        uploadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Implement file upload logic here
                // You can use the selected file (URI) for uploading to a server or saving locally.
                String selectedFilePath = selectedFileNameEditText.getText().toString();
                // Handle the upload logic accordingly.
            }
        });


        return  view;
    }



    private void openFilePicker()
    {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*,application/pdf"); // Allow JPG and PDF files
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {
            if (data != null && data.getData() != null)
            {
                Uri selectedFileUri = data.getData();
                selectedFileNameEditText.setText(selectedFileUri.toString());
            }
        }
    }

    // Query the database for lab reports
    DatabaseReference labReportsRef = databaseReference.child("lab_reports").child(patientID);


    // Function to retrieve and display patient's records and lab reports
    private void displayPatientRecords(String patientID)
    {
        // Query the database for patient information
        DatabaseReference patientRef = databaseReference.child("patients").child(patientID);
        patientRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    PatientRecord patient = dataSnapshot.getValue(PatientRecord.class);
                    // Update UI with patient's information
                    // For example, set TextViews with patient's name, ID, etc.
                    TextView patientNameTextView = view.findViewById(R.id.patientNameTextView);
                    patientNameTextView.setText(patient.getPatientName());
                }
            }




}