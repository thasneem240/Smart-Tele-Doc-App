package com.example.capstoneprojectgroup4.writing_prescriptions;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers.DrugsContainers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePrescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePrescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button selectDrugs;
    Button submitPrescription;
    EditText doctorName;
    EditText patientName;
    EditText date;
    EditText treatmentDuration;
    EditText prescriptionNotes;
    TextView drugsCount;
    Map<String, Object> prescription = new HashMap<>();
    WritingPrescriptionActivity writingPrescriptionActivity;

    public CreatePrescriptionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreatePrescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreatePrescriptionFragment newInstance(String param1, String param2) {
        CreatePrescriptionFragment fragment = new CreatePrescriptionFragment();
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
        View v = inflater.inflate(R.layout.fragment_create_prescription, container, false);


        selectDrugs = v.findViewById(R.id.button_select_drugs);
        submitPrescription = v.findViewById(R.id.button_submit_prescription);
        doctorName = v.findViewById(R.id.edit_text_doctor_name);
        patientName = v.findViewById(R.id.edit_text_patient_name);
        date = v.findViewById(R.id.edit_text_date);
        treatmentDuration = v.findViewById(R.id.edit_text_treatment_duration);
        prescriptionNotes = v.findViewById(R.id.edit_text_prescription_notes);
        drugsCount = v.findViewById(R.id.drugs_count);

        writingPrescriptionActivity = (WritingPrescriptionActivity) v.getContext();
        prescription = writingPrescriptionActivity.getPrescription();

        if(!writingPrescriptionActivity.getSelectedDrugs().isEmpty())
            drugsCount.setText(""+ writingPrescriptionActivity.getSelectedDrugs().size());

        if(!prescription.isEmpty()){
            if(prescription.containsKey("Doctor's name")){
                doctorName.setText(prescription.get("Doctor's name").toString());
            }
            if(prescription.containsKey("Patient's name")){
                patientName.setText(prescription.get("Patient's name").toString());
            }
            if(prescription.containsKey("Date")){
                date.setText(prescription.get("Date").toString());
            }
            if(prescription.containsKey("Duration of treatments")){
                treatmentDuration.setText(prescription.get("Duration of treatments").toString());
            }
            if(prescription.containsKey("Prescription notes")){
                prescriptionNotes.setText(prescription.get("Prescription notes").toString());
            }
        }

        selectDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescription.put("Doctor's name", doctorName.getText().toString());
                prescription.put("Patient's name", patientName.getText().toString());
                prescription.put("Date", date.getText().toString());
                prescription.put("Duration of treatments", treatmentDuration.getText().toString());
                prescription.put("Prescription notes", prescriptionNotes.getText().toString());

                writingPrescriptionActivity = (WritingPrescriptionActivity) getActivity();
                writingPrescriptionActivity.setPrescription(prescription);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();
            }
        });



        submitPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescription.put("Doctor's name", doctorName.getText().toString());
                prescription.put("Patient's name", patientName.getText().toString());
                prescription.put("Date", date.getText().toString());
                prescription.put("Duration of treatments", treatmentDuration.getText().toString());
                prescription.put("Prescription notes", prescriptionNotes.getText().toString());

                writingPrescriptionActivity = (WritingPrescriptionActivity) getActivity();
                prescription.put("Selected drugs", writingPrescriptionActivity.getSelectedDrugs());

                long time= System.currentTimeMillis();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Prescriptions2").child(""+doctorName.getText().toString()+"_"+System.currentTimeMillis());

                myRef.setValue(prescription).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent mainActivity = new Intent(getActivity(), MainActivity.class);
                        startActivity(mainActivity);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });


            }
        });

        return v;
    }
}