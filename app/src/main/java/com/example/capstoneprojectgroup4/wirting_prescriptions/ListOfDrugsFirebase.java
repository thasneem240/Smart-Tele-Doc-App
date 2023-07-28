package com.example.capstoneprojectgroup4.wirting_prescriptions;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class ListOfDrugsFirebase implements Callable<ArrayList<String>> {
    ArrayList<String> listOfDrugs;
    Map <String, Object> doctors = new HashMap<>();
    Map <String, Object> detailsOfEachDoctor = new HashMap<>();
    ArrayList<String> locations;

    @Override
    public ArrayList<String> call() throws Exception {
        getListOfDrugs();

/*        FirebaseRecyclerOptions<Doctors> options =
                new FirebaseRecyclerOptions.Builder<Doctors>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors"), Doctors.class)
                        .build();*/

        try {
            Thread.sleep(2000);
        } catch (Exception e) {

        }

        return listOfDrugs;
    }

    private void getListOfDrugs(){
        listOfDrugs = new ArrayList<>();

        Query query = FirebaseDatabase.getInstance().getReference("Drugs");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String s = ds.getKey();
                    listOfDrugs.add(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

/*        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Drugs").get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String s = ds.getKey();
                    listOfDrugs.add(s);
                }
            }
        });*/



        /*
        String[] cars = {"Acetaminophen","Adderall","Amitriptyline","Amlodipine","Amoxicillin","Ativan","Atorvastatin","Azithromycin","Benzonatate","Brilinta","Bunavail","Buprenorphine","Cephalexin","Ciprofloxacin","Citalopram","Clindamycin","Clonazepam","Cyclobenzaprine","Cymbalta","Doxycycline","Dupixent","Entresto","Entyvio","Farxiga","Fentanyl Patch","Gabapentin","Gilenya","Humira","Hydrochlorothiazide","Hydroxychloroquine","Ibuprofen","Imbruvica","Invokana","Januvia","Jardiance","Kevzara","Lexapro","Lisinopril","Lofexidine","Loratadine","Lyrica","Melatonin","Meloxicam","Metformin","Methadone","Methotrexate","Metoprolol","Naloxone","Naltrexone","Naproxen","Narcan","Nurtec","Omeprazole","Onpattro","Otezla","Ozempic","Pantoprazole","Plan B","Prednisone","Probuphine","Rybelsus","secukinumab","Sublocade","Tramadol","Trazodone","Viagra","Wegovy","Wellbutrin","Xanax","Zubsolv"};

        for(String s:cars){
            myRef.child("Drugs").child(s).setValue(true);
        }*/
    }

}
