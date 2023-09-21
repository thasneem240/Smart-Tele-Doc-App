package com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.drug_containers;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.prescriptions.edit_prescription.EditPrescriptionAdapter;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.SearchWordByWord;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.select_the_drug.SelectTheDrug;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.CreatePrescriptionFragment;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.WritingPrescriptionActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
        View v = inflater.inflate(R.layout.fragment_select_drugs, container, false);

        Button backToPrescription = v.findViewById(R.id.button_to_prescription);
        ImageButton addDrugs = v.findViewById(R.id.button_add_drugs);

        RecyclerView rv = v.findViewById(R.id.drugs_container_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) getActivity();
        DrugsContainersAdapter drugsContainersAdapter = new DrugsContainersAdapter(writingPrescriptionActivity.getSelectedDrug2s());
        rv.setAdapter(drugsContainersAdapter);

        backToPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CreatePrescriptionFragment createPrescriptionFragment = new CreatePrescriptionFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, createPrescriptionFragment).commit();
            }
        });

        addDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                SelectTheDrug selectTheDrug = new SelectTheDrug(listOfDrugs);
//                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, selectTheDrug).commit();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("Drugs");
                ArrayList<String> listOfDrugs = new ArrayList<>();
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            String s = ds.getKey();
                            listOfDrugs.add(s);
                        }

                        FragmentManager fm = getActivity().getSupportFragmentManager();
                        SearchWordByWord searchWordByWord = new SearchWordByWord(listOfDrugs);
//                fm.beginTransaction().remove(fm.findFragmentById(R.id.fragmentContainerPrescription));
                        fm.beginTransaction().replace(R.id.fragmentContainerPrescription, searchWordByWord).commit();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                    }
                });
            }
        });

        return v;
    }
}