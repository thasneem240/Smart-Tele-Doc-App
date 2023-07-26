package com.example.capstoneprojectgroup4.wirting_prescriptions;

import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ListOfDrugsFirebase implements Callable<ArrayList<String>> {
    ArrayList<String> listOfDrugs;

    @Override
    public ArrayList<String> call() throws Exception {
        getListOfDrugs();

        try {
            Thread.sleep(4000);
        } catch (Exception e) {

        }
        Log.d("nnrp", "List of drugs "+listOfDrugs);

        return listOfDrugs;
    }

    private void getListOfDrugs(){
        listOfDrugs = new ArrayList<>();

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Drugs").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String s = ds.getKey();
                    listOfDrugs.add(s);
                }
            }
        });



        /*
        String[] cars = {"Acetaminophen","Adderall","Amitriptyline","Amlodipine","Amoxicillin","Ativan","Atorvastatin","Azithromycin","Benzonatate","Brilinta","Bunavail","Buprenorphine","Cephalexin","Ciprofloxacin","Citalopram","Clindamycin","Clonazepam","Cyclobenzaprine","Cymbalta","Doxycycline","Dupixent","Entresto","Entyvio","Farxiga","Fentanyl Patch","Gabapentin","Gilenya","Humira","Hydrochlorothiazide","Hydroxychloroquine","Ibuprofen","Imbruvica","Invokana","Januvia","Jardiance","Kevzara","Lexapro","Lisinopril","Lofexidine","Loratadine","Lyrica","Melatonin","Meloxicam","Metformin","Methadone","Methotrexate","Metoprolol","Naloxone","Naltrexone","Naproxen","Narcan","Nurtec","Omeprazole","Onpattro","Otezla","Ozempic","Pantoprazole","Plan B","Prednisone","Probuphine","Rybelsus","secukinumab","Sublocade","Tramadol","Trazodone","Viagra","Wegovy","Wellbutrin","Xanax","Zubsolv"};

        for(String s:cars){
            myRef.child("Drugs").child(s).setValue(true);
        }*/
    }

}
