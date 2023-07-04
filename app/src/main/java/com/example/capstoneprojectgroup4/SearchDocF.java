package com.example.capstoneprojectgroup4;

import android.os.Bundle;

import android.app.SearchManager;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDocF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDocF extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Toolbar toolbar;
    private Button searchButton;
    private RadioGroup radioGroup;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;
    private SearchView searchView;

    public SearchDocF() {
        // Required empty public constructor
    }

    public static SearchDocF newInstance(String param1, String param2) {
        SearchDocF fragment = new SearchDocF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_doc, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        searchButton = view.findViewById(R.id.btnSearch);
        radioGroup = view.findViewById(R.id.radioGroupSearchBy);
        recyclerView = view.findViewById(R.id.rvDoctors);

        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Search Doctors");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Doctors> options =
                new FirebaseRecyclerOptions.Builder<Doctors>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors"), Doctors.class)
                        .build();

        doctorAdapter = new DoctorAdapter(options);
        recyclerView.setAdapter(doctorAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_doctor_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                performSearch(query);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void performSearch(String searchText) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        Query query;

        if (selectedRadioButtonId == R.id.radioName) {
            query = FirebaseDatabase.getInstance().getReference().child("Doctors").orderByChild("Name").startAt(searchText).endAt(searchText + "\uf8ff");
        } else if (selectedRadioButtonId == R.id.radioSpecialization) {
            query = FirebaseDatabase.getInstance().getReference().child("Doctors").orderByChild("Specialization").startAt(searchText).endAt(searchText + "\uf8ff");
        } else if (selectedRadioButtonId == R.id.radioLocation) {
            query = FirebaseDatabase.getInstance().getReference().child("Doctors").orderByChild("Location").startAt(searchText).endAt(searchText + "\uf8ff");
        } else {
            query = FirebaseDatabase.getInstance().getReference().child("Doctors");
        }

        FirebaseRecyclerOptions<Doctors> options = new FirebaseRecyclerOptions.Builder<Doctors>()
                .setQuery(query, Doctors.class)
                .build();


        doctorAdapter = new DoctorAdapter(options);
        doctorAdapter.startListening();
        recyclerView.setAdapter(doctorAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
            doctorAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
            doctorAdapter.stopListening();

    }
}