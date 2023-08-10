package com.example.capstoneprojectgroup4;

import android.app.SearchManager;
import android.app.appsearch.SearchResults;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

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


    Toolbar toolbar;

    Button orderButton;


    RecyclerView recyclerView;
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
        View v = inflater.inflate(R.layout.fragment_pharmacies, container, false);


        toolbar = v.findViewById(R.id.toolbar);
        orderButton = v.findViewById(R.id.orderB);

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                Delivery_Track welcomeF = new Delivery_Track();
               fm.beginTransaction().replace(R.id.fragment_container, welcomeF).commit();

            }
        });


        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("Search Pharmacies");


        recyclerView = v.findViewById(R.id.recyclerviewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Pharmacy> options =
                new FirebaseRecyclerOptions.Builder<Pharmacy>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Pharmacies"), Pharmacy.class)
                        .build();

        pharmacyAdapter = new PharmacyAdapter(options);
        recyclerView.setAdapter(pharmacyAdapter);



        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        pharmacyAdapter.startListening();;
    }

    @Override
    public void onStop() {
        super.onStop();
        pharmacyAdapter.stopListening();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_pharma, menu);
        MenuItem menuItem = menu.findItem(R.id.search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconified(true);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                textSearch(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                textSearch(s);
                return false;
            }
        });


        super.onCreateOptionsMenu(menu, inflater);
    }



     public void textSearch(String str)
     {
         FirebaseRecyclerOptions<Pharmacy> options =
                 new FirebaseRecyclerOptions.Builder<Pharmacy>()
                         .setQuery(FirebaseDatabase.getInstance().getReference().child("Pharmacies").orderByChild("Name").startAt(str).endAt(str+ "~"), Pharmacy.class)
                         .build();

         pharmacyAdapter = new PharmacyAdapter(options);
         pharmacyAdapter.startListening();
         recyclerView.setAdapter(pharmacyAdapter);
     }
}