package com.example.capstoneprojectgroup4.prescriptions.view_prescriptions;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.available_pharmacies.AvailablePharmaciesAdapter;
import com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers.PrescriptionObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPrescriptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPrescriptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Map<String, PrescriptionObject> prescriptionList;
    ArrayList<PrescriptionObject> prescriptionsListRebuilt;

    public ViewPrescriptionsFragment() {
        // Required empty public constructor
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
    public static ViewPrescriptionsFragment newInstance(String param1, String param2) {
        ViewPrescriptionsFragment fragment = new ViewPrescriptionsFragment();
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
        View v = inflater.inflate(R.layout.fragment_view_prescriptions, container, false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Prescriptions3");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prescriptionList =  (Map) snapshot.getValue();
                Log.d(TAG, ""+prescriptionList);

                prescriptionsListRebuilt = new ArrayList<>();
                for(Map.Entry<String, Object> entry : prescriptions.entrySet()){
                    prescriptionsList.add(entry);
                }

                for()

                RecyclerView rv = v.findViewById(R.id.recycler_view_prescriptions);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                ViewPrescriptionsAdapter viewPrescriptionsAdapter = new ViewPrescriptionsAdapter(prescriptionsList);
                rv.setAdapter(viewPrescriptionsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        /*DatabaseReference myRef = database.getReference("Prescriptions2");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                prescriptions = (Map) snapshot.getValue();

//                int i = 0;
//
//                prescriptionsV2 = new HashMap<>();
//                for(Map.Entry<String, Object> entry : prescriptions.entrySet()){
//                    prescriptionsV2.put(i, entry.getValue());
//                    i++;
//                }

                ArrayList<Map.Entry<String, Object>> prescriptionsList = new ArrayList<>();
                for(Map.Entry<String, Object> entry : prescriptions.entrySet()){
                    prescriptionsList.add(entry);
                }

                RecyclerView rv = v.findViewById(R.id.recycler_view_prescriptions);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                ViewPrescriptionsAdapter viewPrescriptionsAdapter = new ViewPrescriptionsAdapter(prescriptionsList);
                rv.setAdapter(viewPrescriptionsAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        return v;
    }
}