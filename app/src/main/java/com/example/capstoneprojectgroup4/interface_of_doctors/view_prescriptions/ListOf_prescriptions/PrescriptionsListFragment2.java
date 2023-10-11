package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOf_prescriptions;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PrescriptionsListFragment2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PrescriptionsListFragment2 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<PrescriptionObject> listOfPrescriptions;
    String patientUID;

    public PrescriptionsListFragment2() {
        // Required empty public constructor
    }
    public PrescriptionsListFragment2(String patientUID){
        this.patientUID = patientUID;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewPrescriptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PrescriptionsListFragment2 newInstance(String param1, String param2) {
        PrescriptionsListFragment2 fragment = new PrescriptionsListFragment2();
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
        View v = inflater.inflate(R.layout.fragment_prescriptions_list, container, false);
        ImageView backButton = v.findViewById(R.id.backButtonViewPrescription);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Prescriptions").child(DoctorsActivity.getAppointmentObject().getPatientUserId()).
                child("Prescriptions");
        listOfPrescriptions = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    PrescriptionObject prescriptionObject = userSnapshot.getValue(PrescriptionObject.class);
                    if(prescriptionObject.getDoctorName().equals(DoctorsActivity.getDoctorObject().getName())){
                        listOfPrescriptions.add(prescriptionObject);
                    }
                }
                
                if(listOfPrescriptions.isEmpty()){
                    Toast.makeText(getActivity(), "There are no prescriptions for this patient.", Toast.LENGTH_SHORT).show();
                }
                else{
                    RecyclerView rv = v.findViewById(R.id.RecyclerView_ListOfPatients);
                    rv.setLayoutManager(new LinearLayoutManager(getContext()));
                    PrescriptionsListAdapter2 prescriptionsListAdapter2 = new PrescriptionsListAdapter2(listOfPrescriptions);
                    rv.setAdapter(prescriptionsListAdapter2);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Error adding data: " + error);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        return v;
    }
}