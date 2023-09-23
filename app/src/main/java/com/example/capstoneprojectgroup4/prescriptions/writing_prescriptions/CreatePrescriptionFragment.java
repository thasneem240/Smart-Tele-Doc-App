package com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions;

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
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.prescriptions.edit_prescription.EditPrescriptionFragment;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.drug_containers.DrugsContainers;
import com.example.capstoneprojectgroup4.prescriptions.PrescriptionObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
    PrescriptionObject prescriptionObject;
    WritingPrescriptionActivity writingPrescriptionActivity;
    String currentTimeObj;
    int treatmentDurationObj; // treatment duration in days
    ArrayList<String> selectedDrugsObj;
    String doctorNameObj, patientNameObj, prescriptionNotesObj;

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
        View v = inflater.inflate(R.layout.fragment_prescription_writing, container, false);

        selectDrugs = v.findViewById(R.id.select_drugs_button);
        submitPrescription = v.findViewById(R.id.SubmitButton);
        doctorName = v.findViewById(R.id.doctor_s_na);
        patientName = v.findViewById(R.id.patientName);
        date = v.findViewById(R.id.presc_date);
        treatmentDuration = v.findViewById(R.id.treatment_duration);
        prescriptionNotes = v.findViewById(R.id.pres_notes);
        drugsCount = v.findViewById(R.id.drugs_count_textView);

        writingPrescriptionActivity = (WritingPrescriptionActivity) v.getContext();
        prescriptionObject = writingPrescriptionActivity.getPrescriptionObject();

        doctorName.setText(prescriptionObject.getDoctorName());
        patientName.setText(prescriptionObject.getPatientName());
        date.setText(prescriptionObject.getDateTime()+"");
        treatmentDuration.setText(prescriptionObject.getTreatmentDuration()+"");
        prescriptionNotes.setText(prescriptionObject.getPrescriptionNotes());

        drugsCount.setText(""+ writingPrescriptionActivity.getSelectedDrug2s().size());

        selectDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorNameObj = doctorName.getText().toString();
                patientNameObj = patientName.getText().toString();
                currentTimeObj = "Tuesday";
//                currentTimeObj = Calendar.getInstance().getTime();
                treatmentDurationObj = Integer.valueOf(treatmentDuration.getText().toString());
                prescriptionNotesObj = prescriptionNotes.getText().toString();

                prescriptionObject = new PrescriptionObject();
                prescriptionObject.setDoctorName(doctorNameObj);
                prescriptionObject.setPatientName(patientNameObj);
                prescriptionObject.setDateTime(currentTimeObj+"");
                prescriptionObject.setTreatmentDuration(treatmentDurationObj);
                prescriptionObject.setPrescriptionNotes(prescriptionNotesObj);

                writingPrescriptionActivity = (WritingPrescriptionActivity) getActivity();
                writingPrescriptionActivity.setPrescriptionObject(prescriptionObject);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();

            }
        });



        submitPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doctorNameObj = doctorName.getText().toString();
                patientNameObj = patientName.getText().toString();
//                currentTimeObj = Calendar.getInstance().getTime();
                currentTimeObj = "Tuesday";
                treatmentDurationObj = Integer.valueOf(treatmentDuration.getText().toString());
                prescriptionNotesObj = prescriptionNotes.getText().toString();
                writingPrescriptionActivity = (WritingPrescriptionActivity) getActivity();
                selectedDrugsObj = writingPrescriptionActivity.getSelectedDrug2s();

                prescriptionObject = new PrescriptionObject();
                prescriptionObject.setDoctorName(doctorNameObj);
                prescriptionObject.setPatientName(patientNameObj);
                prescriptionObject.setDateTime(currentTimeObj+"");
                prescriptionObject.setTreatmentDuration(treatmentDurationObj);
                prescriptionObject.setPrescriptionNotes(prescriptionNotesObj);
                prescriptionObject.setSelectedDrugs(selectedDrugsObj);

                long time= System.currentTimeMillis();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Prescriptions3").child(""+doctorName.getText().toString()+"_"+System.currentTimeMillis());

                myRef.setValue(prescriptionObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Intent MainActivity2 = new Intent(getActivity(), MainActivity2.class);
                        startActivity(MainActivity2);
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