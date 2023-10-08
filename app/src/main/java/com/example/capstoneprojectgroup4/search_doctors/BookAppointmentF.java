package com.example.capstoneprojectgroup4.search_doctors;

import static android.icu.text.MessagePattern.Part.Type.ARG_START;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookAppointmentF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookAppointmentF extends Fragment {

    private static final String ARG_DOCTOR_NAME = "doctorName";
    private static final String ARG_DAY = "day";
    private static final String ARG_DATE = "date";
    private static final String ARG_START = "start";
    private static final String ARG_END = "End";
    private static final String ARG_LOCATION = "location";
    private static final String ARG_NOAPP = "noApp";
    private static final String TAG = "BookAppointmentF";
    private String doctorName;
    private String noApp;
    private String location;
    private String day;
    private String date;
    private String start;
    private String End;
    private  int New_NoAppValue;
    private String patientKey;
    private String appointmentKey;
    private TextView patientName ;
    private Button UploadAppointment ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;



    public BookAppointmentF() {
        // Required empty public constructor
    }

    public static BookAppointmentF newInstance(String doctorName, String date, String day,String start, String End, String noApp, String location) {
        BookAppointmentF fragment = new BookAppointmentF();
        Bundle args = new Bundle();
        args.putString(ARG_DOCTOR_NAME, doctorName);
        args.putString(ARG_DAY, day);
        args.putString(ARG_START, start);
        args.putString(ARG_END, End);
        args.putString(ARG_DAY, day);
        args.putString(ARG_DATE, date);
        args.putString(ARG_NOAPP,noApp);
        args.putString(ARG_LOCATION,location);
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
            location = getArguments().getString(ARG_LOCATION);
            date = getArguments().getString(ARG_DATE);


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
        New_NoAppValue = Integer.valueOf(noApp) + 1;
        noAppTextView.setText(String.valueOf(New_NoAppValue));
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

                // Generate a unique key for the appointment
                patientKey = MainActivity.getPatientObject().getUid();
                appointmentKey = databaseReference.child("Appointment Data").child(patientKey).push().getKey();
                AppointmentKeyGenerator.setAppointmentKey(appointmentKey);
                String sanitizedDoctorName = doctorName.replaceAll("[.#$\\[\\]]", "_");
                AppointmentKeyGenerator.setDoctorName(sanitizedDoctorName);

                //String sanitizedDoctorName = doctorName.replace(".", "_");
                //String sanitizedHospitalName = location.replace(".", "_");
                //String sanitizedDate = date.replace(".", "_");

                //appointmentKey = AppointmentKeyGenerator.generateAppointmentKey(patientKey,sanitizedDoctorName,sanitizedHospitalName,sanitizedDate);
                Log.d(TAG, "Generated appointmentKey: " + appointmentKey);
                String PatientID = MainActivity.getPatientObject().getUid();

                uploadAppointment(email, getPatientName, doctorName, day, start, End, getAppointmentType, location, New_NoAppValue, PatientID);
                uploadDoctorAppointment( doctorName, getPatientName, email,day, appointmentKey, getAppointmentType, location, New_NoAppValue,start, End, PatientID);

                updateAvailability(doctorName, location, date, New_NoAppValue);
            }
        });

        return view;
    }


    private void updateAvailability(String doctorName, String location, String date, int newNoAppValue) {
        DatabaseReference availabilityRef = FirebaseDatabase.getInstance().getReference("Availability");

        // Find the doctor's availability using their name
        Query query = availabilityRef.orderByChild("Name").equalTo(doctorName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String doctorKey = doctorSnapshot.getKey();
                    Log.d(TAG, "Doctor Key: " + doctorKey);

                    // Check both "l1" and "l2" for the specified location
                    DataSnapshot locationSnapshotL1 = doctorSnapshot.child("l1");
                    DataSnapshot locationSnapshotL2 = doctorSnapshot.child("l2");

                    boolean foundInLocationL1 = false;
                    boolean foundInLocationL2 = false;

                    // Check if "LName" matches for "l1"
                    if (locationSnapshotL1.exists()) {
                        if (locationSnapshotL1.child("LName").exists() && locationSnapshotL1.child("LName").getValue(String.class).equals(location)) {
                            foundInLocationL1 = true;
                            Log.d(TAG, "l1: " + foundInLocationL1);
                        }
                    }

                    // Check if "LName" matches for "l2"
                    if (locationSnapshotL2.exists()) {
                        if (locationSnapshotL2.child("LName").exists() && locationSnapshotL2.child("LName").getValue(String.class).equals(location)) {
                            foundInLocationL2 = true;
                            Log.d(TAG, "l2: " + foundInLocationL2);
                        }
                    }

                    if (foundInLocationL1 || foundInLocationL2) {
                        // Check and update "NoApp" value in both locations
                        if (foundInLocationL1) {
                            DataSnapshot daySnapshot = locationSnapshotL1.child(date);
                            if (daySnapshot.exists()) {
                                // Date matches, update "NoApp" value
                                daySnapshot.child("NoApp").getRef().setValue(newNoAppValue);
                                Log.d(TAG, "Updated L1");

                            }
                        }

                        if (foundInLocationL2) {
                            DataSnapshot daySnapshot = locationSnapshotL2.child(date);
                            if (daySnapshot.exists()) {
                                // Date matches, update "NoApp" value
                                daySnapshot.child("NoApp").getRef().setValue(newNoAppValue);
                                Log.d(TAG, "Updated L2");

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
    private void uploadDoctorAppointment(String doctorName, String pPatientName, String pPatientEmail, String pDay, String appointmentKey, String VoiceVideoCallType, String location, int noApp, String start, String end, String PatientID) {
        // Sanitize input values to remove invalid characters
        String sanitizedPatientName = pPatientName.replaceAll("[.#$\\[\\]]", "_");
        String sanitizedDoctorName = doctorName.replaceAll("[.#$\\[\\]]", "_");

        // Create a reference to the "Doctor Appointments" node under the doctor's name
        DatabaseReference doctorAppointmentsRef = databaseReference.child("Doctor Appointments").child(sanitizedDoctorName);

        // Create a HashMap to represent the appointment data
        HashMap<String, Object> appointmentData = new HashMap<>();
        appointmentData.put("Location", location);
        appointmentData.put("AppointmentType", VoiceVideoCallType);
        appointmentData.put("AppointmentNumber", noApp);
        appointmentData.put("PatientName", sanitizedPatientName);
        appointmentData.put("PatientEmail", pPatientEmail);
        appointmentData.put("StartTime", start);
        appointmentData.put("EndTime", end);
        appointmentData.put("Date", pDay);
        appointmentData.put("PatientUserId",PatientID);

        // Use the generated key to store the appointment data under the doctor's appointments
        doctorAppointmentsRef.child(appointmentKey).setValue(appointmentData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Error booking appointment", Toast.LENGTH_SHORT).show();
                });
    }






    private void uploadAppointment(String email, String pPatientName, String pDoctorName, String pDay, String start, String end, String VoiceVideoCallType, String location, int noApp, String PatientID) {

        // Sanitize the email to remove invalid characters

        String sanitizedEmail = email.replaceAll("[.#$\\[\\]]", "_");
        String sanitizedPatientName = pPatientName.replaceAll("[.#$\\[\\]]", "_");
        String sanitizedDoctorName = pDoctorName.replaceAll("[.#$\\[\\]]", "_");

        // Encrypt patientName and email before uploading
        String encryptedPatientName = EncryptionUtil.encrypt(sanitizedPatientName);
        String encryptedEmail = EncryptionUtil.encrypt(sanitizedEmail);



        HashMap<String, Object> appointmentData = new HashMap<>();
        //appointmentData.put("PatientName", sanitizedPatientName);
        //appointmentData.put("PatientEmail", sanitizedEmail);
        appointmentData.put("PatientName", encryptedPatientName);
        appointmentData.put("PatientEmail", encryptedEmail);

        appointmentData.put("DoctorName", sanitizedDoctorName);
        appointmentData.put("Location", location);
        appointmentData.put("AppointmentType", VoiceVideoCallType);
        appointmentData.put("AppointmentNumber", noApp);
        appointmentData.put("StartTime", start);
        appointmentData.put("EndTime", end);
        appointmentData.put("Date", pDay);
        appointmentData.put("PatientUserId",PatientID);

        // Use the generated key to store the appointment data under the patient's appointments
        databaseReference.child("Appointment Data").child(patientKey).child(appointmentKey).setValue(appointmentData)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(requireContext(), "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();

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
