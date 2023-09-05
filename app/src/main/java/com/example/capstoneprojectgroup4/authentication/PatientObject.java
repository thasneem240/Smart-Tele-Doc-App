package com.example.capstoneprojectgroup4.authentication;

public class PatientObject {
    private String firstName;
    private String lastName;
    private String nic;
    private String mobileNumber;
    private String fullName; // remove
    private String dob;
    private String gender;
    private String address;
    private String city;
    private String country;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "PatientObject{" +
                "fullName='" + fullName + '\'' +
                ", nic='" + nic + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", dob='" + dob + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
