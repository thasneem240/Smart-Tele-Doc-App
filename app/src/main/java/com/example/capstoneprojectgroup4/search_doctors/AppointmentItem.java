package com.example.capstoneprojectgroup4.search_doctors;

public class AppointmentItem {
    private String PatientName;
    private String PatientEmail;
    private String DoctorName;

    private String Location;
    private int AppointmentNumber;


    private String AppointmentType;
    private String Date;
    private String EndTime;
    private String StartTime;
    private String appointmentKey;
    private String patientKey;


    public AppointmentItem() {
        // Default constructor required for Firebase
    }

    public AppointmentItem(String patientName, String patientEmail, String doctorName, String appointmentType, String date, String StartTime, String EndTime, String appointmentKey, String patientKey, String Location, int AppointmentNumber) {
        this.PatientName = patientName;
        this.PatientEmail = patientEmail;
        this.DoctorName = doctorName;
        this.AppointmentType = appointmentType;
        this.Date = date;
        this.EndTime = EndTime;
        this.StartTime = StartTime;
        this.appointmentKey = appointmentKey;
        this.patientKey = patientKey;
        this.Location = Location;
        this.AppointmentNumber = AppointmentNumber;

    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        this.StartTime = startTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        this.EndTime = endTime;
    }
    public String getPatientName() {
        return PatientName;
    }

    public void setPatientName(String patientName) {
        this.PatientName = patientName;
    }

    public String getPatientEmail() {
        return PatientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        this.PatientEmail = patientEmail;
    }

    public String getDoctorName() {
        return DoctorName;
    }

    public void setDoctorName(String doctorName) {
        this.DoctorName = doctorName;
    }

    public String getAppointmentType() {
        return AppointmentType;
    }

    public String getLocation() {
        return Location;
    }

    public int getAppointmentNumber() {
        return AppointmentNumber;
    }

    public void setAppointmentType(String appointmentType) {
        this.AppointmentType = appointmentType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    // Getter and Setter methods for appointment key
    public String getAppointmentKey() {
        return appointmentKey;
    }

    public void setAppointmentKey(String appointmentKey) {
        this.appointmentKey = appointmentKey;
    }

    // Getter and Setter methods for patient key
    public String getPatientKey() {
        return patientKey;
    }

    public void setPatientKey(String patientKey) {
        this.patientKey = patientKey;
    }



}
