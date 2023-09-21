package com.example.capstoneprojectgroup4.ssearch_pharmacy;

import static android.content.ContentValues.TAG;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.search_doctors.SearchDocF;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PharmaciesF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PharmaciesF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button searchButton;
    Toolbar toolbar;

    Button orderButton;

    RadioGroup radioGroup;

    Toolbar toolbar2;

    Button orderButton2;

    RadioGroup radioGroup2;
    TextView etPharmName ;
    int searchType=-1;

    TextView etPharmLocation;
    TextView etPharmDrugs ;
    RecyclerView recyclerView;


    Button search;
    PharmacyAdapter pharmacyAdapter;
    private SearchView searchView;
    FragmentManager fm;


    public PharmaciesF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PharmciesF.
     */
    // TODO: Rename and change types and number of parameters
    public static PharmaciesF newInstance(String param1, String param2) {
        PharmaciesF fragment = new PharmaciesF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

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
        View view = inflater.inflate(R.layout.fragment_pharmacies, container, false);

        etPharmName= view.findViewById(R.id.searchPharmName);
        etPharmLocation = view.findViewById(R.id.searchPharmLoc);
        etPharmDrugs = view.findViewById(R.id.searchDrugs);
        recyclerView = view.findViewById(R.id.pharmrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView backButton = view.findViewById(R.id.backButtonPharma);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fm = getActivity().getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        search = view.findViewById(R.id.pharmsearchButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        return view;
    }

    private void performSearch() {
        String nameEd = etPharmName.getText().toString().trim();
        String drugsEd = etPharmDrugs.getText().toString().trim();
        String locationEd = etPharmLocation.getText().toString().trim();

        Query query = FirebaseDatabase.getInstance().getReference().child("Pharmacies");

        if (!nameEd.isEmpty() && locationEd.isEmpty())
        {
            Log.d(TAG, "Search by Name: " + nameEd);
            searchType = 0;
        }else {
            Log.d(TAG, "Search by Location: " + locationEd);
            searchType = 1;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Pharmacy> pharmacies = new ArrayList<>();
                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String name = (String) doctorSnapshot.child("Name").getValue();
                    String location = (String) doctorSnapshot.child("Address").getValue();
                    String phoneNum = (String) doctorSnapshot.child("PhoneNumber").getValue();

                    if (searchType == 0 && name != null && name.toLowerCase().contains(nameEd.toLowerCase())) {
                        com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy doctor = new com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy(name, location, phoneNum);
                        pharmacies.add(doctor);
                    } else if (searchType == 1 && location != null && location.toLowerCase().contains(locationEd.toLowerCase())) {
                        com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy doctor = new com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy(name, location, phoneNum);
                        pharmacies.add(doctor);

                    }
                }
                PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(pharmacies);
                recyclerView.setAdapter(pharmacyAdapter);
                pharmacyAdapter.notifyDataSetChanged();




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });




    }
}