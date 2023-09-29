package com.example.capstoneprojectgroup4.writing_prescriptions;

import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;

import java.util.ArrayList;

public class PrescriptionObject {
    private String doctorName;
    private String patientName;
    private String writtenOn;
    private ArrayList<PrescriptionDrugObject> selectedDrugs = new ArrayList<>();

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getWrittenOn() {
        return writtenOn;
    }

    public void setWrittenOn(String writtenOn) {
        this.writtenOn = writtenOn;
    }

    public ArrayList<PrescriptionDrugObject> getSelectedDrugs() {
        return selectedDrugs;
    }

    public void setSelectedDrugs(ArrayList<PrescriptionDrugObject> selectedDrugs) {
        this.selectedDrugs = selectedDrugs;
    }
}
