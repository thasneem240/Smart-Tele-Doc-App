package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointmentsAdapter;
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
import java.util.List;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorViewAppointments#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorViewAppointments extends Fragment {
    private RecyclerView recyclerView;
    private DoctorViewAppointmentsAdapter viewAppointmentsAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorViewAppointments() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorViewAppointments.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorViewAppointments newInstance(String param1, String param2) {
        DoctorViewAppointments fragment = new DoctorViewAppointments();
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
        View view = inflater.inflate(R.layout.fragment_doctor_view_appointments, container, false);

        ImageView back = view.findViewById(R.id.backButtonDocViewApp);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMainMenu doctorAvailability = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorAvailability).commit();
            }
        });






        recyclerView = view.findViewById(R.id.recyclerDocAppView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Set the adapter here
        viewAppointmentsAdapter = new DoctorViewAppointmentsAdapter(new ArrayList<>());
        recyclerView.setAdapter(viewAppointmentsAdapter);
        Calendar calendar = Calendar.getInstance();
        Date currentDateTime = calendar.getTime();

        // Create a SimpleDateFormat for date and time comparison using the device's local time zone
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yy HH:mm");
        dateTimeFormat.setTimeZone(TimeZone.getDefault()); // Set to the local time zone

        String name = DoctorsActivity.getDoctorObject().getName();
        String sanitizedDoctorName = name.replaceAll("[.#$\\[\\]]", "_");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Doctor Appointments").child(sanitizedDoctorName);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<DoctorAppointmentItemList> appointments = new ArrayList<>();

                for (DataSnapshot appointmentSnapshot : dataSnapshot.getChildren()) {
                    // Deserialize the data into an AppointmentItem object
                    DoctorAppointmentItemList appointment = appointmentSnapshot.getValue(DoctorAppointmentItemList.class);

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
                viewAppointmentsAdapter = new DoctorViewAppointmentsAdapter(appointments);
                recyclerView.setAdapter(viewAppointmentsAdapter);
                viewAppointmentsAdapter.notifyDataSetChanged(); // Notify data change
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle any errors here
            }
        });


        return view;
    }
}