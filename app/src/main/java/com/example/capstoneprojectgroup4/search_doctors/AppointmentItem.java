package com.example.capstoneprojectgroup4.search_doctors;

public class AppointmentItem {
    private String PatientName;
    private String PatientEmail;
    private String DoctorName;
    private String AppointmentType;
    private String Date;
    private String EndTime;
    private String StartTime;


    public AppointmentItem() {
        // Default constructor required for Firebase
    }

    public AppointmentItem(String patientName, String patientEmail, String doctorName, String appointmentType, String date,String StartTime, String EndTime) {
        this.PatientName = patientName;
        this.PatientEmail = patientEmail;
        this.DoctorName = doctorName;
        this.AppointmentType = appointmentType;
        this.Date = date;
        this.EndTime = EndTime;
        this.StartTime = StartTime;

    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
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

    public void setAppointmentType(String appointmentType) {
        this.AppointmentType = appointmentType;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }


}
