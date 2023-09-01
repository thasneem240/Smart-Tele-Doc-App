package com.example.capstoneprojectgroup4.prescriptions;

public class MedicineObject {
    private String name;
    private float price;
    private int qty;

    public MedicineObject(String name, float price, int qty) {
        this.name = name;
        this.price = price;
        this.qty = qty;
    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        return price;
    }

    public int getQty() {
        return qty;
    }
}
