package com.example.capstoneprojectgroup4;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PrescriptionItemGenerator
{
    private static final String[] medications =
            {
                    "Aspirin",
                    "Ibuprofen",
                    "Acetaminophen (Paracetamol)",
                    "Penicillin",
                    "Amoxicillin",
                    "Ciprofloxacin",
                    "Prednisone",
                    "Lisinopril",
                    "Atorvastatin",
                    "Metformin",
                    "Panadol"

            };


    private static final String[] dosages =
            {
                    "10 mg",
                    "20 mg",
                    "30 mg",
                    "40 mg",
                    "50 mg",
                    "1 tablet",
                    "2 tablets",
                    "3 tablets",
                    "1 capsule",
                    "2 capsules"
            };

    public static List<PrescriptionItem> generateRandomPrescriptionItems(int count)
    {
        List<PrescriptionItem> prescriptionItems = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++)
        {
            String medication = medications[random.nextInt(medications.length)];
            String dosage = dosages[random.nextInt(dosages.length)];
            prescriptionItems.add(new PrescriptionItem(medication, dosage));
        }

        return prescriptionItems;
    }


}
