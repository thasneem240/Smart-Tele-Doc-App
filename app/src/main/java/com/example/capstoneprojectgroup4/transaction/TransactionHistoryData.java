package com.example.capstoneprojectgroup4.transaction;

import com.google.type.DateTime;

import java.text.DateFormat;

public class TransactionHistoryData {
    private String name;
    private String date;
    private String price;


    private String description;

    private String patientID;



    public TransactionHistoryData(){};



    public TransactionHistoryData(String name, String date, String price, String description, String patientID) {
        this.name = name;
        this.date = date;
        this.price = price;
        this.description = description;
        this.patientID = patientID;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {return date;}

    public void setDate(String date) {this.date = date;}

    public String getPrice() {return price;}

    public void setPrice(String price) {this.price = price;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public String getPatientID() {return patientID;}

    public void setPatientID(String patientID) {this.patientID = patientID;}

}

