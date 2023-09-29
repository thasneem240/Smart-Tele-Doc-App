package com.example.capstoneprojectgroup4.search_doctors;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import androidx.annotation.NonNull;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocAvailF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocAvailF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;

    AvailAdapter availAdapter;

    public DocAvailF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param doctorName Parameter 1.
     * @param location Parameter 2.
     * @return A new instance of fragment DocAvailF.
     */
    // TODO: Rename and change types and number of parameters
    public static DocAvailF newInstance(String doctorName, String location) {
        DocAvailF fragment = new DocAvailF();
        Bundle args = new Bundle();
        args.putString("doctorName", doctorName);
        args.putString("location", location);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doc_avail, container, false);
        TextView textDoctorName = view.findViewById(R.id.textDoctorName);
        TextView textDoctorLocation = view.findViewById(R.id.textDoctorLocation);
        ImageView backButton = view.findViewById(R.id.backButtonDocAvail);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SearchDocF searchDoctors = new SearchDocF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            String doctorNameV = getArguments().getString("doctorName");
            String locationV = getArguments().getString("location");
            Log.d("DocAvailF", "Doctor Name: " + doctorNameV);
            Log.d("DocAvailF", "Location: " + locationV);
            if (doctorNameV != null && locationV != null) {
                DatabaseReference availabilityRef = FirebaseDatabase.getInstance().getReference("Availability");

                Query query = availabilityRef.orderByChild("Name").equalTo(doctorNameV);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        ArrayList<Availability> sessionDetails = new ArrayList<>();

                        if (snapshot.exists()) {
                            DataSnapshot doctorData = snapshot.child("d1"); // Change this to match the specific doctor's data

                            if (doctorData != null) {
                                String doctorName = doctorData.child("Name").getValue(String.class);

                                if (doctorName != null) {
                                    textDoctorName.setText(doctorName);
                                }

                                DataSnapshot locationData = doctorData.child("l1"); // Change this to match the specific location's data

                                if (locationData != null) {
                                    String locationName = locationData.child("LName").getValue(String.class);

                                    if (locationName != null) {
                                        textDoctorLocation.setText(locationName);
                                    }

                                    // Get the current date and time in the device's local time zone
                                    Calendar calendar = Calendar.getInstance();
                                    Date currentDateTime = calendar.getTime();

                                    SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
                                    dateTimeFormat.setTimeZone(TimeZone.getDefault()); // Set to local time zone

                                    for (DataSnapshot dayData : locationData.getChildren()) {
                                        if (dayData.getKey() != null && !dayData.getKey().equals("LName")) {
                                            String day = dayData.getKey();
                                            String date = dayData.child("Date").getValue(String.class);
                                            String startTime = dayData.child("StartTime").getValue(String.class);
                                            String endTime = dayData.child("EndTime").getValue(String.class);
                                            int noApp = dayData.child("NoApp").getValue(Integer.class);

                                            if (day != null && date != null && startTime != null && endTime != null) {
                                                try {
                                                    Date sessionDateTime = dateTimeFormat.parse(date + " " + startTime);

                                                    // Compare session date-time with the current date-time
                                                    if (sessionDateTime != null && sessionDateTime.after(currentDateTime)) {
                                                        Availability sessionObject = new Availability(doctorName, locationName, day, noApp, endTime, startTime, date);
                                                        sessionDetails.add(sessionObject);
                                                    }
                                                } catch (ParseException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }

                                    // Sort the sessionDetails list based on the day of the week
                                    Collections.sort(sessionDetails, new Comparator<Availability>() {
                                        @Override
                                        public int compare(Availability o1, Availability o2) {
                                            // Define the order of days of the week
                                            String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

                                            // Get the index of the day for each availability object
                                            int index1 = Arrays.asList(daysOfWeek).indexOf(o1.getDay());
                                            int index2 = Arrays.asList(daysOfWeek).indexOf(o2.getDay());

                                            // Compare based on the index to sort in the desired order
                                            return Integer.compare(index1, index2);
                                        }
                                    });

                                    // Create the adapter and set it to the RecyclerView
                                    AvailAdapter availAdapter = new AvailAdapter(sessionDetails, doctorName, "", 0, "", locationName);
                                    recyclerView.setAdapter(availAdapter);
                                }
                            }
                        }
                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            } else {
                Toast.makeText(getContext(), "Doctor name and location not provided.", Toast.LENGTH_SHORT).show();
            }
        }

        return view;
    }
}
