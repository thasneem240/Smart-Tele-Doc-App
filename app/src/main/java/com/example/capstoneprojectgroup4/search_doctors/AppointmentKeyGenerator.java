package com.example.capstoneprojectgroup4.search_doctors;

import java.util.UUID;

public class AppointmentKeyGenerator {
    private static String appointmentKey;

    public static String generateAppointmentKey(String patientKey, String DocName, String location,String date) {


        String randomString = UUID.randomUUID().toString();
        appointmentKey = patientKey + "_" + DocName + "_" + location + "_" + date + "_" + randomString ;
        return appointmentKey;
    }

    public static String getAppointmentKey() {
        return appointmentKey;
    }
    public static void setAppointmentKey(String appointmentKey) {
        AppointmentKeyGenerator.appointmentKey = appointmentKey;
    }
}