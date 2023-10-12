package com.example.capstoneprojectgroup4.interface_of_doctors;


public class DoctorAppointmentItemList {
    private int AppointmentNumber;
    private String AppointmentType;
    private String Date;
    private String Location;
    private String PatientEmail;
    private String PatientName;

    private String EndTime;
    private String StartTime;

    public DoctorAppointmentItemList() {
        // Default constructor required for Firebase Realtime Database
    }


    public DoctorAppointmentItemList(int appointmentNumber, String appointmentType, String date, String location, String patientEmail, String patientName, String StartTime, String EndTime) {
        AppointmentNumber = appointmentNumber;
        AppointmentType = appointmentType;
        Date = date;
        Location = location;
        PatientEmail = patientEmail;
        PatientName = patientName;
        this.EndTime = EndTime;
        this.StartTime = StartTime;
    }

    public int getAppointmentNumber() {
        return AppointmentNumber;
    }

    public void setAppointmentNumber(int appointmentNumber) {
        AppointmentNumber = appointmentNumber;
    }

    public String getAppointmentType() {
        return AppointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        AppointmentType = appointmentType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPatientEmail() {
        return PatientEmail;
    }

    public void setPatientEmail(String patientEmail) {
        PatientEmail = patientEmail;
    }

    public String getPatientName() {
        return PatientName;
    }
    public String getEndTime() {
        return EndTime;
    }
    public String getStartTime() {
        return StartTime;
    }

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getStartTime()
    {
        return StartTime;
    }
}