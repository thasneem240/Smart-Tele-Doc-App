package com.example.capstoneprojectgroup4.search_doctors;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        if (getArguments() != null) {
            String doctorNameV = getArguments().getString("doctorName");
            String locationV = getArguments().getString("location");

            if (doctorNameV != null && locationV != null) {
                DatabaseReference availabilityRef = FirebaseDatabase.getInstance().getReference("Availability");

                Query query = availabilityRef.orderByChild("Name").equalTo(doctorNameV);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Map <String, Object> doctors;
                        Map <String, Object> detailsOfEachDoctor;
                        Map <String, Object> eachSession;
                        Map <String, Object> eachDay;
                        String doctorName, endTime = null, location = null, startTime = null,  day = null, date=null;
                        int noApp = 0;
                        ArrayList<Availability> sessionDetails = new ArrayList<>();

                        doctors = (Map) snapshot.getValue();

                        for (Map.Entry<String, Object> eachDoctor : doctors.entrySet()) {
                            detailsOfEachDoctor = (Map) eachDoctor.getValue();
                            doctorName = detailsOfEachDoctor.get("Name")+"";
                            if (doctorNameV.equals(doctorName)) {

                                for (Map.Entry<String, Object> allSessionsEntry : detailsOfEachDoctor.entrySet()) {
                                    if (!allSessionsEntry.getKey().equals("Name")) {
                                        eachSession = (Map) allSessionsEntry.getValue();

                                        location = eachSession.get("LName") + "";
                                        if (locationV.equals(location)) {

                                            for (Map.Entry<String, Object> sevenDays : eachSession.entrySet()) {
                                                if (!sevenDays.getKey().equals("LName")) {
                                                    day = sevenDays.getKey();

                                                    eachDay = (Map) sevenDays.getValue();

                                                    endTime = eachDay.get("EndTime") + "";
                                                    startTime = eachDay.get("StartTime") + "";
                                                    noApp = Math.toIntExact((Long) eachDay.get("NoApp"));                                                    date = eachDay.get("Date") + "";

                                                    Availability sessionObject = new Availability(doctorName, location, day, noApp, endTime, startTime, date);
                                                    sessionDetails.add(sessionObject);
                                                }
                                            }
                                            textDoctorName.setText(doctorName);
                                            textDoctorLocation.setText(location);
                                        }

                                    }


                                }
                            }
                            Collections.sort(sessionDetails, (availability1, availability2) -> {
                                String day1 = availability1.getDay();
                                String day2 = availability2.getDay();
                                // Assuming day values are Monday, Tuesday, ..., Sunday
                                List<String> daysOrder = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");
                                return Integer.compare(daysOrder.indexOf(day1), daysOrder.indexOf(day2));
                            });
                            // added param docName
                            AvailAdapter availAdapter1 = new AvailAdapter(sessionDetails, doctorName, day, noApp, date );
                            recyclerView.setAdapter(availAdapter1);



                        }

                        Log.d(TAG, String.format("%s ", sessionDetails+"")); // ArrayList<SessionObject>

/*                FragmentManager fm = getSupportFragmentManager();
                _Fragment _fragment = new _Fragment(sessionDetails);
                fm.beginTransaction().replace(R.id.fragment_container, _fragment).commit();*/
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