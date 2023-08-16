package com.example.capstoneprojectgroup4;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.os.Bundle;

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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchDocF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchDocF extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    int searchType = -1; // Default to no specific search
    Map <String, Object> doctors = new HashMap<>();
    Map<String, Object> detailsOfEachDoctor = new HashMap<>();
    String nameResult = "";
    String specializationResult = "";
    ArrayList<String> locations;
    private String mParam1;
    private String mParam2;

    private Toolbar toolbar;
    private Button searchButton;
    private RadioGroup radioGroup;
    private EditText searchEditText;
    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;

    private DocSearchResultAdapter docSearchResultAdapter;
    private SearchView searchView;

    Button button;
    Button datePickerButton;
    Query query;

    Query query2;

    FirebaseRecyclerOptions<Doctors> options;
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

         datePickerButton = view.findViewById(R.id.btnDatePicker);
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

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
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performSearch(query);

                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        performSearch(query);

                    }
                });                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void performSearch(String searchText) {
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();

        if (selectedRadioButtonId == R.id.radioName) {
            searchType = 0;
        } else if (selectedRadioButtonId == R.id.radioSpecialization) {
            searchType = 1;
        } else if (selectedRadioButtonId == R.id.radioLocation) {
            searchType = 2;
        }

        query = FirebaseDatabase.getInstance().getReference().child("Doctors");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> doctors = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String name = (String) doctorSnapshot.child("Name").getValue();
                    String specialization = (String) doctorSnapshot.child("Specialization").getValue();
                    ArrayList<String> locations = (ArrayList<String>) doctorSnapshot.child("Locations").getValue();

                    if (searchType == 0 && name != null && name.toLowerCase().contains(searchText.toLowerCase())) {
                        Doctors doctor = new Doctors(name, specialization, locations);
                        doctors.add(doctor);
                    } else if (searchType == 1 && specialization != null && specialization.toLowerCase().contains(searchText.toLowerCase())) {
                        Doctors doctor = new Doctors(name, specialization, locations);
                        doctors.add(doctor);
                    } else if (searchType == 2 && locations != null) {
                        // Check if the searched location matches any location for this doctor (case-insensitive).
                        for (String location : locations) {
                            if (location.toLowerCase().contains(searchText.toLowerCase())) {
                                // Convert the locations list to an ArrayList with a single element.
                                ArrayList<String> locationList = new ArrayList<>();
                                locationList.add(location);

                                // Create the doctor instance only once with the matched location.
                                Doctors doctor = new Doctors(name, specialization, locationList);
                                doctors.add(doctor);
                                break; // No need to check other locations for this doctor.
                            }
                        }
                    }


                }

                DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(doctors);
                recyclerView.setAdapter(docSearchResultAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void showDatePickerDialog() {
        Log.d(TAG, "showDatePickerDialog() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                datePickerButton.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        Log.d(TAG, "1");

                                                        performSearchDate(formattedDate); // Perform search using the selected date

                                                    }
                                                });
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void performSearchDate(String formattedDate) {
        DatabaseReference availabilityRef = FirebaseDatabase.getInstance().getReference("Availability");
        Log.d(TAG, "2");

        Query queryDate = availabilityRef.orderByChild("Name");
        queryDate.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "3");

                ArrayList<Availability> sessionDetailsDateList = new ArrayList<>();
                ArrayList<Doctors> doctorsDateList = new ArrayList<>(); // New list to accumulate doctors with matching dates

                for (DataSnapshot doctorSnapshotDate : snapshot.getChildren()) {
                    String doctorNameDate = doctorSnapshotDate.child("Name").getValue(String.class);
                    Log.d(TAG, "4");

                    for (DataSnapshot sessionSnapshotDate : doctorSnapshotDate.getChildren()) {
                        if (!sessionSnapshotDate.getKey().equals("Name")) {
                            String locationDate = sessionSnapshotDate.child("LName").getValue(String.class);
                            Log.d(TAG, "5");

                            for (DataSnapshot daySnapshotDate : sessionSnapshotDate.getChildren()) {
                                if (!daySnapshotDate.getKey().equals("LName")) {
                                    String dayDate = daySnapshotDate.getKey();
                                    String dateDate = daySnapshotDate.child("Date").getValue(String.class);
                                    Log.d(TAG, "6");
                                    Log.d(TAG, "formattedDate: " + formattedDate);
                                    Log.d(TAG, "dateDate: " + dateDate);

                                    if (formattedDate.equals(dateDate)) {
                                        Log.d(TAG, "7");

                                        Query query2Date = FirebaseDatabase.getInstance().getReference().child("Doctors");
                                        query2Date.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                Log.d(TAG, "8");

                                                for (DataSnapshot doctorSnapshotDate : snapshot.getChildren()) {
                                                    String name2Date = doctorSnapshotDate.child("Name").getValue(String.class);
                                                    String specializationDate = doctorSnapshotDate.child("Specialization").getValue(String.class);
                                                    ArrayList<String> locationsDateList = new ArrayList<>();
                                                    for (DataSnapshot locationSnapshotDate : doctorSnapshotDate.child("Locations").getChildren()) {
                                                        String location = locationSnapshotDate.getValue(String.class);

                                                        if (location != null && location.equals(locationDate)) {
                                                            locationsDateList.add(location);
                                                        }                                                    }

                                                    if (name2Date != null && name2Date.toLowerCase().contains(doctorNameDate.toLowerCase())) {
                                                        Doctors doctorDate = new Doctors(name2Date, specializationDate, locationsDateList);
                                                        doctorsDateList.add(doctorDate);
                                                        Log.d(TAG, "9");
                                                    }
                                                }
                                                Log.d(TAG, "10");

                                                DocSearchResultAdapter docSearchResultAdapterDate = new DocSearchResultAdapter(doctorsDateList);
                                                recyclerView.setAdapter(docSearchResultAdapterDate);
                                                // docSearchResultAdapterDate.notifyDataSetChanged();
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.w(TAG, "Failed to read value.", error.toException());
                                            }
                                        });
                                    }
                                }
                            }
                        }
                    }
                }
                Log.d(TAG, String.format("%s", sessionDetailsDateList + ""));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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