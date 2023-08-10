package com.example.capstoneprojectgroup4;

import java.util.ArrayList;

public class Doctors {

    String Name;
    String Specialization;
    String Location;
    String Location2;

    private ArrayList<String> Locations;


    private String Location_Location2; // New field to combine Location and Location2

    // Constructors, getters, setters, etc.

    public String getLocation_Location2() {
        // Check if both Location and Location2 are not null
        if (Location != null && Location2 != null) {
            // Combine both Location and Location2 with a newline separator
            return Location + "\n" + Location2;
        } else if (Location != null) {
            // If Location2 is null, return only Location
            return Location;
        } else if (Location2 != null) {
            // If Location is null, return only Location2
            return Location2;
        } else {
            // If both Location and Location2 are null, return an empty string or some default value
            return "";
        }
    }
    Doctors()
    {

    }



    public Doctors(String Name, String Specialization, ArrayList<String> Locations)
    {
        this.Name = Name;
        this.Specialization = Specialization;
        this.Locations = Locations;


    }


    public String getSpecialization() {
        return Specialization;
    }

    public String getName() {
        return Name;
    }
    public ArrayList<String> getLocations() {
        return Locations;
    }
    public String getLocation() {
        return Location;
    }

    public String getLocation2() {
        return Location2;
    }

}


