package com.example.capstoneprojectgroup4.wirting_prescriptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WritingPrescriptionActivity extends AppCompatActivity {
    private Map<String, Object> prescription;
    private Map<String, Integer> selectedDrugs = new HashMap<>();
    Boolean prescriptionIsAvailable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        FragmentManager fm = getSupportFragmentManager();
        CreatePrescriptionFragment createPrescriptionFragment = (CreatePrescriptionFragment) fm.findFragmentById(R.id.fragmentContainerPrescription);

        if (createPrescriptionFragment == null) {
            createPrescriptionFragment = new CreatePrescriptionFragment();
            fm.beginTransaction().replace(R.id.fragmentContainerPrescription, createPrescriptionFragment).commit();
        }
    }
    public Map<String, Object> getPrescription() {
        if(!prescriptionIsAvailable)
            prescription = new HashMap<>();

        return prescription;
    }

    public void setPrescription(Map<String, Object> prescription) {
        this.prescription = prescription;
        prescriptionIsAvailable = true;

    }

    public void setSelectedDrugs(String drugName, Integer dosage) {
        this.selectedDrugs.put(drugName, dosage);
    }
    public ArrayList<Map.Entry<String, Integer>> getSelectedDrugs() {
        ArrayList<Map.Entry<String, Integer>> selectedDrugsList = new ArrayList<>();
        for(Map.Entry<String, Integer> entry : selectedDrugs.entrySet()){
            selectedDrugsList.add(entry);
        }
        return selectedDrugsList;
    }
}