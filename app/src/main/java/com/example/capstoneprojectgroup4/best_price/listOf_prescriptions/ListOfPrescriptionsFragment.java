package com.example.capstoneprojectgroup4.best_price.listOf_prescriptions;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;
import com.example.capstoneprojectgroup4.writing_prescriptions.PrescriptionObject;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListOfPrescriptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOfPrescriptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<PrescriptionObject> listOfPrescriptions;

    public ListOfPrescriptionsFragment() {
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
    public static ListOfPrescriptionsFragment newInstance(String param1, String param2) {
        ListOfPrescriptionsFragment fragment = new ListOfPrescriptionsFragment();
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
        View v = inflater.inflate(R.layout.fragment_listof_prescriptions, container, false);
        ImageView backButton = v.findViewById(R.id.backButtonViewPrescription);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Prescriptions");
        listOfPrescriptions = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot userSnapshot: snapshot.getChildren()) {
                    PrescriptionObject prescriptionObject = userSnapshot.getValue(PrescriptionObject.class);
                    listOfPrescriptions.add(prescriptionObject);
                }

                RecyclerView rv = v.findViewById(R.id.RecyclerView_ListOfPatients);
                rv.setLayoutManager(new LinearLayoutManager(getContext()));
                ListOfPrescriptionsAdapter listOfPrescriptionsAdapter = new ListOfPrescriptionsAdapter(listOfPrescriptions);
                rv.setAdapter(listOfPrescriptionsAdapter);
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
                PharmaciesF pharmaciesF = new PharmaciesF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, pharmaciesF).commit();
            }
        });
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PharmaciesF pharmaciesF = new PharmaciesF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, pharmaciesF).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


        return v;
    }
}