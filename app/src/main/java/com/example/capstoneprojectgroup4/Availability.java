package com.example.capstoneprojectgroup4;

public class Availability {
    private String doctorName;
    private String location;
    private String day;
    private String noApp;
    private String endTime;
    private String startTime;

    public Availability(String doctorName, String location, String day, String noApp, String endTime, String startTime) {
        this.doctorName = doctorName;
        this.location = location;
        this.day = day;
        this.noApp = noApp;
        this.endTime = endTime;
        this.startTime = startTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getLocation() {
        return location;
    }
    public String getNoApp() {
        return noApp;
    }

    public String getDay() {
        return day;
    }
    @Override
    public String toString() {
        return "SessionObject{" +
                "doctorName='" + doctorName + '\'' +
                ", location='" + location + '\'' +
                ", day='" + day + '\'' +
                ", noApp='" + noApp + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startTime='" + startTime + '\'' +
                '}';
    }


}
