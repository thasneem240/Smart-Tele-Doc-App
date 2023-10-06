package com.example.capstoneprojectgroup4.transaction;

import com.google.type.DateTime;

import java.text.DateFormat;

public class TransactionHistoryData {
    private String name;
    private DateFormat date;
    private double price;



    public TransactionHistoryData(){};


    public TransactionHistoryData(String name) {
        this.name = name;


    }




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public DateFormat getDate() { return date; }

    public void setDate(DateFormat date) {this.date = date;}

    public double getPrice() {return price;}

    public void setPrice(double price) {this.price = price;}
}
