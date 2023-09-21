
package com.example.capstoneprojectgroup4.search_doctors;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookAppointmentF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookAppointmentF extends Fragment {

    private static final String ARG_DOCTOR_NAME = "doctorName";
    private static final String ARG_DAY = "day";

    private static final String ARG_NOAPP = "noApp";
    private String doctorName;
    private String noApp;
    private String day;
    private EditText patientName ;
    private Button UploadAppointment ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;



    public BookAppointmentF() {
        // Required empty public constructor
    }

    public static BookAppointmentF newInstance(String doctorName, String day, String noApp) {
        BookAppointmentF fragment = new BookAppointmentF();
        Bundle args = new Bundle();
        args.putString(ARG_DOCTOR_NAME, doctorName);
        args.putString(ARG_DAY, day);
        args.putString(ARG_NOAPP,noApp);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            doctorName = getArguments().getString(ARG_DOCTOR_NAME);
            day = getArguments().getString(ARG_DAY);
            noApp = getArguments().getString(ARG_NOAPP);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);
        TextView doctorNameTextView = view.findViewById(R.id.textDoctorNameValue);
        TextView dayTextView = view.findViewById(R.id.textDateTimeValue);
        TextView noAppTextView = view.findViewById(R.id.textAppointmentNumberValue);
        ImageView backButton = view.findViewById(R.id.backButtonAppoint);

        // Set the doctor's name and day to the TextViews
        doctorNameTextView.setText(doctorName);
        dayTextView.setText(day);
        noAppTextView.setText(noApp);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DocAvailF searchDoctors = new DocAvailF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        patientName = view.findViewById(R.id.textPatientNameValue);
        EditText appointmentType = view.findViewById(R.id.textAppointmentType); // Add this line
        UploadAppointment = view.findViewById(R.id.buttonConfirmAppointment);

        UploadAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPatientName = patientName.getText().toString();
                String getAppointmentType = appointmentType.getText().toString(); // Get the appointment type from EditText

                uploadAppointment(getPatientName, doctorName, day, getAppointmentType); // Pass appointment type
            }
        });

        return view;
    }
    public void uploadAppointment(String pPatientName, String pDoctorName, String pDay, String VoiceVideoCallType) {
        // Sanitize the strings to remove invalid characters
        String sanitizedPatientName = pPatientName.replaceAll("[.#$\\[\\]]", "_");
        String sanitizedDoctorName = pDoctorName.replaceAll("[.#$\\[\\]]", "_");
        String sanitizedDay = pDay.replaceAll("[.#$\\[\\]]", "_");

        HashMap<String, Object> phashMap = new HashMap<>();
        phashMap.put("Doctor Name", sanitizedDoctorName);
        phashMap.put("Patient Name", sanitizedPatientName);
        phashMap.put("Day", sanitizedDay);
        phashMap.put("Appointment Type", VoiceVideoCallType); // Include the appointment type

        databaseReference.child("Appointment Data")
                .child(sanitizedPatientName + sanitizedDoctorName + sanitizedDay)
                .setValue(phashMap);
        Toast.makeText(requireContext(), "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
    }

    public static void uploadAppointment(String pPatientName, String pDoctorName, String pDay){

        HashMap<String, Object> hashMap = new HashMap<> ();
        hashMap.put("Doctor Name", pPatientName);
        hashMap.put("Patient Name", pDoctorName);
        hashMap.put("Day", pDay);

        FirebaseDatabase firebaseDatabaseSecond ;
        firebaseDatabaseSecond = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabaseSecond.getReference();
        databaseReference.child("Appointment Data")
                .child(pPatientName+pDoctorName+pDay)
                .setValue(hashMap);

    }
}
