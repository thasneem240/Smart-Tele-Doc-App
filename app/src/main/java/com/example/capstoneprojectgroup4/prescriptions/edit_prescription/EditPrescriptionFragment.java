package com.example.capstoneprojectgroup4.prescriptions.edit_prescription;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.capstoneprojectgroup4.available_pharmacies.AvailablePharmaciesFragment;
import com.example.capstoneprojectgroup4.available_pharmacies.ObjectPharmacyAndPrice;
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
 * Use the {@link EditPrescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPrescriptionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Map<String, Object>> selectedDrugs;
    Button availablePharmacies;

    public EditPrescriptionFragment() {
        // Required empty public constructor
    }
    public EditPrescriptionFragment(ArrayList<Map<String, Object>> selectedDrugs) {
        this.selectedDrugs = selectedDrugs;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPrescriptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPrescriptionFragment newInstance(String param1, String param2) {
        EditPrescriptionFragment fragment = new EditPrescriptionFragment();
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
        View v = inflater.inflate(R.layout.fragment_edit_prescription, container, false);

        RecyclerView rv = v.findViewById(R.id.recycler_view_edit_prescription);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        EditPrescriptionAdapter editPrescriptionAdapter = new EditPrescriptionAdapter(selectedDrugs);
        rv.setAdapter(editPrescriptionAdapter);

        availablePharmacies = v.findViewById(R.id.button_available_pharmacies);

        availablePharmacies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Integer> selectedDrugsPrescription = new HashMap<>();

                for(Map<String, Object> eachSelectedDrug : selectedDrugs){
                    selectedDrugsPrescription.put(eachSelectedDrug.get("key").toString(), Integer.valueOf(eachSelectedDrug.get("value")+""));
                }

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();


                myRef.child("Phar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<ObjectPharmacyAndPrice> availablePharmacies = getAvailablePharmacies(task, selectedDrugsPrescription);

                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            AvailablePharmaciesFragment availablePharmaciesFragment = new AvailablePharmaciesFragment(availablePharmacies);
                            fm.beginTransaction().replace(R.id.fragment_container, availablePharmaciesFragment).commit();
                        }
                    }
                });

            }
        });

        return v;
    }

    private ArrayList<ObjectPharmacyAndPrice> getAvailablePharmacies(Task<DataSnapshot> task, Map<String, Integer> prescription){
        Map<String, Object> allPharmacies;
        Map<String, Object> allTheMedicineEachPharmacy;
        Map<String, Object> qtyAndValue;
        int totalCost = 0;

        ArrayList<ObjectPharmacyAndPrice> availablePharmacies = new ArrayList<>();
        allPharmacies = (Map) task.getResult().getValue();

        boolean allTheDrugsAreAvailable = true;

        for (Map.Entry<String, Object> onePharmacy : allPharmacies.entrySet()) {
            String pharmacy = onePharmacy.getKey();
            allTheMedicineEachPharmacy = (Map) onePharmacy.getValue();

            for (Map.Entry<String, Integer> entry : prescription.entrySet()) {

                String drug = entry.getKey();
                String s = String.valueOf(entry.getValue());
                int dosage = Integer.valueOf(s);

                if(allTheMedicineEachPharmacy.containsKey(drug)){

                    qtyAndValue = (Map) allTheMedicineEachPharmacy.get(drug);
                    String qtyString = String.valueOf(qtyAndValue.get("qty"));
                    int qty = Integer.valueOf(qtyString);

                    if (qty <= dosage)
                        allTheDrugsAreAvailable = false;
                    else{
                        String stringPriceForOne = String.valueOf(qtyAndValue.get("price"));
                        int priceForOne = Integer.valueOf(stringPriceForOne);

                        totalCost = totalCost + priceForOne*dosage;
                    }
                }
                else
                    allTheDrugsAreAvailable = false;

            }

            if (allTheDrugsAreAvailable){
                ObjectPharmacyAndPrice objectPharmacyAndPrice = new ObjectPharmacyAndPrice(pharmacy, totalCost);
                availablePharmacies.add(objectPharmacyAndPrice);
            }

            totalCost = 0;

        }
        return availablePharmacies;
    }
}