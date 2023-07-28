package com.example.capstoneprojectgroup4.available_pharmacies;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Callable;

public class AvailablePharmaciesFirebase implements Callable<ArrayList<String>> {
    FirebaseDatabase database;

    int pharmacyNumber;
    Map<String, Integer> prescription;
    Map<String, Object> map;
    Map<String, Object> mapMedicine;
    Map<String, Object> qtyAndValue;

    public AvailablePharmaciesFirebase(Map<String, Integer> prescription) {
        this.prescription = prescription;
    }

    @Override
    public ArrayList<String> call() throws Exception {
        ArrayList<String> availablePharmacies = getAvailablePharmacies();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }

        return availablePharmacies;
    }

    private ArrayList<String> getAvailablePharmacies() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();

        ArrayList<String> availablePharmacies = new ArrayList<>();

/*        myRef.child("Phar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
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

                            if (qty <= dosage)
                                allTheDrugsAreAvailable = false;

                        }

                        if (allTheDrugsAreAvailable)
                            availablePharmacies.add(pharmacy);

                    }

                }


            }
        });

        return availablePharmacies;
}*/
                myRef.child("Phar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    map = (Map) task.getResult().getValue();

                    boolean allTheDrugsAreAvailable = true;

                    for (Map.Entry<String, Object> entryL1 : map.entrySet()) {
                        String pharmacy = entryL1.getKey();
                        mapMedicine = (Map) entryL1.getValue();

                    }

                }


            }
        });

        return availablePharmacies;
}
}
