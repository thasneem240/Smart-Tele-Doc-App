package com.example.capstoneprojectgroup4.search_doctors;


import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.search_doctors.AppointmentItem;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointmentsAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

public class ViewAppointments extends Fragment {

    private RecyclerView recyclerView;
    private ViewAppointmentsAdapter viewAppointmentsAdapter;
    private BookAppointmentF bookAppointmentFragment;

    private String userId; // User ID obtained from MainActivity

    public ViewAppointments() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize user ID from MainActivity
        userId = MainActivity.getPatientObject().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_appointments, container, false);
        TextView patient = view.findViewById(R.id.patientNameViewApp);
        String name = MainActivity.getPatientObject().getFirstName();
        patient.setText(name);
        recyclerView = view.findViewById(R.id.recyclerAppView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the adapter here
        viewAppointmentsAdapter = new ViewAppointmentsAdapter(new ArrayList<>());
        recyclerView.setAdapter(viewAppointmentsAdapter);


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
                        if (endTime != null && endTime.after(currentDateTime) || endTime.equals(currentDateTime)) {
                            appointments.add(appointment);
                        }
                    }
                }

                // Create and set the adapter with the fetched appointments
                viewAppointmentsAdapter = new ViewAppointmentsAdapter(appointments);
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

