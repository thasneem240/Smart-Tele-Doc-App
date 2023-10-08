package com.example.capstoneprojectgroup4.search_doctors;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    String userId;


    RecyclerView recyclerView;
    AppointmentHistoryAdapter viewAppointmentsAdapter;
    public AppointmentHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentHistory newInstance(String param1, String param2) {
        AppointmentHistory fragment = new AppointmentHistory();
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
        userId = MainActivity.getPatientObject().getUid();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_appointment_history, container, false);

        TextView patient = view.findViewById(R.id.patientNameViewApp2);
        ImageView back = view.findViewById(R.id.backButtonViewApp2);
        String name = MainActivity.getPatientObject().getFirstName();
        patient.setText(name);
         recyclerView = view.findViewById(R.id.appHistoryrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the adapter here
         viewAppointmentsAdapter = new AppointmentHistoryAdapter(new ArrayList<>());
        recyclerView.setAdapter(viewAppointmentsAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fm = getActivity().getSupportFragmentManager();
                ViewAppointments searchDoctors = new ViewAppointments();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        return view;

    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getContext() == null)  {
            return;
        }

        // Get the current date and time in the device's local time zone
        Calendar calendar = Calendar.getInstance();
        Date currentDateTime = calendar.getTime();

        // Create a SimpleDateFormat for date and time comparison using the device's local time zone
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        dateTimeFormat.setTimeZone(TimeZone.getDefault()); // Set to the local time zone

        // Create a database reference to the "Appointment Data" section for the specific user
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Appointment Data")
                .child(userId); // This will reference the appointments for the specific user.

        // Attach a ValueEventListener to fetch appointments
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<AppointmentItem> appointments = new ArrayList<>();

                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    // Deserialize the data into an AppointmentItem object
                    AppointmentItem appointment = appointmentSnapshot.getValue(AppointmentItem.class);

                    if (appointment != null) {
                        // Parse the end time string to a Date object
                        Date endTime;
                        try {
                            endTime = dateTimeFormat.parse(appointment.getDate() + " " + appointment.getEndTime());
                        } catch (ParseException e) {
                            e.printStackTrace();
                            continue; // Skip invalid date-time formats
                        }

                        // Compare end time with the current date-time
                        if (endTime != null && endTime.before(currentDateTime)) {
                            // This appointment is in the past, so add it to the list
                            appointments.add(appointment);
                        }
                    }
                }

                // Create and set the adapter with the fetched past appointments
                viewAppointmentsAdapter = new AppointmentHistoryAdapter(appointments);
                recyclerView.setAdapter(viewAppointmentsAdapter);
                viewAppointmentsAdapter.notifyDataSetChanged(); // Notify data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors here
            }
        });
    }



}