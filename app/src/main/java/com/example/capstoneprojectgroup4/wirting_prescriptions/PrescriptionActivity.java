package com.example.capstoneprojectgroup4.wirting_prescriptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrescriptionActivity extends AppCompatActivity {
    private Map<String, Object> prescription;
    private ArrayList<String> selectedDrugs = new ArrayList<>();
    Boolean prescriptionIsAvailable = false;

    public int numberOfContainers = 1;

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
    public ArrayList<String> getSelectedDrugs() {
        return selectedDrugs;
    }

    public void setSelectedDrugs(String selectedDrug) {
        selectedDrugs.add(selectedDrug);
    }
}