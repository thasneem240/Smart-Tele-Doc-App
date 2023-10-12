package com.example.capstoneprojectgroup4.best_price;

public class PharmacyDrugObject {
    private String nameOfTheDrug;
    private String brandName;
    private String strength;
    private boolean availability;
    private float price;

    public String getNameOfTheDrug() {
        return nameOfTheDrug;
    }

    public void setNameOfTheDrug(String nameOfTheDrug) {
        this.nameOfTheDrug = nameOfTheDrug;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getStrength() {
        return strength;
    }

    public void setStrength(String strength) {
        this.strength = strength;
    }

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
