package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.drug_details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.DetailedPrescription2;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrugDetailsFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrugDetailsFragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    PrescriptionObject prescriptionObject;
    public DrugDetailsFragment2() {
        // Required empty public constructor
    }
    public DrugDetailsFragment2(PrescriptionObject prescriptionObject){
        this.prescriptionObject = prescriptionObject;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrugDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DrugDetailsFragment2 newInstance(String param1, String param2) {
        DrugDetailsFragment2 fragment = new DrugDetailsFragment2();
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
        View v = inflater.inflate(R.layout.fragment_drug_details, container, false);

        ImageView backButton = v.findViewById(R.id.backButtonViewPrescription2);
        RecyclerView rv = v.findViewById(R.id.recyclerView_medDetails);

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        DrugDetailsAdapter2 drugDetailsAdapter2 = new DrugDetailsAdapter2(prescriptionObject.getSelectedDrugs());
        rv.setAdapter(drugDetailsAdapter2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DetailedPrescription2 detailedPrescription2 = new DetailedPrescription2(prescriptionObject);
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, detailedPrescription2).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DetailedPrescription2 detailedPrescription2 = new DetailedPrescription2(prescriptionObject);
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, detailedPrescription2).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return v;
    }
}