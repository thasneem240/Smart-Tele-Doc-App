
package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.drug_details.DrugDetailsFragment2;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailedPrescription2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailedPrescription2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    PrescriptionObject prescriptionObject;

    public DetailedPrescription2() {
        // Required empty public constructor
    }
    public DetailedPrescription2(PrescriptionObject prescriptionObject){
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
    public static DetailedPrescription2 newInstance(String param1, String param2) {
        DetailedPrescription2 fragment = new DetailedPrescription2();
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
                DrugDetailsFragment2 drugDetailsFragment2 = new DrugDetailsFragment2(prescriptionObject);
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, drugDetailsFragment2).commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();
            }
        });

        return v;
    }
}