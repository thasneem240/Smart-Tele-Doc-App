package com.example.capstoneprojectgroup4;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RetriveFirebaseDataF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RetriveFirebaseDataF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Map<String, Object> map;
    Map<String, Object> mapL1;
    Map<String, Object> mapL2;
    Map<String, Object> mapL3;

    public RetriveFirebaseDataF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RetriveFirebaseData.
     */
    // TODO: Rename and change types and number of parameters
    public static RetriveFirebaseDataF newInstance(String param1, String param2) {
        RetriveFirebaseDataF fragment = new RetriveFirebaseDataF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_retrive_firebase_data, container, false);

        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();


        myRef.child("Phar").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    map = (Map) task.getResult().getValue();

                    for(Map.Entry<String, Object> entryL1 : map.entrySet()){
                        String pharmacy = entryL1.getKey();
                        mapL1 = (Map)entryL1.getValue();

                        for(Map.Entry<String, Object> entryL2 : mapL1.entrySet()){
                            String medicine = entryL2.getKey();
                            mapL2 = (Map)entryL2.getValue();

                            String s = String.valueOf(mapL2.get("qty"));
                            int qty = Integer.valueOf(s);

                            String sf1=String.format("%s: %s: qty = %d",pharmacy, medicine, qty);
                            Log.d("76^^", ""+sf1);
                        }

                    }

                }


            }
        });

        return v;
    }
}