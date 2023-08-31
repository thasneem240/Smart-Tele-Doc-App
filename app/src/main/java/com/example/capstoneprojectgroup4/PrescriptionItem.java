package com.example.capstoneprojectgroup4;

// PrescriptionItem.java
public class PrescriptionItem
{
    private String medication;
    private String dosage;

    public PrescriptionItem(String medication, String dosage)
    {
        this.medication = medication;
        this.dosage = dosage;
    }

    public String getMedication()
    {
        return medication;
    }

    public String getDosage()
    {
        return dosage;
    }
}

