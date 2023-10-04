package com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.writing_prescriptions.AddDrugsManually;
import com.example.capstoneprojectgroup4.writing_prescriptions.DatabaseDrugObject;
import com.example.capstoneprojectgroup4.writing_prescriptions.DrugData;
import com.example.capstoneprojectgroup4.writing_prescriptions.CreatePrescription;
import com.example.capstoneprojectgroup4.writing_prescriptions.WritingPrescriptionActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    ProgressBar loadingDrugs;

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
        View v = inflater.inflate(R.layout.fragment_drug_containers, container, false);

        Button backToPrescription = v.findViewById(R.id.button_to_prescription);
        Button addDrugsFromTheList = v.findViewById(R.id.button_add_drugs);
       // Button addDrugsManuallyButton = v.findViewById(R.id.Button_AddDrugsManually);
        EditText nameOfTheDrug = v.findViewById(R.id.EditText_ManualAddDrugs);
        ImageView back = v.findViewById(R.id.backButtonSelectDrugs);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CreatePrescription createPrescription = (CreatePrescription) fm.findFragmentById(R.id.fragmentContainerPrescription);

                if (createPrescription == null) {
                    createPrescription = new CreatePrescription();
                    fm.beginTransaction().replace(R.id.fragmentContainerPrescription, createPrescription).commit();
                }
            }
        });

        loadingDrugs = v.findViewById(R.id.ProgressBar_LoadingDrugs);

        RecyclerView rv = v.findViewById(R.id.drugs_container_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) getActivity();
        DrugsContainersAdapter drugsContainersAdapter = new DrugsContainersAdapter(writingPrescriptionActivity.getSelectedDrug());
        rv.setAdapter(drugsContainersAdapter);

        loadingDrugs.setVisibility(View.VISIBLE);

        downloadDataOfDrugsFromTheDatabase();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        nameOfTheDrug.requestFocus();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        nameOfTheDrug.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    PrescriptionDrugObject prescriptionDrugObject = new PrescriptionDrugObject();
                    prescriptionDrugObject.setNameOfTheDrug(nameOfTheDrug.getText().toString());

                    WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) getContext();
                    writingPrescriptionActivity.setSelectedDrug(prescriptionDrugObject);

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DrugsContainers drugsContainers = new DrugsContainers();
                    fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();

                    return true;
                }
                return false;
            }
        });

        backToPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                CreatePrescription createPrescription = new CreatePrescription();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, createPrescription).commit();
            }
        });
        addDrugsFromTheList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loadingDrugs.getVisibility() == View.INVISIBLE){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DrugData drugData = new DrugData();
                    fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugData).commit();
                }
                else{
                    Toast.makeText(writingPrescriptionActivity, "Loading database ............", Toast.LENGTH_SHORT).show();
                }

            }
        });

       /* addDrugsManuallyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(loadingDrugs.getVisibility() == View.INVISIBLE){
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    AddDrugsManually addDrugsManually = new AddDrugsManually();
                    fm.beginTransaction().replace(R.id.fragmentContainerPrescription, addDrugsManually).commit();
                }
                else{
                    Toast.makeText(writingPrescriptionActivity, "Loading database ............", Toast.LENGTH_SHORT).show();
                }
            }
        });*/

        return v;
    }

    public void downloadDataOfDrugsFromTheDatabase(){

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("database_of_drugs");
        ArrayList<DatabaseDrugObject> listOfDrugData = new ArrayList<>();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    DatabaseDrugObject s = ds.getValue(DatabaseDrugObject.class);
                    listOfDrugData.add(s);
                }

                WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) getContext();
                writingPrescriptionActivity.setListOfDrugData(listOfDrugData);

                loadingDrugs.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }
}