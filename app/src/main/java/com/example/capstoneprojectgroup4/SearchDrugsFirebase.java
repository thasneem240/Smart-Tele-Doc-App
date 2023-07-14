package com.example.capstoneprojectgroup4;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;

public class SearchDrugsFirebase implements Callable<ArrayList<String>> {
    FirebaseDatabase database;

    int pharmacyNumber;
    Map<String, Integer> prescription;
    Map<String, Object> map;
    Map<String, Object> mapPharmacy;
    Map<String, Object> mapMedicine;
    Map<String, Object> qtyAndValue;

    public SearchDrugsFirebase(Map<String, Integer> prescription) {
        this.prescription = prescription;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        ArrayList<String> availablePharmacies = getAvailablePharmacies();

        try {
            Thread.sleep(3000);
        } catch (Exception e) {

        }

        return availablePharmacies;
    }

    private ArrayList<String> getAvailablePharmacies() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        ArrayList<String> availablePharmacies = new ArrayList<>();

        // getPharmacycount through the Firebase
/*        for(int i = 1; i <= 2; i++) {
            try {
                Thread.sleep(3000);
            }
            catch (Exception e){

            }

            pharmacyNumber = i;
            myRef.child("Phar/Pharmacy "+i).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if (task.isSuccessful()) {

                        boolean allTheDrugsAreAvailable = true;

                        for (Map.Entry<String, Integer> entry : prescription.entrySet()){
                                
                                String drug = entry.getKey();
//                                int dosage = entry.getValue();
                            int dosage = 0;

                            Map<String, Object> drugsInEachPharmacy;
                            drugsInEachPharmacy = (Map) task.getResult().getValue();

                            Object temp = 55;
                            String s = String.valueOf(drugsInEachPharmacy.get(drug)); // Check whether that drug available
                            int qty = Integer.valueOf(s);

                            if (qty <= dosage)
                                allTheDrugsAreAvailable = false;
                        }

                        if(allTheDrugsAreAvailable)
                                availablePharmacies.add("Pharmacy "+pharmacyNumber);

                    } else {
                        Log.e("firebase", "Error getting data", task.getException());

                    }
                }
            });*/

        myRef.child("Phar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    map = (Map) task.getResult().getValue();

                    boolean allTheDrugsAreAvailable = true;

                    for (Map.Entry<String, Object> entryL1 : map.entrySet()) {
                        String pharmacy = entryL1.getKey();
                        mapMedicine = (Map) entryL1.getValue();

                        for (Map.Entry<String, Integer> entry : prescription.entrySet()) {

                            String drug = entry.getKey();
                            String s = String.valueOf(entry.getValue());
                            int dosage = Integer.valueOf(s);

                            qtyAndValue = (Map) mapMedicine.get(drug);
                            String sss = String.valueOf(qtyAndValue.get("qty"));
                            int qty = Integer.valueOf(sss);

//                                Map<String, Object> drugsInEachPharmacy;
//                                drugsInEachPharmacy = (Map) task.getResult().getValue();
//
//                                String ss = String.valueOf(drugsInEachPharmacy.get(drug)); // Check whether that drug available
//                                int qty = Integer.valueOf(ss);

                            if (qty <= dosage)
                                allTheDrugsAreAvailable = false;

                        }

                        if (allTheDrugsAreAvailable)
                            availablePharmacies.add(pharmacy);

/*                            for(Map.Entry<String, Object> entryL2 : mapPharmacy.entrySet()){
//                                String medicine = entryL2.getKey();
                                mapMedicine = (Map)entryL2.getValue();

                                String s = String.valueOf(mapMedicine.get("qty"));
                                int qty = Integer.valueOf(s);

//                                String sf1=String.format("%s: %s: qty = %d",pharmacy, medicine, qty);
//                                Log.d("76^^", ""+sf1);

                                if (qty <= dosage)
                                    allTheDrugsAreAvailable = false;

                            }

                            if(allTheDrugsAreAvailable)
                                availablePharmacies.add("Pharmacy "+pharmacyNumber);*/
                    }

                }


            }
        });

        return availablePharmacies;
}
}
