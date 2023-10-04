package com.example.capstoneprojectgroup4.search_doctors;

public class Availability {
    private String doctorName;
    private String location;
    private String date;
    private String day;
    private int noApp;
    private String endTime;
    private String startTime;

    public Availability(String doctorName, String location, String day, int noApp, String endTime, String startTime, String date) {
        this.doctorName = doctorName;
        this.location = location;
        this.day = day;
        this.noApp = noApp;
        this.endTime = endTime;
        this.startTime = startTime;
        this.date = date;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }

    public String getDay() {
        return day;
    }

    public int getNoApp() {
        return noApp;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Availability{" +
                "doctorName='" + doctorName + '\'' +
                ", location='" + location + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", noApp=" + noApp +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }
}
