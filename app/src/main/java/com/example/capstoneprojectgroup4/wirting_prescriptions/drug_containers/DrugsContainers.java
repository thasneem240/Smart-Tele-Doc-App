package com.example.capstoneprojectgroup4.wirting_prescriptions.drug_containers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.available_pharmacies.AvailablePharmaciesAdapter;
import com.example.capstoneprojectgroup4.wirting_prescriptions.CreatePrescriptionFragment;
import com.example.capstoneprojectgroup4.wirting_prescriptions.PrescriptionActivity;
import com.example.capstoneprojectgroup4.wirting_prescriptions.select_the_drug.SelectTheDrug;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrugsContainers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrugsContainers extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Map<String, Object> prescription;

    public DrugsContainers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectDrugsTemp.
     */
    // TODO: Rename and change types and number of parameters
    public static DrugsContainers newInstance(String param1, String param2) {
        DrugsContainers fragment = new DrugsContainers();
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
        View v = inflater.inflate(R.layout.fragment_select_drugs_temp, container, false);

        Button backToPrescription = v.findViewById(R.id.button_to_prescription);

        RecyclerView rv = v.findViewById(R.id.drugs_container_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        PrescriptionActivity prescriptionActivity =  (PrescriptionActivity) getActivity();
        DrugsContainersAdapter drugsContainersAdapter = new DrugsContainersAdapter(prescriptionActivity.numberOfContainers);
        rv.setAdapter(drugsContainersAdapter);

        backToPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CreatePrescriptionFragment createPrescriptionFragment = new CreatePrescriptionFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, createPrescriptionFragment).commit();
            }
        });

        return v;
    }
}