package com.example.capstoneprojectgroup4.best_price.edit_howMuch;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.best_price.available_pharmacies.AvailablePharmaciesFragment;
import com.example.capstoneprojectgroup4.best_price.ObjectPharmacyAndPrice;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditHowMuchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditHowMuchFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<PrescriptionDrugObject> selectedDrugs;
    Button availablePharmacies;

    public EditHowMuchFragment() {
        // Required empty public constructor
    }
    public EditHowMuchFragment(ArrayList<PrescriptionDrugObject> selectedDrugs) {
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
    public static EditHowMuchFragment newInstance(String param1, String param2) {
        EditHowMuchFragment fragment = new EditHowMuchFragment();
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
        View v = inflater.inflate(R.layout.fragment_edit_how_much, container, false);

        ImageView backButton = v.findViewById(R.id.backButtonSearchDrugs);

        RecyclerView rv = v.findViewById(R.id.recycler_view_edit_prescription);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        EditHowMuchAdapter editHowMuchAdapter = new EditHowMuchAdapter(selectedDrugs);
        rv.setAdapter(editHowMuchAdapter);

        availablePharmacies = v.findViewById(R.id.button_available_pharmacies);

        availablePharmacies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Integer> selectedDrugsPrescription = new HashMap<>();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference();


                myRef.child("Pharmacy database").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<ObjectPharmacyAndPrice> availablePharmacies = getAvailablePharmacies(task, selectedDrugsPrescription);

                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            AvailablePharmaciesFragment availablePharmaciesFragment = new AvailablePharmaciesFragment(availablePharmacies);
                            fm.beginTransaction().replace(R.id.fragmentContainerView, availablePharmaciesFragment).commit();
                        }
                    }
                });

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPrescriptionsFragment listOfPrescriptionsFragment = new ListOfPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, listOfPrescriptionsFragment).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPrescriptionsFragment listOfPrescriptionsFragment = new ListOfPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, listOfPrescriptionsFragment).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        return v;
    }

    private ArrayList<ObjectPharmacyAndPrice> getAvailablePharmacies(Task<DataSnapshot> task, Map<String, Integer> prescription){
        Map<String, Object> allPharmacies;
        Map<String, Object> allTheMedicineEachPharmacy;
        Map<String, Object> pharmacyDrugObject;
        float totalCost = 0;

        ArrayList<ObjectPharmacyAndPrice> availablePharmacies = new ArrayList<>();
        allPharmacies = (Map) task.getResult().getValue();

        boolean allTheDrugsAreAvailable = true;

        for (Map.Entry<String, Object> onePharmacy : allPharmacies.entrySet()) {
            String pharmacy = onePharmacy.getKey();
            allTheMedicineEachPharmacy = (Map) onePharmacy.getValue();

            for(PrescriptionDrugObject oneSelectedDrug : selectedDrugs){
                String drug = oneSelectedDrug.getNameOfTheDrug();
                int amount = oneSelectedDrug.getAmount();

                if(allTheMedicineEachPharmacy.containsKey(drug)){

                    pharmacyDrugObject = (Map) allTheMedicineEachPharmacy.get(drug);

                    boolean available = Boolean.parseBoolean(pharmacyDrugObject.get("availability")+"");

                    if (available){

                        float priceForOne = Float.parseFloat(pharmacyDrugObject.get("price")+"");

                        totalCost = totalCost + priceForOne*amount;

                    }
                    else{

                        allTheDrugsAreAvailable = false;

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

        availablePharmacies.sort(new Comparator<ObjectPharmacyAndPrice>() {
            @Override
            public int compare(ObjectPharmacyAndPrice o1, ObjectPharmacyAndPrice o2) {
                return Float.compare(o1.price, o2.price);
            }
        });


        return availablePharmacies;
    }
}