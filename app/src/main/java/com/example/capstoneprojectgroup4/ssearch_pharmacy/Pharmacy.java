package com.example.capstoneprojectgroup4.ssearch_pharmacy;

public class Pharmacy {

    String Name;
    String Address;
    String PhoneNumber;

    String Maps;


    Pharmacy()
    {

    }

    public Pharmacy(String Name, String Address, String PhoneNumber, String Maps)
    {
        this.Name = Name;
        this.Address = Address;
        this.PhoneNumber = PhoneNumber;
        this.Maps = Maps;

    }


    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public String getMaps() {
        return Maps;
    }

}
