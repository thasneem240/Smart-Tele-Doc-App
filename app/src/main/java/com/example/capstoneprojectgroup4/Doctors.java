package com.example.capstoneprojectgroup4;

public class Doctors {

    String Name;
    String Specialization;
    String Location;

    Doctors()
    {

    }

    public Doctors(String Name, String Specialization, String Location)
    {
        this.Name = Name;
        this.Specialization = Specialization;
        this.Location = Location;
    }


    public String getSpecialization() {
        return Specialization;
    }

    public String getName() {
        return Name;
    }

    public String getLocation() {
        return Location;
    }
}
