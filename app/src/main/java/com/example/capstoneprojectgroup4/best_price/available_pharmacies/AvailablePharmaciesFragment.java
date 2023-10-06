package com.example.capstoneprojectgroup4.best_price.available_pharmacies;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.ObjectPharmacyAndPrice;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsFragment;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AvailablePharmaciesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AvailablePharmaciesFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<ObjectPharmacyAndPrice> availablePharmacies;

    public AvailablePharmaciesFragment() {
        // Required empty public constructor
    }

    public AvailablePharmaciesFragment(ArrayList<ObjectPharmacyAndPrice> availablePharmacies) {
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
    public static AvailablePharmaciesFragment newInstance(String param1, String param2) {
        AvailablePharmaciesFragment fragment = new AvailablePharmaciesFragment();
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
        View v = inflater.inflate(R.layout.fragment_available_pharmacies, container, false);

        ImageView backButton = v.findViewById(R.id.backButtonAvailablePharmacies);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PharmaciesF pharmaciesF = new PharmaciesF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, pharmaciesF).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPrescriptionsFragment pharmaciesF = new ListOfPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, pharmaciesF).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);

        RecyclerView rv = v.findViewById(R.id.availablePharmaciesRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        AvailablePharmaciesAdapter availablePharmaciesAdapter = new AvailablePharmaciesAdapter(availablePharmacies);
        rv.setAdapter(availablePharmaciesAdapter);

        return v;
    }
}