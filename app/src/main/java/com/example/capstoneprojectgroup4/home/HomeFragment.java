package com.example.capstoneprojectgroup4.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.available_pharmacies.AvailablePharmaciesFragment;
import com.example.capstoneprojectgroup4.prescriptions.view_prescriptions.ViewPrescriptionsFragment;
import com.example.capstoneprojectgroup4.wirting_prescriptions.WritingPrescriptionActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FragmentManager fm;
    FirebaseDatabase database;

    int pharmacyNumber;
    Map<String, Object> map;
    Map<String, Object> mapMedicine;
    Map<String, Object> qtyAndValue;



    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        fm = getActivity().getSupportFragmentManager();

        Button availablePharmacies = v.findViewById(R.id.available_pharmacies);
        Button createPrescription = v.findViewById(R.id.create_prescription);
        Button viewPrescriptions = v.findViewById(R.id.button_prescriptions);

        availablePharmacies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Integer> prescription = new HashMap<>();
                prescription.put("Medicine 1", 11);
                prescription.put("Medicine 2", 8);

                database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();

                ArrayList<String> availablePharmacies = new ArrayList<>();
                myRef.child("Phar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            map = (Map) task.getResult().getValue();

                            boolean allTheDrugsAreAvailable = true;

                            for (Map.Entry<String, Object> entryL1 : map.entrySet()) {
                                String pharmacy = entryL1.getKey();
                                mapMedicine = (Map) entryL1.getValue();

                                for (Map.Entry<String, Integer> entry : prescription.entrySet()) {

                                    String drug = entry.getKey();
                                    String s = String.valueOf(entry.getValue());
                                    int dosage = Integer.valueOf(s);

                                    qtyAndValue = (Map) mapMedicine.get(drug);
                                    String sss = String.valueOf(qtyAndValue.get("qty"));
                                    int qty = Integer.valueOf(sss);

                                    if (qty <= dosage)
                                        allTheDrugsAreAvailable = false;

                                }

                                if (allTheDrugsAreAvailable)
                                    availablePharmacies.add(pharmacy);

                            }

                        }
                        AvailablePharmaciesFragment availablePharmaciesFragment = new AvailablePharmaciesFragment(availablePharmacies);
                        fm.beginTransaction().replace(R.id.fragment_container, availablePharmaciesFragment).commit();
                    }
                });

            }
        });

        createPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prescriptionActivity = new Intent(getActivity(), WritingPrescriptionActivity.class);
                startActivity(prescriptionActivity);
            }
        });

        viewPrescriptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewPrescriptionsFragment viewPrescriptionsFragment = new ViewPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragment_container, viewPrescriptionsFragment).commit();
            }
        });

        return v;
    }
}