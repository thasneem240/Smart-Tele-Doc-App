package com.example.capstoneprojectgroup4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_MedicalHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_MedicalHistory extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Frag_MedicalHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_MedicalHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_MedicalHistory newInstance(String param1, String param2) {
        Frag_MedicalHistory fragment = new Frag_MedicalHistory();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical_history, container, false);
        ImageView backButton = view.findViewById(R.id.backButtonMedicalHistory);

        /* Grab the  UI Variables from Layout file */

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MedicalRecords searchDoctors = new MedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });


        List<MedicalHistoryItem> medicalHistoryItems = new ArrayList<>();

        // Populate medicalHistoryItems with your data


        medicalHistoryItems = MedicalHistoryItemGenerator.generateRandomMedicalHistoryItems(200);

        // Obtain the RecyclerView UI element
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.medicalHistoryRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Create Adapter for the recyclerview
        MedicalHistoryAdapter adapter = new MedicalHistoryAdapter(medicalHistoryItems);

        // Hook it up
        recyclerView.setAdapter(adapter);


        return  view;


    }
}