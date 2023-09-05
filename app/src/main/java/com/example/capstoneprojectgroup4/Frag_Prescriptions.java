package com.example.capstoneprojectgroup4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_Prescriptions#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_Prescriptions extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag_Prescriptions() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_Prescriptions.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_Prescriptions newInstance(String param1, String param2) {
        Frag_Prescriptions fragment = new Frag_Prescriptions();
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
        View view = inflater.inflate(R.layout.fragment_prescriptions, container, false);


        List<PrescriptionItem> prescriptionItems = new ArrayList<>();
        // Populate prescriptionItems with your data

        prescriptionItems = PrescriptionItemGenerator.generateRandomPrescriptionItems(200);

        // Obtain the RecyclerView UI element
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.prescriptionRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Create Adapter for the recyclerview
        PrescriptionAdapter adapter = new PrescriptionAdapter(prescriptionItems);
        recyclerView.setAdapter(adapter);

        return view;
    }
}