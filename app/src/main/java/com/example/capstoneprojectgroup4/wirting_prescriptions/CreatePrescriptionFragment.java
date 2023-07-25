package com.example.capstoneprojectgroup4.wirting_prescriptions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.wirting_prescriptions.drug_containers.DrugsContainers;

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
    FragmentManager fm;
    Button selectDrugs;
    EditText doctorName;
    EditText patientName;
    EditText date;
    EditText treatmentDuration;
    EditText prescriptionNotes;
    Map<String, Object> prescription = new HashMap<>();;
    public CreatePrescriptionFragment() {
        // Required empty public constructor
    }
    public CreatePrescriptionFragment(Map<String, Object> prescription) {
        this.prescription = prescription;

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
        doctorName = v.findViewById(R.id.edit_text_doctor_name);
        patientName = v.findViewById(R.id.edit_text_patient_name);
        date = v.findViewById(R.id.edit_text_date);
        treatmentDuration = v.findViewById(R.id.edit_text_treatment_duration);
        prescriptionNotes = v.findViewById(R.id.edit_text_prescription_notes);

        if(prescription.containsKey("Doctor's name")){
            if(!prescription.get("Doctor's name").toString().isEmpty())
                doctorName.setText(prescription.get("Doctor's name").toString());
        }
        if(prescription.containsKey("Patient's name")){
            if(!prescription.get("Patient's name").toString().isEmpty())
                patientName.setText(prescription.get("Patient's name").toString());
        }
        if(prescription.containsKey("Date")){
            if(!prescription.get("Date").toString().isEmpty())
                date.setText(prescription.get("Date").toString());
        }
        if(prescription.containsKey("Duration of treatments")){
            if(!prescription.get("Duration of treatments").toString().isEmpty())
                treatmentDuration.setText(prescription.get("Duration of treatments").toString());
        }
        if(prescription.containsKey("Prescription notes")){
            if(!prescription.get("Prescription notes").toString().isEmpty())
                prescriptionNotes.setText(prescription.get("Prescription notes").toString());
        }

        selectDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!doctorName.getText().toString().isEmpty())
                    prescription.put("Doctor's name", doctorName.getText().toString());
                if(!patientName.getText().toString().isEmpty())
                    prescription.put("Patient's name", patientName.getText().toString());
                if(!date.getText().toString().isEmpty())
                    prescription.put("Date", date.getText().toString());
                if(!treatmentDuration.getText().toString().isEmpty())
                    prescription.put("Duration of treatments", treatmentDuration.getText().toString());
                if(!prescriptionNotes.getText().toString().isEmpty())
                    prescription.put("Prescription notes", prescriptionNotes.getText().toString());

                fm = getActivity().getSupportFragmentManager();

                DrugsContainers drugsContainers = new DrugsContainers(prescription);
                fm.beginTransaction().replace(R.id.fragment_container, drugsContainers).commit();
            }
        });

        return v;
    }
}