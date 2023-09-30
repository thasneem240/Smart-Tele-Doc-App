package com.example.capstoneprojectgroup4.writing_prescriptions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;

import java.util.ArrayList;

public class WritingPrescriptionActivity extends AppCompatActivity {
    private PrescriptionObject prescriptionObject = new PrescriptionObject();
    private ArrayList<PrescriptionDrugObject> selectedDrug = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        FragmentManager fm = getSupportFragmentManager();
        CreatePrescription createPrescription = (CreatePrescription) fm.findFragmentById(R.id.fragmentContainerPrescription);

        if (createPrescription == null) {
            createPrescription = new CreatePrescription();
            fm.beginTransaction().replace(R.id.fragmentContainerPrescription, createPrescription).commit();
        }
    }

    public ArrayList<PrescriptionDrugObject> getSelectedDrug() {
        return selectedDrug;
    }

//    public void setSelectedDrug(String drugName, String brandName, String strength, String notes) {
//        PrescriptionDrugObject prescriptionDrugObject = new PrescriptionDrugObject();
//        prescriptionDrugObject.setNameOfTheDrug(drugName);
//        prescriptionDrugObject.setBrandName(brandName);
//        prescriptionDrugObject.setStrength(strength);
//        prescriptionDrugObject.setDrugSpecificNotes(notes);
//
//        this.selectedDrug.add(prescriptionDrugObject);
//    }

    public void setSelectedDrug(PrescriptionDrugObject prescriptionDrugObject){
        this.selectedDrug.add(prescriptionDrugObject);
    }

    public PrescriptionObject getPrescriptionObject() {
        return prescriptionObject;
    }

    public void setPrescriptionObject(PrescriptionObject prescriptionObject) {
        this.prescriptionObject = prescriptionObject;
    }
}