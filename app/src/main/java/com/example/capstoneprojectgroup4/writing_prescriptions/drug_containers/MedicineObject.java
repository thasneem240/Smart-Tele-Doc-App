package com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers;

public class MedicineObject {
    private float price;
    private int qty;

    public MedicineObject(float price, int qty) {
        this.price = price;
        this.qty = qty;
    }
    public float getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }
}
