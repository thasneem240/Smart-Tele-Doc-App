package com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_DoctorsView;

public class AppoinmentObject {
    private int AppointmentNumber;
    private String AppointmentType;
    private String Date;
    private String EndTime;
    private String Location;
    private String PaitentUserId;
    private String PatientEmail;
    private String PatientName;
    private String StartTime;

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

    public String getEndTime() {
        return EndTime;
    }

    public void setEndTime(String endTime) {
        EndTime = endTime;
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

    public void setPatientName(String patientName) {
        PatientName = patientName;
    }

    public String getPaitentUserId() {
        return PaitentUserId;
    }

    public void setPaitentUserId(String paitentUserId) {
        PaitentUserId = paitentUserId;
    }

    public String getStartTime() {
        return StartTime;
    }

    public void setStartTime(String startTime) {
        StartTime = startTime;
    }
}
