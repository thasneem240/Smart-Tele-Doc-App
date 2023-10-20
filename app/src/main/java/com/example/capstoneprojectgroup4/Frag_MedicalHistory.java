package com.example.capstoneprojectgroup4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_MedicalHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_MedicalHistory extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String userId;
    private String userType = "Patient";


    private RecyclerView recyclerView;
    private MedicalHistoryAdapter adapter;



    public Frag_MedicalHistory()
    {
        // Required empty public constructor
    }

    public Frag_MedicalHistory(String userType)
    {
        this.userType = userType;
    }




    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_MedicalHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_MedicalHistory newInstance(String param1, String param2) {
        Frag_MedicalHistory fragment = new Frag_MedicalHistory();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_medical_history, container, false);
        ImageView backButton = view.findViewById(R.id.backButtonMedicalHistory);

        // Obtain the RecyclerView UI element
        recyclerView = (RecyclerView) view.findViewById(R.id.medicalHistoryRecyclerView);

        /* Grab the  UI Variables from Layout file */

        if(userType.equalsIgnoreCase("Patient"))
        {
            userId = MainActivity.getPatientObject().getUid();
        }
        else // For Doctors
        {
            userId = DoctorsActivity.getAppointmentObject().getPatientUserId();
        }



        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(userType.equalsIgnoreCase("Patient"))
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    MedicalRecords medicalRecords = new MedicalRecords();
                    fm.beginTransaction().replace(R.id.fragmentContainerView, medicalRecords).commit();
                }
                else
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();
                    fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();
                }



            }
        });


        List<MedicalHistoryItem> medicalHistoryItemList = new ArrayList<>();

        Log.d("OnCreateView:", "After Array List Created");

        // Populate medicalHistoryItems with your data
        //medicalHistoryItems = MedicalHistoryItemGenerator.generateRandomMedicalHistoryItems(200);

        retrieveFromRealTimeDataBase(medicalHistoryItemList);
        Log.d("OnCreateView:", "After Retrieved the Array List");


//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        //Create Adapter for the recyclerview
//        adapter = new MedicalHistoryAdapter(medicalHistoryItemList);
//
//        // Hook it up
//        recyclerView.setAdapter(adapter);


        return  view;


    }



    public void retrieveFromRealTimeDataBase(List<MedicalHistoryItem> medicalHistoryItemList)
    {
        Log.d("retrieveFromRealTimeDataBase:", "Before Get reference");

        // Get a reference to the user's medical records node
        DatabaseReference medicalRecordsRef = FirebaseDatabase.getInstance()
                .getReference("Patient's Medical History").child(userId).child("medicalRecords");

        Log.d("retrieveFromRealTimeDataBase:", "After Get reference");

        // Attach a ValueEventListener to retrieve medical records
        medicalRecordsRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {

                Log.d("onDataChange :", "Before For Loop");

                // Clear the existing list before adding new data
                medicalHistoryItemList.clear();

                // Iterate through the medical records and add them to your list
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Log.d("onDataChange Before snapshot :", "In For Loop");

                    MedicalHistoryItem medicalHistoryItem = snapshot.getValue(MedicalHistoryItem.class);

                    Log.d("onDataChange After snapshot :", "In For Loop");

                    medicalHistoryItemList.add(medicalHistoryItem);
                }

                Log.d("onDataChange :", "Out of For Loop");

                String message = "Successfully Retrieved the Medical History data from " +
                        "Firebase Realtime database";
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                //Create Adapter for the recyclerview
                adapter = new MedicalHistoryAdapter(medicalHistoryItemList);

                // Hook it up
                recyclerView.setAdapter(adapter);



            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

                String message = "Error!! While Retrieving Medical history data " +
                        "from Firebase Realtime database";
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        });



    }


    public void setUserId(String userId) {

        this.userId = userId;
    }

    public void setAdapter(MedicalHistoryAdapter adapter)
    {
        this.adapter = adapter;
    }

    public String getUserId()
    {
        return userId;
    }

    public MedicalHistoryAdapter getAdapter()
    {
        return adapter;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}