package com.example.capstoneprojectgroup4.writing_prescriptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers.MedicineObject;
import com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers.PrescriptionObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WritingPrescriptionActivity extends AppCompatActivity {
    private PrescriptionObject prescriptionObject = new PrescriptionObject();
    private ArrayList<String> selectedDrug2s = new ArrayList<>();
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

    public ArrayList<String> getSelectedDrug2s() {
        return selectedDrug2s;
    }

    public void setSelectedDrug2s(String drugName) {

        this.selectedDrug2s.add(drugName);
    }

    public PrescriptionObject getPrescriptionObject() {
        return prescriptionObject;
    }

    public void setPrescriptionObject(PrescriptionObject prescriptionObject) {
        this.prescriptionObject = prescriptionObject;
    }
}