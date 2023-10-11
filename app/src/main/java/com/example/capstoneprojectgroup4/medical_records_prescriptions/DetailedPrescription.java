
package com.example.capstoneprojectgroup4.medical_records_prescriptions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;
import com.example.capstoneprojectgroup4.medical_records_prescriptions.drug_details.DrugDetailsAdapter;
import com.example.capstoneprojectgroup4.medical_records_prescriptions.drug_details.DrugDetailsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedPrescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedPrescription extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    PrescriptionObject prescriptionObject;

    public DetailedPrescription() {
        // Required empty public constructor
    }
    public DetailedPrescription(PrescriptionObject prescriptionObject){
        this.prescriptionObject = prescriptionObject;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailedPrescription.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailedPrescription newInstance(String param1, String param2) {
        DetailedPrescription fragment = new DetailedPrescription();
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
        View v = inflater.inflate(R.layout.fragment_detailed_prescription, container, false);

        TextView patientName = v.findViewById(R.id.textView_patientName);
        TextView doctorName = v.findViewById(R.id.textView_doctorName);
        TextView dob = v.findViewById(R.id.textView_dob);
        TextView writtenOn = v.findViewById(R.id.textView_writtenOn);
        TextView notes = v.findViewById(R.id.textView_prescriptionNotes);
        ImageView backButton = v.findViewById(R.id.backButtonMedicalRecords2);
        Button medsButton = v.findViewById(R.id.button_meds);

        patientName.setText(prescriptionObject.getPatientName());
        doctorName.setText(prescriptionObject.getDoctorName());
        dob.setText(prescriptionObject.getDob());
        writtenOn.setText(prescriptionObject.getWrittenOn());
        notes.setText(prescriptionObject.getPrescriptionNotes());

        medsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugDetailsFragment drugDetailsFragment = new DrugDetailsFragment(prescriptionObject);
                fm.beginTransaction().replace(R.id.fragmentContainerView, drugDetailsFragment).commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MedicalRecords medicalRecords = new MedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerView, medicalRecords).commit();
            }
        });

        return v;
    }
}