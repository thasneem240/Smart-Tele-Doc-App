package com.example.capstoneprojectgroup4.search_drugs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDrugsF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDrugsF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> availablePharmacies;

    public SearchDrugsF() {
        // Required empty public constructor
    }

    public SearchDrugsF(ArrayList<String> availablePharmacies) {
        this.availablePharmacies = availablePharmacies;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchDrugsF.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchDrugsF newInstance(String param1, String param2) {
        SearchDrugsF fragment = new SearchDrugsF();
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
        View v = inflater.inflate(R.layout.fragment_search_drugs, container, false);

        RecyclerView rv = v.findViewById(R.id.availablePharmaciesRV);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchMedicinesAdapter searchMedicinesAdapter = new SearchMedicinesAdapter(availablePharmacies);
        rv.setAdapter(searchMedicinesAdapter);

        return v;
    }
}