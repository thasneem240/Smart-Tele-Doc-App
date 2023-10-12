package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other;

import java.util.ArrayList;

public class DatabaseDrugObject {
    private String nameOfTheDrug;
    private ArrayList<String> brandNames;
    private ArrayList<String> availableStrengths;

    public String getNameOfTheDrug() {
        return nameOfTheDrug;
    }

    public void setNameOfTheDrug(String nameOfTheDrug) {
        this.nameOfTheDrug = nameOfTheDrug;
    }

    public ArrayList<String> getBrandNames() {
        return brandNames;
    }

    public void setBrandNames(ArrayList<String> brandNames) {
        this.brandNames = brandNames;
    }

    public ArrayList<String> getAvailableStrengths() {
        return availableStrengths;
    }

    public void setAvailableStrengths(ArrayList<String> availableStrengths) {
        this.availableStrengths = availableStrengths;
    }
}