package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;

import java.util.ArrayList;

public class WritingPrescriptionActivity extends AppCompatActivity {
    private PrescriptionObject prescriptionObject = new PrescriptionObject();
    private PatientObject patientObject = new PatientObject();
    private ArrayList<PrescriptionDrugObject> selectedDrug = new ArrayList<>();
    private ArrayList<DatabaseDrugObject> listOfDrugData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription);

        PatientObject patientObject = (PatientObject) getIntent().getSerializableExtra("patientObject");
        setPatientObject(patientObject);

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

    public void setSelectedDrug(PrescriptionDrugObject prescriptionDrugObject){
        this.selectedDrug.add(prescriptionDrugObject);
    }

    public void removeSelectedDrug(int position){
        this.selectedDrug.remove(position);
    }

    public PrescriptionObject getPrescriptionObject() {
        return prescriptionObject;
    }

    public void setPrescriptionObject(PrescriptionObject prescriptionObject) {
        this.prescriptionObject = prescriptionObject;
    }

    public void setSelectedDrug(ArrayList<PrescriptionDrugObject> selectedDrug) {
        this.selectedDrug = selectedDrug;
    }

    public ArrayList<DatabaseDrugObject> getListOfDrugData() {
        return listOfDrugData;
    }

    public void setListOfDrugData(ArrayList<DatabaseDrugObject> listOfDrugData) {
        this.listOfDrugData = listOfDrugData;
    }

    public PatientObject getPatientObject() {
        return patientObject;
    }

    public void setPatientObject(PatientObject patientObject) {
        this.patientObject = patientObject;
    }

    public void setWrittenByManually(){
        prescriptionObject.setManuallyWrittenDrugs(true);
    }
}