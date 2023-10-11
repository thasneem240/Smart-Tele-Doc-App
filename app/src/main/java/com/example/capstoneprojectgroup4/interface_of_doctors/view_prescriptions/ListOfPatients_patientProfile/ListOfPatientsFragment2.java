package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOfPatients_patientProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_writingPrescription.AppointmentObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMainMenu;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfPatientsFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfPatientsFragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView rv;

    public ListOfPatientsFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListOfPatientsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOfPatientsFragment2 newInstance(String param1, String param2) {
        ListOfPatientsFragment2 fragment = new ListOfPatientsFragment2();
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
        View v = inflater.inflate(R.layout.fragment_list_of_patients, container, false);

        ImageView backButton = v.findViewById(R.id.ImageView_backButton);
        Button searchButton = v.findViewById(R.id.button_searchPatients);
        TextInputEditText searchNameEditText = v.findViewById(R.id.textInputEditText_searchName);

        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        ArrayList<AppointmentObject> appointmentObjectArrayList = new ArrayList<>();

        String sanitizedDoctorName = DoctorsActivity.getDoctorObject().getName().replaceAll("[.#$\\[\\]]", "_");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Doctor Appointments").child(sanitizedDoctorName);

        rv = v.findViewById(R.id.RecyclerView_ListOfPatients);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));


        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshotChild : dataSnapshot.getChildren()){
                    AppointmentObject appointmentObject = dataSnapshotChild.getValue(AppointmentObject.class);
                    appointmentObjectArrayList.add(appointmentObject);
                }

                if(appointmentObjectArrayList.isEmpty()){
                    Toast.makeText(getActivity(), "There are no appointments under this registration number yet.", Toast.LENGTH_SHORT).show();

                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DoctorMainMenu searchDoctors = new DoctorMainMenu();
                    fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, searchDoctors).commit();
                }
                else{

                    ListOfPatientsAdapter2 listOfPatientsAdapter2 = new ListOfPatientsAdapter2(appointmentObjectArrayList);
                    rv.setAdapter(listOfPatientsAdapter2);
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Error in the database. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = searchNameEditText.getText().toString().toLowerCase();
                ArrayList<AppointmentObject> filteredAppointmentObjects = filterPatientNames(appointmentObjectArrayList, searchText);
                ListOfPatientsAdapter2 filteredAdapter = new ListOfPatientsAdapter2(filteredAppointmentObjects);
                rv.setAdapter(filteredAdapter);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMainMenu doctorMainMenu = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMainMenu).commit();
            }
        });

        return v;
    }

    private ArrayList<AppointmentObject> filterPatientNames(ArrayList<AppointmentObject> appointmentObjectArrayList, String searchText) {
        ArrayList<AppointmentObject> filteredAppointmentObjects = new ArrayList<>();

        for(AppointmentObject o : appointmentObjectArrayList){
            if(o.getPatientName().toLowerCase().contains(searchText)){
                filteredAppointmentObjects.add(o);
            }
        }

        return filteredAppointmentObjects;
    }
}