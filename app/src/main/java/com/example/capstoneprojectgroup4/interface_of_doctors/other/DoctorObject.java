package com.example.capstoneprojectgroup4.interface_of_doctors.other;

import java.util.ArrayList;

public class DoctorObject {
    private ArrayList<String> Locations;
    private String Name;
    private String Specialization;

    public ArrayList<String> getLocations() {
        return Locations;
    }

    public void setLocations(ArrayList<String> locations) {
        Locations = locations;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }
}
