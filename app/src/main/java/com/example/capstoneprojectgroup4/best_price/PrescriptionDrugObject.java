package com.example.capstoneprojectgroup4.best_price;

public class PrescriptionDrugObject {
    private String nameOfTheDrug;
    private String brandName;
    private String strength;
    private int amount;

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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
