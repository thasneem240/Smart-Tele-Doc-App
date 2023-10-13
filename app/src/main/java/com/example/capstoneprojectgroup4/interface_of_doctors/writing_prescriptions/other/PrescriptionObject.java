package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other;

import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;

import java.util.ArrayList;

public class PrescriptionObject {
    private String patientName;
    private String doctorName;
    private String dob;
    private String writtenOn;
    private String prescriptionNotes;
    private boolean manuallyWrittenDrugs;
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

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPrescriptionNotes() {
        return prescriptionNotes;
    }

    public void setPrescriptionNotes(String prescriptionNotes) {
        this.prescriptionNotes = prescriptionNotes;
    }

    public boolean isManuallyWrittenDrugs() {
        return manuallyWrittenDrugs;
    }

    public void setManuallyWrittenDrugs(boolean manuallyWrittenDrugs) {
        this.manuallyWrittenDrugs = manuallyWrittenDrugs;
    }
}
