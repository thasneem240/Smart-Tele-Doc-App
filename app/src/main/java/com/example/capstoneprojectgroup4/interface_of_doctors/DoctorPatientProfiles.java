package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMainMenu;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/*
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorPatientProfiles#newInstance} factory method to
 * create an instance of this fragment.*/


public class DoctorPatientProfiles extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorPatientProfiles() {
        // Required empty public constructor
    }

/*
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorPatientProfiles.*/


    // TODO: Rename and change types and number of parameters
    public static DoctorPatientProfiles newInstance(String param1, String param2) {
        DoctorPatientProfiles fragment = new DoctorPatientProfiles();
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
        View v = inflater.inflate(R.layout.fragment_doctor_patient_profiles, container, false);

        ImageView back = v.findViewById(R.id.backButtonDocPatProf);
        RecyclerView rv = v.findViewById(R.id.listPatientsrv);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        EditText searchEditText = v.findViewById(R.id.ListPatsearchName);
        Button search = v.findViewById(R.id.searchPatientList);

        String name = DoctorsActivity.getDoctorObject().getName();
        String sanitizedDoctorName = name.replaceAll("[.#$\\[\\]]", "_");


        DatabaseReference doctorAppointmentsRef = FirebaseDatabase.getInstance().getReference("Doctor Appointments");
        DatabaseReference drAjithRef = doctorAppointmentsRef.child(sanitizedDoctorName);

        drAjithRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<String> patientNames = new ArrayList<>();
                    for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                        String patientName = appointmentSnapshot.child("PatientName").getValue(String.class);
                        if (patientName != null) {
                            patientNames.add(patientName);
                        }
                    }

                    // Create and set the initial adapter with all patient names
                    PatientListAdapter adapter = new PatientListAdapter(patientNames);
                    rv.setAdapter(adapter);

                    search.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String searchText = searchEditText.getText().toString().toLowerCase();
                            List<String> filteredNames = filterPatientNames(patientNames, searchText);
                            adapter.setPatientNames(filteredNames);
                            adapter.notifyDataSetChanged();
                        }
                    });
                } else {
                    Log.d("DoctorPatientProfiles", "No data found for Dr. Ajith Amarasinghe.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("DoctorPatientProfiles", "Database error: " + databaseError.getMessage());
            }
        });






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMainMenu doctorAvailability = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorAvailability).commit();
            }
        });

        return v;
    }

    private List<String> filterPatientNames(List<String> patientNames, String searchText) {
        List<String> filteredNames = new ArrayList<>();
        for (String name : patientNames) {
            if (name.toLowerCase().contains(searchText.toLowerCase())) {
                filteredNames.add(name);
            }
        }
        return filteredNames;
    }


}
