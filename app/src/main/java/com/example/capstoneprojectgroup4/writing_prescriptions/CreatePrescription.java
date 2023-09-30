package com.example.capstoneprojectgroup4.writing_prescriptions;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers.DrugsContainers;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreatePrescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreatePrescription extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView backButton;
    Button selectDrugs;
    Button submitPrescription;
    EditText eidtText_doctorName;
    EditText editText_patientName;
    EditText editText_dob;
    EditText editText_expirationDate;
    EditText editText_prescriptionNotes;
    EditText editText_writtenOn;
    TextView drugsCount;
    WritingPrescriptionActivity writingPrescriptionActivity;
    String currentTimeObj;
    int treatmentDurationObj; // treatment duration in days
    ArrayList<PrescriptionDrugObject> selectedDrugsObj;
    String doctorNameObj, patientNameObj, prescriptionNotesObj;

    public CreatePrescription() {
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
    public static CreatePrescription newInstance(String param1, String param2) {
        CreatePrescription fragment = new CreatePrescription();
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

        selectDrugs = v.findViewById(R.id.Button_SelectedDrugs);
        submitPrescription = v.findViewById(R.id.Button_Submit);
        eidtText_doctorName = v.findViewById(R.id.EditText_doctorName);
        editText_patientName = v.findViewById(R.id.EditText_patientName);
        editText_dob = v.findViewById(R.id.EditText_dob);
        editText_writtenOn = v.findViewById(R.id.EditText_WrittenOn);
        editText_expirationDate = v.findViewById(R.id.EditText_ExpirationDate);
        editText_prescriptionNotes = v.findViewById(R.id.EditText_PrescriptionNotes);
        drugsCount = v.findViewById(R.id.TextView_DrugsCount);
        backButton = v.findViewById(R.id.ImageView_BackButton);

        writingPrescriptionActivity = (WritingPrescriptionActivity) v.getContext();
        PrescriptionObject prescriptionObject = writingPrescriptionActivity.getPrescriptionObject();

        editText_patientName.setText(prescriptionObject.getPatientName());
        eidtText_doctorName.setText(prescriptionObject.getDoctorName());
        editText_dob.setText(prescriptionObject.getDob());
        editText_writtenOn.setText(prescriptionObject.getWrittenOn());
        editText_expirationDate.setText(prescriptionObject.getExpireAfter());
        editText_prescriptionNotes.setText(prescriptionObject.getPrescriptionNotes());

        drugsCount.setText(""+ writingPrescriptionActivity.getSelectedDrug().size());

        selectDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prescriptionObject.setPatientName(editText_patientName.getText().toString());
                prescriptionObject.setDoctorName(eidtText_doctorName.getText().toString());
                prescriptionObject.setDob(editText_dob.getText().toString());
                prescriptionObject.setWrittenOn(editText_writtenOn.getText().toString());
                prescriptionObject.setExpireAfter(editText_expirationDate.getText().toString());
                prescriptionObject.setPrescriptionNotes(editText_expirationDate.getText().toString());

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
                prescriptionObject.setPatientName(editText_patientName.getText().toString());
                prescriptionObject.setDoctorName(eidtText_doctorName.getText().toString());
                prescriptionObject.setDob(editText_dob.getText().toString());
                prescriptionObject.setWrittenOn(editText_writtenOn.getText().toString());
                prescriptionObject.setExpireAfter(editText_expirationDate.getText().toString());
                prescriptionObject.setPrescriptionNotes(editText_expirationDate.getText().toString());

                writingPrescriptionActivity = (WritingPrescriptionActivity) getActivity();
                prescriptionObject.setSelectedDrugs(writingPrescriptionActivity.getSelectedDrug());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Prescriptions2").child(""+ eidtText_doctorName.getText().toString()+"_"+System.currentTimeMillis());

                myRef.setValue(prescriptionObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(writingPrescriptionActivity, "Prescription has submitted successfully", Toast.LENGTH_SHORT).show();
//                        Intent MainActivity2 = new Intent(getActivity(), MainActivity2.class);
//                        startActivity(MainActivity2);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // do nothing
                // needs to change later
            }
        });

        return v;
    }
}