package com.example.capstoneprojectgroup4.wirting_prescriptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;

import com.example.capstoneprojectgroup4.R;

import java.util.HashMap;
import java.util.Map;

public class PrescriptionActivity extends AppCompatActivity {
    public int containerNo;
    public String selectedDrug;
    private Map<String, Object> prescription;



    private Map<String, Object> selectedDrugs;
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
        Log.d("nnrp", ""+prescriptionIsAvailable);
        if(!prescriptionIsAvailable)
            prescription = new HashMap<>();

        return prescription;
    }

    public void setPrescription(Map<String, Object> prescription) {
        this.prescription = prescription;
        prescriptionIsAvailable = true;

    }
/*    public Map<String, Object> getSelectedDrugs() {
        return selectedDrugs;
    }

    public void setSelectedDrugs(String selectedDrug) {
        this.selectedDrugs = selectedDrugs;
    }*/
}