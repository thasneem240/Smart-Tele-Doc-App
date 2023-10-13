package com.example.capstoneprojectgroup4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MedicalHistoryItemGenerator
{
    private static final String[] descriptions =
            {
                    "Flu treatment",
                    "Broken arm",
                    "Annual checkup",
                    "Prescription refill",
                    "Dental cleaning",
                    "X-ray examination",
                    "Allergy diagnosis",
                    "Physical therapy",
                    "Vaccination",
                    "Chronic pain management"
            };

    public static List<MedicalHistoryItem> generateRandomMedicalHistoryItems(int count)
    {
        List<MedicalHistoryItem> medicalHistoryItems = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++)
        {
            String date = generateRandomDate();
            String description = descriptions[random.nextInt(descriptions.length)];
            medicalHistoryItems.add(new MedicalHistoryItem(date, description));
        }

        return medicalHistoryItems;
    }

    private static String generateRandomDate()
    {
        Random random = new Random();
        int year = 2010 + random.nextInt(12); // Generate years from 2010 to 2021
        int month = 1 + random.nextInt(12); // Generate months from 1 to 12
        int day = 1 + random.nextInt(28); // Generate days from 1 to 28 (for simplicity)

        return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
    }


}


