package com.example.capstoneprojectgroup4.search_doctors;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppHistoryBookAppointmentF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppHistoryBookAppointmentF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String ARG_DOCTOR_NAME = "doctorName";
    private static final String ARG_DAY = "day";
    private static final String ARG_DATE = "date";
    private static final String ARG_START = "start";
    private static final String ARG_END = "End";
    private static final String ARG_LOCATION = "location";
    private static final String ARG_NOAPP = "noApp";
    private static final String ARG_PRICE = "docPrice";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String doctorName;
    private String noApp;
    private String location;
    private String day;
    private String date;
    private String start;
    private String End;
    private double docPrice;
    private  int New_NoAppValue;
    private String patientKey;
    private String appointmentKey;
    private Button UploadAppointment ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    public AppHistoryBookAppointmentF() {
        // Required empty public constructor
    }


    public static AppHistoryBookAppointmentF newInstance(String doctorName, String date, String day,String start, String End, String noApp, String location, double docPrice) {
        AppHistoryBookAppointmentF fragment = new AppHistoryBookAppointmentF();
        Bundle args = new Bundle();
        args.putString(ARG_DOCTOR_NAME, doctorName);
        args.putString(ARG_DAY, day);
        args.putString(ARG_START, start);
        args.putString(ARG_END, End);
        args.putString(ARG_DAY, day);
        args.putString(ARG_DATE, date);
        args.putString(ARG_NOAPP,noApp);
        args.putString(ARG_LOCATION,location);
        args.putDouble(ARG_PRICE,docPrice);
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
            docPrice = getArguments().getDouble(ARG_PRICE);
        }
        // Initialize the Firebase Database reference here
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(); // You can adjust the reference path as needed

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_history_book_appointment, container, false);
        ImageView previousButton = view.findViewById(R.id.backButtonAppoint2);

        TextView noAppTextView = view.findViewById(R.id.textAppointmentNumberValue2);
        TextView doctorNameTextView = view.findViewById(R.id.textDoctorNameValue2);
        TextView dayTextView = view.findViewById(R.id.textDateTimeValue);

        TextView TotalPrice = view.findViewById(R.id.TotalTv);
        TextView AppointmentFees = view.findViewById(R.id.AdminfeesTv);
        // Set the doctor's name and day to the TextViews
        doctorNameTextView.setText(doctorName);
        dayTextView.setText(day + " "+ start+"-"+ End);
        New_NoAppValue = Integer.valueOf(noApp) + 1;
        noAppTextView.setText(String.valueOf(New_NoAppValue));
        // Get patient's name and set it to the patientName TextView
        String patientName = MainActivity.getPatientObject().getFirstName();
        TextView patientNameTextView = view.findViewById(R.id.textPatientNameValue2);
        patientNameTextView.setText(patientName);

        // Format and set the appointment fees and total price as text
        AppointmentFees.setText("Rs " + String.valueOf((int) docPrice) + ".00"); // Convert double to String
        double TotalFees = docPrice + 100;
        TotalPrice.setText("Rs " + String.valueOf((int) TotalFees) + ".00"); // Convert double to String


        // Initialize appointmentType EditText and UploadAppointment Button
        EditText appointmentType = view.findViewById(R.id.textAppointmentType2);
        UploadAppointment = view.findViewById(R.id.buttonConfirmAppointment2);



        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("DocAvailF", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

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


                Log.d(TAG, "Generated appointmentKey: " + appointmentKey);
                String PatientID = MainActivity.getPatientObject().getUid();

                uploadAppointment(email, getPatientName, doctorName, day, start, End, getAppointmentType, location, New_NoAppValue, PatientID);
                uploadDoctorAppointment( doctorName, getPatientName, email,day, appointmentKey, getAppointmentType, location, New_NoAppValue,start, End, PatientID);

            }
        });

        return view;

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

}