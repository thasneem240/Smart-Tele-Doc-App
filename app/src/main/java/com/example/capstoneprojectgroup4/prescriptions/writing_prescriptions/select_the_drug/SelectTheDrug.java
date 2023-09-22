package com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.select_the_drug;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectTheDrug#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTheDrug extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> listOfDrugs;

    public SelectTheDrug() {
        // Required empty public constructor
    }
    public SelectTheDrug(ArrayList<String> listOfDrugs) {
        this.listOfDrugs = listOfDrugs;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTheDrug.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTheDrug newInstance(String param1, String param2) {
        SelectTheDrug fragment = new SelectTheDrug();
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
        View v = inflater.inflate(R.layout.fragment_select_the_drug, container, false);
        RecyclerView rv = v.findViewById(R.id.selectTheDrugRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        SelectTheDrugAdapter selectTheDrugAdapter = new SelectTheDrugAdapter(listOfDrugs);
        rv.setAdapter(selectTheDrugAdapter);

        return v;
    }
}