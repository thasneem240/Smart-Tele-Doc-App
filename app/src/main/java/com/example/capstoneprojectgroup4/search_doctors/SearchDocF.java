package com.example.capstoneprojectgroup4.search_doctors;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
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
    public int searchType = -1; // Default to no specific search
    Map <String, Object> doctors = new HashMap<>();
    Map<String, Object> detailsOfEachDoctor = new HashMap<>();
    String nameResult = "";
    String specializationResult = "";
    ArrayList<String> locations;
    private String mParam1;
    private String mParam2;
    public TextView etName ;
    public TextView etSpecialization ;
    public TextView etLocation;
    public TextView etDate ;
    private Toolbar toolbar;
    String specializationV;
    private Button searchButton;
    private RadioGroup radioGroup;
    private EditText searchEditText;
    public RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;

    private DocSearchResultAdapter docSearchResultAdapter;
    private SearchView searchView;

    Button button;
    int datesearchid =0;
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

        //toolbar = view.findViewById(R.id.toolbar);
        searchButton = view.findViewById(R.id.searchDoctorsNSLDButton);
        recyclerView = view.findViewById(R.id.searchrv);
        ImageView backButton = view.findViewById(R.id.backButtonSearchDoc);

         etName = view.findViewById(R.id.PatsearchName);
         etSpecialization = view.findViewById(R.id.searchSpec);
         etLocation = view.findViewById(R.id.searchLoc);
         etDate = view.findViewById(R.id.searchDate);

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateSearch();
            }
        });
//Comment

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                FragmentManager fm = getActivity().getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        //((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
       // ((AppCompatActivity) requireActivity()).getSupportActionBar().setTitle("Search Doctors");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        FirebaseRecyclerOptions<Doctors> options =
                new FirebaseRecyclerOptions.Builder<Doctors>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors"), Doctors.class)
                        .build();

        doctorAdapter = new DoctorAdapter(options);
        recyclerView.setAdapter(doctorAdapter);

        return view;
    }

    public void openDateSearch()
    {
        String specializationV = etSpecialization.getText().toString().trim();
        String nameV = etName.getText().toString().trim();
        String locV = etLocation.getText().toString().trim();


        Log.d(TAG, "Spec: " + specializationV);


                if (!specializationV.isEmpty() && nameV.isEmpty()  && locV.isEmpty()  ) {
                    showDatePickerDialogS(specializationV);
                }
                else if (!nameV.isEmpty() && specializationV.isEmpty() && locV.isEmpty() )
                {
                     showDatePickerDialogN(nameV);
                }
                else if (!locV.isEmpty() && specializationV.isEmpty() && nameV.isEmpty() )
                {
                    showDatePickerDialogL(locV);
                }
                else if (!locV.isEmpty() && !specializationV.isEmpty() && nameV.isEmpty() )
                {
                    showDatePickerDialogLS(specializationV, locV)    ;
                }
                else if (!locV.isEmpty() && !nameV.isEmpty() && specializationV.isEmpty() )
                {
                    showDatePickerDialogNL(nameV, locV);    ;
                }
                else if (!specializationV.isEmpty() && !nameV.isEmpty() && locV.isEmpty() )
                {
                    showDatePickerDialogNS(nameV, specializationV);    ;
                }
                else if (!specializationV.isEmpty() && !nameV.isEmpty() && !locV.isEmpty() )
                {
                    showDatePickerDialogEverything(nameV, specializationV, locV);    ;
                }
                else {
                    showDatePickerDialogD();
                }

    }


    private String removeWhitespaceAndToLower(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("\\s", "").toLowerCase();
    }

    public void performSearch() {
        String selectedDate = etDate.getText().toString();
        String nameEd = etName.getText().toString().trim();
        String specializationEd = etSpecialization.getText().toString().trim();
        String locationEd = etLocation.getText().toString().trim();

        if (!nameEd.isEmpty() && areOtherEditTextsEmptyName()) {
            Log.d(TAG, "Search by Name: " + nameEd);
            searchType = 0;
        } else if (!specializationEd.isEmpty() && areOtherEditTextsEmptySpec()) {
            Log.d(TAG, "Search by Specialization: " + specializationEd);
            searchType = 1;
        } else if (!locationEd.isEmpty() && areOtherEditTextsEmptyLoc()) {
            Log.d(TAG, "Search by Location: " + locationEd);
            searchType = 2;
        } else if (selectedDate != null && areOtherEditTextsEmptyDate()) {
            Log.d(TAG, "Search by Date");
            searchType = 3;
        }
        else if (!locationEd.isEmpty() && !specializationEd.isEmpty() && nameEd.isEmpty() && selectedDate.isEmpty())
        {
            searchType =4;
        }
        else if (!nameEd.isEmpty() && !specializationEd.isEmpty() && locationEd.isEmpty() && selectedDate.isEmpty())
        {
            searchType =5;
        }
        else if (!nameEd.isEmpty() && !locationEd.isEmpty() && specializationEd.isEmpty() && selectedDate.isEmpty())
        {
            searchType =6;
        }
        else if (!nameEd.isEmpty() && !locationEd.isEmpty() && !specializationEd.isEmpty() && selectedDate.isEmpty())
        {
            searchType =7;
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

                    if (searchType == 0 && name != null &&
                            removeWhitespaceAndToLower(name).contains(removeWhitespaceAndToLower(nameEd))) {                        Doctors doctor = new Doctors(name, specialization, locations);
                        doctors.add(doctor);

                    } else if (searchType == 1 && specialization != null && removeWhitespaceAndToLower(specialization).contains(removeWhitespaceAndToLower(specializationEd))){
                        Doctors doctor = new Doctors(name, specialization, locations);
                        doctors.add(doctor);
                    }  else if (searchType == 2 && locations != null) {
                        // Check if the searched location matches any location for this doctor (case-insensitive).
                        for (String location : locations) {
                            if (removeWhitespaceAndToLower(location).contains(removeWhitespaceAndToLower(locationEd))) {
                                // Convert the locations list to an ArrayList with a single element.
                                ArrayList<String> locationList = new ArrayList<>();
                                locationList.add(location);

                                // Create the doctor instance only once with the matched location.
                                Doctors doctor = new Doctors(name, specialization, locationList);
                                doctors.add(doctor);
                                break; // No need to check other locations for this doctor.
                            }
                        }
                    } else if (searchType == 4) {
                        performSearchSpecializationAndLocation(specializationEd,locationEd );
                    }
                    else if (searchType == 5) {
                        performSearchSpecializationAndName(specializationEd,nameEd );
                    }
                    else if (searchType == 6) {
                        performSearchLocationAndName(locationEd,nameEd );
                    }
                    else if (searchType == 7) {
                        performSearchSpecializationLocationAndName(specializationEd, locationEd,nameEd );
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
    private boolean areOtherEditTextsEmptyName() {
        return etSpecialization.getText().toString().trim().isEmpty() &&
                etLocation.getText().toString().trim().isEmpty() &&
                etDate.getText().toString().trim().isEmpty();
    }

    private boolean areOtherEditTextsEmptySpec() {
        return etName.getText().toString().trim().isEmpty() &&
                etLocation.getText().toString().trim().isEmpty() &&
                etDate.getText().toString().trim().isEmpty();
    }

    private boolean areOtherEditTextsEmptyLoc() {
        return etSpecialization.getText().toString().trim().isEmpty() &&
                etName.getText().toString().trim().isEmpty() &&
                etDate.getText().toString().trim().isEmpty();
    }

    private boolean areOtherEditTextsEmptyDate() {
        return etSpecialization.getText().toString().trim().isEmpty() &&
                etLocation.getText().toString().trim().isEmpty() &&
                etName.getText().toString().trim().isEmpty();
    }

    private void performSearchSpecializationLocationAndName(String specializationSLN, String locationSLN, String nameSLN) {
        Log.d(TAG, "performSearchSpecializationLocationAndName started");
        Log.d(TAG, "Specialization: " + specializationSLN);
        Log.d(TAG, "Location: " + locationSLN);
        Log.d(TAG, "Name: " + nameSLN);

        DatabaseReference availabilityRefSLN = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefSLN.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);

                    for (DataSnapshot sessionSnapshot : doctorSnapshot.getChildren()) {
                        if (!sessionSnapshot.getKey().contains("Name")) {
                            String sessionLocation = sessionSnapshot.child("LName").getValue(String.class);
                            Log.d(TAG, "LName " + sessionLocation);

                            if (sessionLocation != null && removeWhitespaceAndToLower(sessionLocation).contains(removeWhitespaceAndToLower(locationSLN))) {
                                Log.d(TAG, "Location matched for doctor: " + doctorName);
                                String doctorId = doctorSnapshot.getKey();
                                Query doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);

                                doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name2 = snapshot.child("Name").getValue(String.class);
                                        String spec2 = snapshot.child("Specialization").getValue(String.class);
                                        ArrayList<String> locationsList = new ArrayList<>();
                                        for (DataSnapshot locationSnapshot : snapshot.child("Locations").getChildren()) {
                                            String doctorLocation = locationSnapshot.getValue(String.class);

                                            if (doctorLocation != null && removeWhitespaceAndToLower(doctorLocation).contains(removeWhitespaceAndToLower(sessionLocation))) {
                                                locationsList.add(doctorLocation);
                                            }
                                        }

                                        Log.d(TAG, "Comparing specializationSLN: " + specializationSLN + " with spec2: " + spec2);
                                        if (name2 != null && removeWhitespaceAndToLower(name2).contains(removeWhitespaceAndToLower(nameSLN)) &&
                                                spec2 != null && removeWhitespaceAndToLower(spec2).contains(removeWhitespaceAndToLower(specializationSLN))) {
                                            Log.d(TAG, "Doctor matched: " + name2);
                                            Doctors doctor = new Doctors(name2, spec2, locationsList);
                                            Log.d(TAG, "D: " + doctor);
                                            matchedDoctorsList.add(doctor);
                                        }

                                        Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                                        DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                                        recyclerView.setAdapter(docSearchResultAdapter);
                                        Log.d(TAG, "Setting adapter for RecyclerView");
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }






    private void performSearchLocationAndName(String locationLN, String nameLN) {
        Log.d(TAG, "performSearchLocationAndName started");
        Log.d(TAG, "Location: " + locationLN);
        Log.d(TAG, "Name: " + nameLN);

        DatabaseReference availabilityRefLN = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefLN.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);

                    for (DataSnapshot sessionSnapshot : doctorSnapshot.getChildren()) {
                        if (!sessionSnapshot.getKey().contains("Name")) {
                            String sessionLocation = sessionSnapshot.child("LName").getValue(String.class);
                            Log.d(TAG, "LName " + sessionLocation);

                            if (sessionLocation != null && removeWhitespaceAndToLower(sessionLocation).contains(removeWhitespaceAndToLower(locationLN))) {
                                Log.d(TAG, "Location matched for doctor: " + doctorName);
                                String doctorId = doctorSnapshot.getKey();
                                Query doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);

                                doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name2 = snapshot.child("Name").getValue(String.class);
                                        String spec2 = snapshot.child("Specialization").getValue(String.class);
                                        ArrayList<String> locationsList = new ArrayList<>();
                                        for (DataSnapshot locationSnapshot : snapshot.child("Locations").getChildren()) {
                                            String doctorLocation = locationSnapshot.getValue(String.class);

                                            if (doctorLocation != null && removeWhitespaceAndToLower(doctorLocation).contains(removeWhitespaceAndToLower(sessionLocation))) {
                                                locationsList.add(doctorLocation);
                                            }
                                        }

                                        Log.d(TAG, "Comparing nameLN: " + nameLN + " with name2: " + name2);
                                        if (name2 != null && removeWhitespaceAndToLower(name2).contains(removeWhitespaceAndToLower(nameLN))) {
                                            Log.d(TAG, "Doctor matched: " + name2);
                                            Doctors doctor = new Doctors(name2, spec2, locationsList);
                                            Log.d(TAG, "D: " + doctor);
                                            matchedDoctorsList.add(doctor);
                                        }

                                        Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                                        DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                                        recyclerView.setAdapter(docSearchResultAdapter);
                                        Log.d(TAG, "Setting adapter for RecyclerView");
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void performSearchSpecializationLocationAndDate(String specializationSLD, String locationSLD, String selectedDateSLD) {
        Log.d(TAG, "performSearchSpecializationLocationAndDate started");
        Log.d(TAG, "Specialization: " + specializationSLD);
        Log.d(TAG, "Location: " + locationSLD);
        Log.d(TAG, "Date: " + selectedDateSLD);

        DatabaseReference availabilityRefSLD = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefSLD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);

                    for (DataSnapshot sessionSnapshot : doctorSnapshot.getChildren()) {
                        if (!sessionSnapshot.getKey().contains("Name")) {
                            String sessionLocation = sessionSnapshot.child("LName").getValue(String.class);
                            Log.d(TAG, "LName " + sessionLocation);

                            if (sessionLocation != null && removeWhitespaceAndToLower(sessionLocation).contains(removeWhitespaceAndToLower(locationSLD))) {
                                for (DataSnapshot daySnapshot : sessionSnapshot.getChildren()) {
                                    if (!daySnapshot.getKey().contains("LName")) {
                                        String sessionDate = daySnapshot.child("Date").getValue(String.class);

                                        Log.d(TAG, "Comparing selectedDateSLD: " + selectedDateSLD + " with sessionDate: " + sessionDate);

                                        if (selectedDateSLD.contains(sessionDate)) {
                                            Log.d(TAG, "Date matched for doctor: " + doctorName);
                                            String doctorId = doctorSnapshot.getKey();
                                            Query doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);

                                            doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String name = snapshot.child("Name").getValue(String.class);
                                                    String spec = snapshot.child("Specialization").getValue(String.class);
                                                    ArrayList<String> locationsList = new ArrayList<>();
                                                    for (DataSnapshot locationSnapshot : snapshot.child("Locations").getChildren()) {
                                                        String doctorLocation = locationSnapshot.getValue(String.class);

                                                        if (doctorLocation != null && removeWhitespaceAndToLower(doctorLocation).contains(removeWhitespaceAndToLower(sessionLocation))) {
                                                            locationsList.add(doctorLocation);
                                                        }
                                                    }

                                                    Log.d(TAG, "Comparing specializationSLD: " + specializationSLD + " with spec: " + spec);
                                                    if (name != null && removeWhitespaceAndToLower(name).contains(removeWhitespaceAndToLower(doctorName)) &&
                                                            spec != null && removeWhitespaceAndToLower(spec).contains(removeWhitespaceAndToLower(specializationSLD))) {
                                                        Log.d(TAG, "Doctor matched: " + name);
                                                        Doctors doctor = new Doctors(name, spec, locationsList);
                                                        Log.d(TAG, "D: " + doctor);
                                                        matchedDoctorsList.add(doctor);
                                                    }

                                                    Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                                                    DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                                                    recyclerView.setAdapter(docSearchResultAdapter);
                                                    Log.d(TAG, "Setting adapter for RecyclerView");
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void performSearchNameLocationAndDate(String nameNLD, String locationNLD, String selectedDateNLD) {
        Log.d(TAG, "performSearchNameLocationAndDate started");
        Log.d(TAG, "Name: " + nameNLD);
        Log.d(TAG, "Location: " + locationNLD);
        Log.d(TAG, "Date: " + selectedDateNLD);

        DatabaseReference availabilityRefNLD = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefNLD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);

                    if (doctorName != null && removeWhitespaceAndToLower(doctorName).contains(removeWhitespaceAndToLower(nameNLD))) {
                        for (DataSnapshot sessionSnapshot : doctorSnapshot.getChildren()) {
                            if (!sessionSnapshot.getKey().contains("Name")) {
                                String sessionLocation = sessionSnapshot.child("LName").getValue(String.class);
                                Log.d(TAG, "LName " + sessionLocation);

                                if (sessionLocation != null && sessionLocation.toLowerCase().contains(locationNLD.toLowerCase())) {
                                    for (DataSnapshot daySnapshot : sessionSnapshot.getChildren()) {
                                        if (!daySnapshot.getKey().contains("LName")) {
                                            String sessionDate = daySnapshot.child("Date").getValue(String.class);

                                            Log.d(TAG, "Comparing selectedDateNLD: " + selectedDateNLD + " with sessionDate: " + sessionDate);

                                            if (selectedDateNLD.equals(sessionDate)) {
                                                Log.d(TAG, "Date matched for doctor: " + doctorName);
                                                String doctorId = doctorSnapshot.getKey();
                                                Query doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);

                                                doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String name = snapshot.child("Name").getValue(String.class);
                                                        String spec = snapshot.child("Specialization").getValue(String.class);
                                                        ArrayList<String> locationsList = new ArrayList<>();
                                                        for (DataSnapshot locationSnapshot : snapshot.child("Locations").getChildren()) {
                                                            String doctorLocation = locationSnapshot.getValue(String.class);

                                                            if (doctorLocation != null && removeWhitespaceAndToLower(doctorLocation).contains(removeWhitespaceAndToLower(sessionLocation)) ){
                                                                locationsList.add(doctorLocation);
                                                            }
                                                        }

                                                        Log.d(TAG, "Comparing nameNLD: " + nameNLD + " with name: " + name);
                                                        if (spec != null) {
                                                            Log.d(TAG, "Doctor matched: " + name);
                                                            Doctors doctor = new Doctors(name, spec, locationsList);
                                                            Log.d(TAG, "D: " + doctor);
                                                            matchedDoctorsList.add(doctor);
                                                        }

                                                        Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                                                        DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                                                        recyclerView.setAdapter(docSearchResultAdapter);
                                                        Log.d(TAG, "Setting adapter for RecyclerView");
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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void performSearchSpecializationAndName(String specializationSN, String nameSN) {
        Log.d(TAG, "performSearchSpecializationAndName started");
        Log.d(TAG, "Specialization: " + specializationSN);
        Log.d(TAG, "Name: " + nameSN);

        DatabaseReference doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors");

        doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);
                    String doctorSpec = doctorSnapshot.child("Specialization").getValue(String.class);

                    if (doctorName != null && removeWhitespaceAndToLower(doctorName).contains(removeWhitespaceAndToLower(nameSN)) &&
                            doctorSpec != null && removeWhitespaceAndToLower(doctorSpec).contains(removeWhitespaceAndToLower(specializationSN))) {
                        Log.d(TAG, "Doctor matched: " + doctorName);
                        ArrayList<String> locationsList = new ArrayList<>();
                        for (DataSnapshot locationSnapshot : doctorSnapshot.child("Locations").getChildren()) {
                            String doctorLocation = locationSnapshot.getValue(String.class);
                            locationsList.add(doctorLocation);
                        }

                        Doctors doctor = new Doctors(doctorName, doctorSpec, locationsList);
                        Log.d(TAG, "D: " + doctor);
                        matchedDoctorsList.add(doctor);
                    }
                }

                Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                recyclerView.setAdapter(docSearchResultAdapter);
                Log.d(TAG, "Setting adapter for RecyclerView");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void performSearchSpecializationAndLocation(String specializationSL, String locationSL) {
        Log.d(TAG, "performSearchSpecializationAndLocation started");
        Log.d(TAG, "Specialization: " + specializationSL);
        Log.d(TAG, "Location: " + locationSL);

        DatabaseReference availabilityRefSL = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefSL.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);

                    for (DataSnapshot sessionSnapshot : doctorSnapshot.getChildren()) {
                        if (!sessionSnapshot.getKey().contains("Name")) {
                            String sessionLocation = sessionSnapshot.child("LName").getValue(String.class);
                            Log.d(TAG, "LName " + sessionLocation);

                            if (sessionLocation != null && removeWhitespaceAndToLower(sessionLocation).contains(removeWhitespaceAndToLower(locationSL))) {
                                Log.d(TAG, "Location matched for doctor: " + doctorName);
                                String doctorId = doctorSnapshot.getKey();
                                Query doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);

                                doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name2 = snapshot.child("Name").getValue(String.class);
                                        String spec2 = snapshot.child("Specialization").getValue(String.class);
                                        ArrayList<String> locationsList = new ArrayList<>();
                                        for (DataSnapshot locationSnapshot : snapshot.child("Locations").getChildren()) {
                                            String doctorLocation = locationSnapshot.getValue(String.class);

                                            if (doctorLocation != null && removeWhitespaceAndToLower(doctorLocation).contains(removeWhitespaceAndToLower(sessionLocation))) {
                                                locationsList.add(doctorLocation);
                                            }
                                        }

                                        Log.d(TAG, "Comparing specializationSL: " + specializationSL + " with spec2: " + spec2);
                                        if (name2 != null && removeWhitespaceAndToLower(name2).contains(removeWhitespaceAndToLower(doctorName)) &&
                                                spec2 != null && removeWhitespaceAndToLower(spec2).contains(removeWhitespaceAndToLower(specializationSL))) {
                                            Log.d(TAG, "Doctor matched: " + name2);
                                            Doctors doctor = new Doctors(name2, spec2, locationsList);
                                            Log.d(TAG, "D: " + doctor);
                                            matchedDoctorsList.add(doctor);
                                        }

                                        Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                                        DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                                        recyclerView.setAdapter(docSearchResultAdapter);
                                        Log.d(TAG, "Setting adapter for RecyclerView");
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

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void performSearchSpecializationAndDate(String specializationS, String selectedDateS) {
        Log.d(TAG, "performSearchSpecializationAndDate started");
        Log.d(TAG, "D Spec: " + specializationS);

        DatabaseReference availabilityRefS = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefS.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> doctorsSpecializationDateList = new ArrayList<>();

                for (DataSnapshot doctorSnapshotS : snapshot.getChildren()) {
                    String doctorNameDateS = doctorSnapshotS.child("Name").getValue(String.class);

                    for (DataSnapshot sessionSnapshotDateS : doctorSnapshotS.getChildren()) {
                        if (!sessionSnapshotDateS.getKey().contains("Name")) {
                            String locationDateS = sessionSnapshotDateS.child("LName").getValue(String.class);

                            for (DataSnapshot daySnapshotDateS : sessionSnapshotDateS.getChildren()) {
                                if (!daySnapshotDateS.getKey().contains("LName")) {
                                    String dateDateS = daySnapshotDateS.child("Date").getValue(String.class);

                                    Log.d(TAG, "Comparing selectedDateS: " + selectedDateS + " with dateDateS: " + dateDateS);

                                    if (selectedDateS.equals(dateDateS)) {
                                        Log.d(TAG, "Dates matched for doctor: " + doctorNameDateS);
                                        Query query2DateS = FirebaseDatabase.getInstance().getReference().child("Doctors");
                                        query2DateS.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                for (DataSnapshot doctorSnapshotDateS : snapshot.getChildren()) {
                                                    String name2DateS = doctorSnapshotDateS.child("Name").getValue(String.class);
                                                    String specDateS = doctorSnapshotDateS.child("Specialization").getValue(String.class);
                                                    ArrayList<String> locationsDateListS = new ArrayList<>();
                                                    for (DataSnapshot locationSnapshotDateS : doctorSnapshotDateS.child("Locations").getChildren()) {
                                                        String locationS = locationSnapshotDateS.getValue(String.class);

                                                        if (locationS != null && removeWhitespaceAndToLower(locationS).contains(removeWhitespaceAndToLower(locationDateS))) {
                                                            locationsDateListS.add(locationS);
                                                        }                                                    }

                                                    if (name2DateS != null && removeWhitespaceAndToLower(name2DateS).contains(removeWhitespaceAndToLower(doctorNameDateS)) &&
                                                            specDateS != null && removeWhitespaceAndToLower(specDateS).contains(removeWhitespaceAndToLower(specializationS))) {
                                                        Log.d(TAG, "Doctor matched: " + name2DateS);
                                                        Doctors doctorDateS = new Doctors(name2DateS, specDateS, locationsDateListS);
                                                        Log.d(TAG, "D: " + doctorDateS);
                                                        doctorsSpecializationDateList.add(doctorDateS);
                                                    }
                                                }

                                                Log.d(TAG, "Doctors matched: " + doctorsSpecializationDateList);

                                                    DocSearchResultAdapter docSearchResultAdapterSpecDateS = new DocSearchResultAdapter(doctorsSpecializationDateList);
                                                    recyclerView.setAdapter(docSearchResultAdapterSpecDateS);
                                                Log.d(TAG, "Setting adapter for RecyclerView");

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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void performSearchNameSpecializationLocationAndDate(String nameNSLD, String specializationNSLD, String locationNSLD, String selectedDateNSLD) {
        Log.d(TAG, "performSearchNameSpecializationLocationAndDate started");
        Log.d(TAG, "Name: " + nameNSLD);
        Log.d(TAG, "Specialization: " + specializationNSLD);
        Log.d(TAG, "Location: " + locationNSLD);
        Log.d(TAG, "Date: " + selectedDateNSLD);

        DatabaseReference availabilityRefNSLD = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefNSLD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);
                    Log.d(TAG, "dN " + doctorName);

                    if (doctorName != null && removeWhitespaceAndToLower(doctorName).contains(removeWhitespaceAndToLower(nameNSLD))) {

                        for (DataSnapshot sessionSnapshot : doctorSnapshot.getChildren()) {
                            if (!sessionSnapshot.getKey().contains("Name")) {
                                String sessionLocation = sessionSnapshot.child("LName").getValue(String.class);
                                Log.d(TAG, "LName " + sessionLocation);

                                if (sessionLocation != null && removeWhitespaceAndToLower(sessionLocation).contains(removeWhitespaceAndToLower(locationNSLD))) {

                                    for (DataSnapshot daySnapshot : sessionSnapshot.getChildren()) {
                                        if (!daySnapshot.getKey().contains("LName")) {
                                            String sessionDate = daySnapshot.child("Date").getValue(String.class);

                                            Log.d(TAG, "Comparing selectedDateNSLD: " + selectedDateNSLD + " with sessionDate: " + sessionDate);

                                            if (selectedDateNSLD.equals(sessionDate)) {
                                                Log.d(TAG, "Date matched for doctor: " + doctorName);
                                                String doctorId = doctorSnapshot.getKey();
                                                Query doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);

                                                doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        String name = snapshot.child("Name").getValue(String.class);
                                                        String spec = snapshot.child("Specialization").getValue(String.class);
                                                        ArrayList<String> locationsList = new ArrayList<>();
                                                        for (DataSnapshot locationSnapshot : snapshot.child("Locations").getChildren()) {
                                                            String doctorLocation = locationSnapshot.getValue(String.class);

                                                            if (doctorLocation != null && removeWhitespaceAndToLower(doctorLocation).contains(removeWhitespaceAndToLower(sessionLocation))) {
                                                                locationsList.add(doctorLocation);
                                                            }
                                                        }

                                                        Log.d(TAG, "Comparing nameNSLD: " + nameNSLD + " with name: " + name);
                                                        if (spec != null && removeWhitespaceAndToLower(spec).contains(removeWhitespaceAndToLower(specializationNSLD))) {
                                                            Log.d(TAG, "Doctor matched: " + name);
                                                            Doctors doctor = new Doctors(name, spec, locationsList);
                                                            Log.d(TAG, "D: " + doctor);
                                                            matchedDoctorsList.add(doctor);
                                                        }

                                                        Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                                                        DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                                                        recyclerView.setAdapter(docSearchResultAdapter);
                                                        Log.d(TAG, "Setting adapter for RecyclerView");
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
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    private void performSearchNameSpecializationAndDate(String nameNSD, String specializationNSD, String selectedDateNSD) {
        Log.d(TAG, "performSearchNameSpecializationAndDate started");
        Log.d(TAG, "Name: " + nameNSD);
        Log.d(TAG, "Specialization: " + specializationNSD);
        Log.d(TAG, "Date: " + selectedDateNSD);

        DatabaseReference availabilityRefNSD = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefNSD.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> matchedDoctorsList = new ArrayList<>();

                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorName = doctorSnapshot.child("Name").getValue(String.class);
                    Log.d(TAG, "dN " + doctorName);

                    //String doctorSpecialization = doctorSnapshot.child("Specialization").getValue(String.class);
                    //Log.d(TAG, "dS " + doctorSpecialization);

                    if (doctorName != null && removeWhitespaceAndToLower(doctorName).contains(removeWhitespaceAndToLower(nameNSD))) {

                        for (DataSnapshot sessionSnapshot : doctorSnapshot.getChildren()) {
                            if (!sessionSnapshot.getKey().contains("Name")) {
                                String sessionLocation = sessionSnapshot.child("LName").getValue(String.class);
                                Log.d(TAG, "LName " + sessionLocation);

                                for (DataSnapshot daySnapshot : sessionSnapshot.getChildren()) {
                                    if (!daySnapshot.getKey().contains("LName")) {
                                        String sessionDate = daySnapshot.child("Date").getValue(String.class);

                                        Log.d(TAG, "Comparing selectedDateNSD: " + selectedDateNSD + " with sessionDate: " + sessionDate);

                                        if (selectedDateNSD.equals(sessionDate)) {
                                            Log.d(TAG, "Date matched for doctor: " + doctorName);
                                            String doctorId = doctorSnapshot.getKey();
                                            Query doctorsRef = FirebaseDatabase.getInstance().getReference("Doctors").child(doctorId);

                                            doctorsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    String name = snapshot.child("Name").getValue(String.class);
                                                    String spec = snapshot.child("Specialization").getValue(String.class);
                                                    ArrayList<String> locationsList = new ArrayList<>();
                                                    for (DataSnapshot locationSnapshot : snapshot.child("Locations").getChildren()) {
                                                        String doctorLocation = locationSnapshot.getValue(String.class);

                                                        if (doctorLocation != null && removeWhitespaceAndToLower(doctorLocation).contains(removeWhitespaceAndToLower(sessionLocation))) {
                                                            locationsList.add(doctorLocation);
                                                        }
                                                    }

                                                    Log.d(TAG, "Comparing nameNSD: " + nameNSD + " with name: " + name);
                                                    if (spec != null  && removeWhitespaceAndToLower(spec).contains(removeWhitespaceAndToLower(specializationNSD)) )
                                                    {
                                                        Log.d(TAG, "Doctor matched: " + name);
                                                        Doctors doctor = new Doctors(name, spec, locationsList);
                                                        Log.d(TAG, "D: " + doctor);
                                                        matchedDoctorsList.add(doctor);
                                                    }

                                                    Log.d(TAG, "Doctors matched: " + matchedDoctorsList);

                                                    DocSearchResultAdapter docSearchResultAdapter = new DocSearchResultAdapter(matchedDoctorsList);
                                                    recyclerView.setAdapter(docSearchResultAdapter);
                                                    Log.d(TAG, "Setting adapter for RecyclerView");
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }



    private void performSearchNameAndDate(String doctorNameS, String selectedDateS) {
        Log.d(TAG, "performSearchNameAndDate started");
        Log.d(TAG, "Doctor Name: " + doctorNameS);

        DatabaseReference availabilityRefS = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefS.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> doctorsNameDateList = new ArrayList<>();

                for (DataSnapshot doctorSnapshotS : snapshot.getChildren()) {
                    String doctorNameDateS = doctorSnapshotS.child("Name").getValue(String.class);

                    if (doctorNameDateS != null && removeWhitespaceAndToLower(doctorNameDateS).contains(removeWhitespaceAndToLower(doctorNameS))) {
                        for (DataSnapshot sessionSnapshotDateS : doctorSnapshotS.getChildren()) {
                            if (!sessionSnapshotDateS.getKey().contains("Name")) {
                                String locationDateS = sessionSnapshotDateS.child("LName").getValue(String.class);

                                for (DataSnapshot daySnapshotDateS : sessionSnapshotDateS.getChildren()) {
                                    if (!daySnapshotDateS.getKey().contains("LName")) {
                                        String dateDateS = daySnapshotDateS.child("Date").getValue(String.class);

                                        Log.d(TAG, "Comparing selectedDateS: " + selectedDateS + " with dateDateS: " + dateDateS);

                                        if (selectedDateS.equals(dateDateS)) {
                                            Log.d(TAG, "Dates matched for doctor: " + doctorNameDateS);
                                            Query query2DateS = FirebaseDatabase.getInstance().getReference().child("Doctors");
                                            query2DateS.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot doctorSnapshotDateS : snapshot.getChildren()) {
                                                        String name2DateS = doctorSnapshotDateS.child("Name").getValue(String.class);
                                                        String specDateS = doctorSnapshotDateS.child("Specialization").getValue(String.class);
                                                        ArrayList<String> locationsDateListS = new ArrayList<>();
                                                        for (DataSnapshot locationSnapshotDateS : doctorSnapshotDateS.child("Locations").getChildren()) {
                                                            String locationS = locationSnapshotDateS.getValue(String.class);

                                                            if (locationS != null && locationS.contains(locationDateS)) {
                                                                locationsDateListS.add(locationS);
                                                            }                                                    }

                                                        if (name2DateS != null && removeWhitespaceAndToLower(name2DateS).contains(removeWhitespaceAndToLower(doctorNameDateS))
                                                               ) {
                                                            Log.d(TAG, "Doctor matched: " + name2DateS);
                                                            Doctors doctorDateS = new Doctors(name2DateS, specDateS, locationsDateListS);
                                                            Log.d(TAG, "D: " + doctorDateS);
                                                            doctorsNameDateList.add(doctorDateS);
                                                        }
                                                    }

                                                    Log.d(TAG, "Doctors matched: " + doctorsNameDateList);

                                                    DocSearchResultAdapter docSearchResultAdapterNameDateS = new DocSearchResultAdapter(doctorsNameDateList);
                                                    recyclerView.setAdapter(docSearchResultAdapterNameDateS);
                                                    Log.d(TAG, "Setting adapter for RecyclerView");

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void performSearchLocationAndDate(String locationSV, String selectedDateS) {
        Log.d(TAG, "performSearchLocationAndDate started");
        Log.d(TAG, "Location: " + locationSV);

        DatabaseReference availabilityRefS = FirebaseDatabase.getInstance().getReference("Availability");

        availabilityRefS.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Doctors> doctorsLocationDateList = new ArrayList<>();

                for (DataSnapshot doctorSnapshotS : snapshot.getChildren()) {
                    String doctorNameDateS = doctorSnapshotS.child("Name").getValue(String.class);

                    for (DataSnapshot sessionSnapshotDateS : doctorSnapshotS.getChildren()) {
                        if (!sessionSnapshotDateS.getKey().contains("Name")) {
                            String locationDateS = sessionSnapshotDateS.child("LName").getValue(String.class);

                            if (locationDateS != null && removeWhitespaceAndToLower(locationDateS).contains(removeWhitespaceAndToLower(locationSV))) {
                                for (DataSnapshot daySnapshotDateS : sessionSnapshotDateS.getChildren()) {
                                    if (!daySnapshotDateS.getKey().contains("LName")) {
                                        String dateDateS = daySnapshotDateS.child("Date").getValue(String.class);

                                        Log.d(TAG, "Comparing selectedDateS: " + selectedDateS + " with dateDateS: " + dateDateS);

                                        if (selectedDateS.equals(dateDateS)) {
                                            Log.d(TAG, "Dates matched for doctor: " + doctorNameDateS);
                                            Query query2DateS = FirebaseDatabase.getInstance().getReference().child("Doctors");
                                            query2DateS.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    for (DataSnapshot doctorSnapshotDateS : snapshot.getChildren()) {
                                                        String name2DateS = doctorSnapshotDateS.child("Name").getValue(String.class);
                                                        String specDateS = doctorSnapshotDateS.child("Specialization").getValue(String.class);
                                                        ArrayList<String> locationsDateListS = new ArrayList<>();
                                                        for (DataSnapshot locationSnapshotDateS : doctorSnapshotDateS.child("Locations").getChildren()) {
                                                            String locationS = locationSnapshotDateS.getValue(String.class);

                                                            if (locationS != null && locationS.contains(locationDateS)) {
                                                                locationsDateListS.add(locationS);
                                                            }
                                                        }

                                                        if (name2DateS != null && removeWhitespaceAndToLower(name2DateS).contains(removeWhitespaceAndToLower(doctorNameDateS)) &&
                                                                specDateS != null ) {
                                                            Log.d(TAG, "Doctor matched: " + name2DateS);
                                                            Doctors doctorDateS = new Doctors(name2DateS, specDateS, locationsDateListS);
                                                            Log.d(TAG, "D: " + doctorDateS);
                                                            doctorsLocationDateList.add(doctorDateS);
                                                        }
                                                    }

                                                    Log.d(TAG, "Doctors matched: " + doctorsLocationDateList);

                                                    DocSearchResultAdapter docSearchResultAdapterLocationDateS = new DocSearchResultAdapter(doctorsLocationDateList);
                                                    recyclerView.setAdapter(docSearchResultAdapterLocationDateS);
                                                    Log.d(TAG, "Setting adapter for RecyclerView");

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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


    private void showDatePickerDialogNL(String name, String loc) {
        Log.d(TAG, "showDatePickerDialogNL() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchNameLocationAndDate(name, loc, formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }


    private void showDatePickerDialogEverything(String name, String spec, String loc) {
        Log.d(TAG, "showDatePickerDialogEverything() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchNameSpecializationLocationAndDate(name, spec, loc, formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void showDatePickerDialogNS(String name, String spec) {
        Log.d(TAG, "showDatePickerDialogNS() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchNameSpecializationAndDate(name, spec, formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void showDatePickerDialogLS(String spec, String loc) {
        Log.d(TAG, "showDatePickerDialogLS() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchSpecializationLocationAndDate(spec, loc, formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void showDatePickerDialogN(String name) {
        Log.d(TAG, "showDatePickerDialogN() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchNameAndDate(name, formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void showDatePickerDialogL(String location) {
        Log.d(TAG, "showDatePickerDialogL() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchLocationAndDate(location, formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    private void showDatePickerDialogD() {
        Log.d(TAG, "showDatePickerDialogD() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchDate(formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }

    public void showDatePickerDialogS(String specializationV) {
        Log.d(TAG, "showDatePickerDialogS() called");

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        CustomDatePicker datePickerDialog = new CustomDatePicker(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                // Do something with the selected date
                String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%02d", selectedDay, selectedMonth + 1, selectedYear % 100);
                etDate.setText(formattedDate); // Set the text of the datePickerButton to the selected date
                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "1");
                        performSearchSpecializationAndDate(specializationV, formattedDate);
                    }
                });
            }
        }, year, month, day);

        // Set the minimum date (current local date)
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

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