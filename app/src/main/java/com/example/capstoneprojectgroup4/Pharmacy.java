package com.example.capstoneprojectgroup4;

public class Pharmacy {

    String Name;
    String Address;
    String PhoneNumber;

    Pharmacy()
    {

    }

    public Pharmacy(String Name, String Address, String PhoneNumber)
    {
        this.Name = Name;
        this.Address = Address;
        this.PhoneNumber = PhoneNumber;
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
}
