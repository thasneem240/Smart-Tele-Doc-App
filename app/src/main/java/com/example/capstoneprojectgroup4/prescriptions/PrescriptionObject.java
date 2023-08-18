package com.example.capstoneprojectgroup4.prescriptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PrescriptionObject {
    private String doctorName;
    private String patientName;
    private String dateTime;
    private int treatmentDuration; // treatment duration in days
    private String prescriptionNotes;
    private ArrayList<String> selectedDrugs = new ArrayList<>();


    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setTreatmentDuration(int treatmentDuration) {
        this.treatmentDuration = treatmentDuration;
    }

    public void setPrescriptionNotes(String prescriptionNotes) {
        this.prescriptionNotes = prescriptionNotes;
    }

    public void setSelectedDrugs(ArrayList<String> selectedDrugs) {
        this.selectedDrugs = selectedDrugs;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getTreatmentDuration() {
        return treatmentDuration;
    }

    public String getPrescriptionNotes() {
        return prescriptionNotes;
    }

    public ArrayList<String> getSelectedDrugs() {
        return selectedDrugs;
    }

    @Override
    public String toString() {
        return "PrescriptionObject{" +
                "doctorName='" + doctorName + '\'' +
                ", patientName='" + patientName + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", treatmentDuration=" + treatmentDuration +
                ", prescriptionNotes='" + prescriptionNotes + '\'' +
                ", selectedDrugs=" + selectedDrugs +
                '}';
    }
}
