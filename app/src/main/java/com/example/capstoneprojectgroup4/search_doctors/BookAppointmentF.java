package com.example.capstoneprojectgroup4.search_doctors;

import static android.icu.text.MessagePattern.Part.Type.ARG_START;

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
import com.example.capstoneprojectgroup4.home.MainActivity;
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
    private static final String ARG_START = "start";
    private static final String ARG_END = "End";


    private static final String ARG_NOAPP = "noApp";
    private String doctorName;
    private String noApp;
    private String day;
    private String start;
    private String End;


    private TextView patientName ;
    private Button UploadAppointment ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;

    public BookAppointmentF() {
        // Required empty public constructor
    }

    public static BookAppointmentF newInstance(String doctorName, String day,String start, String End, String noApp) {
        BookAppointmentF fragment = new BookAppointmentF();
        Bundle args = new Bundle();
        args.putString(ARG_DOCTOR_NAME, doctorName);
        args.putString(ARG_DAY, day);
        args.putString(ARG_START, start);
        args.putString(ARG_END, End);
        args.putString(ARG_DAY, day);
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
            start = getArguments().getString(ARG_START); // Correct key
            End = getArguments().getString(ARG_END);     // Correct key
            noApp = getArguments().getString(ARG_NOAPP);
        }

        // Initialize the Firebase Database reference here
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(); // You can adjust the reference path as needed
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        // Initialize TextViews and other views
        TextView doctorNameTextView = view.findViewById(R.id.textDoctorNameValue);
        TextView dayTextView = view.findViewById(R.id.textDateTimeValue);
        TextView noAppTextView = view.findViewById(R.id.textAppointmentNumberValue);
        ImageView previousButton = view.findViewById(R.id.backButtonAppoint);

        // Set the doctor's name and day to the TextViews
        doctorNameTextView.setText(doctorName);
        dayTextView.setText(day + " "+ start+"-"+ End);
        noAppTextView.setText(noApp);

        // Get patient's name and set it to the patientName TextView
        String patientName = MainActivity.getPatientObject().getFirstName();
        TextView patientNameTextView = view.findViewById(R.id.textPatientNameValue);
        patientNameTextView.setText(patientName);

        // Initialize appointmentType EditText and UploadAppointment Button
        EditText appointmentType = view.findViewById(R.id.textAppointmentType);
        UploadAppointment = view.findViewById(R.id.buttonConfirmAppointment);

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("DocAvailF", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });





        // Set an OnClickListener for the UploadAppointment Button
        UploadAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String getPatientName = MainActivity.getPatientObject().getFirstName();
                String email = MainActivity.getPatientObject().getEmail();
                String getAppointmentType = appointmentType.getText().toString();
                uploadAppointment(email, getPatientName, doctorName, day, getAppointmentType);
            }
        });

        return view;
    }

    private void uploadAppointment(String email, String pPatientName, String pDoctorName, String pDay, String VoiceVideoCallType) {
        // Sanitize the email to remove invalid characters
        String sanitizedEmail = email.replaceAll("[.#$\\[\\]]", "_");
        String sanitizedPatientName = pPatientName.replaceAll("[.#$\\[\\]]", "_");
        String sanitizedDoctorName = pDoctorName.replaceAll("[.#$\\[\\]]", "_");

        // Create a HashMap to store appointment data
        HashMap<String, Object> appointmentData = new HashMap<>();
        appointmentData.put("PatientName", sanitizedPatientName);
        appointmentData.put("PatientEmail", sanitizedEmail);
        appointmentData.put("DoctorName", sanitizedDoctorName);
        appointmentData.put("AppointmentType", VoiceVideoCallType);
        appointmentData.put("Date", pDay);

        // Use the sanitized email as the unique key for the patient
        String patientKey = MainActivity.getPatientObject().getUid();

        // Generate a unique key for the appointment
        String appointmentKey = databaseReference.child("Appointment Data").child(patientKey).push().getKey();

        // Use the generated key to store the appointment data under the patient's appointments
        databaseReference.child("Appointment Data").child(patientKey).child(appointmentKey).setValue(appointmentData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
                    // Clear the appointment type EditText
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error booking appointment", Toast.LENGTH_SHORT).show();
                });
    }


    public static void uploadAppointmentSecond(String pPatientName, String pDoctorName, String pDay){

        HashMap<String, Object> hashMap = new HashMap<> ();
        hashMap.put("Doctor Name", pDoctorName);
        hashMap.put("Patient Name", pPatientName);
        hashMap.put("Day", pDay);

        FirebaseDatabase firebaseDatabaseSecond ;
        firebaseDatabaseSecond = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabaseSecond.getReference();
        databaseReference.child("Appointment Data")
                .child(pPatientName + pDoctorName + pDay)
                .setValue(hashMap);

    }
}
