package com.example.capstoneprojectgroup4.search_doctors;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.text.DateFormat;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import android.telephony.SmsManager;


import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;

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
    private static final String ARG_PRICE = "docPrice";
    private static final String TAG = "BookAppointmentF";
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
    private TextView patientName ;
    private Button UploadAppointment ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    private String selectedAppointmentType;

    private static final int PAYHERE_REQUEST = 110;
    private Map<String, Object> Transaction = new HashMap<>();
    private String item;
    private double TotalFees;

    private String getPatientName;

    private String phonenum;

    private String getAppointmentType;

    private String PatientID;

    private String email;

    public BookAppointmentF() {
        // Required empty public constructor
    }

    public static BookAppointmentF newInstance(String doctorName, String date, String day,String start, String End, String noApp, String location, double docPrice) {
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
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);


        // Initialize TextViews and other views
        TextView doctorNameTextView = view.findViewById(R.id.textDoctorNameValue2);
        TextView dayTextView = view.findViewById(R.id.textDateTimeValue);
        TextView noAppTextView = view.findViewById(R.id.textAppointmentNumberValue2);
        ImageView previousButton = view.findViewById(R.id.backButtonAppoint2);
        TextView TotalPrice = view.findViewById(R.id.TotalTv);
        TextView AppointmentFees = view.findViewById(R.id.AdminfeesTv);
        // Initialize appointmentType EditText and UploadAppointment Button
        UploadAppointment = view.findViewById(R.id.buttonConfirmAppointment2);
        Spinner appointmentTypeSpinner = view.findViewById(R.id.spinner_appointmentType);

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
        TotalFees = docPrice + 100;
        TotalPrice.setText("Rs " + String.valueOf((int) TotalFees) + ".00"); // Convert double to String

        String[] appointmentTypes = {"Appointment type", "Voice", "Video"};

        ArrayAdapter<String> arrayAdapterBrands = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, appointmentTypes);
        arrayAdapterBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        appointmentTypeSpinner.setAdapter(arrayAdapterBrands);

        appointmentTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedAppointmentType = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

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
                email = MainActivity.getPatientObject().getEmail();

                if(selectedAppointmentType.equals("Appointment type")){
                    Toast.makeText(getActivity(), "Please choose the type of appointment.", Toast.LENGTH_SHORT).show();
                    return;
                }

                getPatientName = MainActivity.getPatientObject().getFirstName();
                String getPatientLastName = MainActivity.getPatientObject().getLastName();
                email = MainActivity.getPatientObject().getEmail();
                phonenum = MainActivity.getPatientObject().getMobile();
                String address = MainActivity.getPatientObject().getAddress();
                String city = MainActivity.getPatientObject().getCity();
                String country = MainActivity.getPatientObject().getCountry();
                getAppointmentType = selectedAppointmentType;

                // Generate a unique key for the appointment
                patientKey = MainActivity.getPatientObject().getUid();
                appointmentKey = databaseReference.child("Appointment Data").child(patientKey).push().getKey();
                AppointmentKeyGenerator.setAppointmentKey(appointmentKey);
                String sanitizedDoctorName = doctorName.replaceAll("[.#$\\[\\]]", "_");
                AppointmentKeyGenerator.setDoctorName(sanitizedDoctorName);

                //Opening a payhere connection to complete the payment for the appointment booking (appointment booked only after the payment is successfully done )
                item = getAppointmentType +" appointment with "+doctorName;
                double price = TotalFees;

                InitRequest req = new InitRequest();
                req.setMerchantId("1223432");
                req.setMerchantSecret("MTczNTk3NTUzNDEzMjE5ODgyNTAzNzk2MzAxNTgzMzEwNTk2NTgw");// Merchant ID
                req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
                req.setAmount(price);             // Final Amount to be charged
                req.setOrderId("230000128");        // Unique Reference ID
                req.setItemsDescription(item);  // Item description title
                req.setCustom1("This is the custom message 1");
                req.setCustom2("This is the custom message 2");
                req.getCustomer().setFirstName(getPatientName);
                req.getCustomer().setLastName(getPatientLastName);
                req.getCustomer().setEmail(email);
                req.getCustomer().setPhone(phonenum);
                req.getCustomer().getAddress().setAddress(address);
                req.getCustomer().getAddress().setCity(city);
                req.getCustomer().getAddress().setCountry(country);
                req.getItems().add(new Item(null, item, 1, price));

                temp(req);

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
    private boolean isOneDayBeforeAppointmentDate(String appointmentDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currentDate = Calendar.getInstance();
        Calendar appointmentCalendar = Calendar.getInstance();

        try {
            Date date = dateFormat.parse(appointmentDate);
            appointmentCalendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        // Check if the current date is one day before the appointment date
        currentDate.add(Calendar.DAY_OF_MONTH, 1);

        return currentDate.get(Calendar.YEAR) == appointmentCalendar.get(Calendar.YEAR) &&
                currentDate.get(Calendar.MONTH) == appointmentCalendar.get(Calendar.MONTH) &&
                currentDate.get(Calendar.DAY_OF_MONTH) == appointmentCalendar.get(Calendar.DAY_OF_MONTH);
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);
            Toast.makeText(requireContext(), "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(requireContext(), "Failed to send SMS", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }


    //Initiate a Payment Request to PayHere Payment Gateway
    public void temp(InitRequest req)
    {
        Intent intent = new Intent(getActivity(), PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
        startActivityForResult(intent, PAYHERE_REQUEST); //unique request ID e.g. "11001"
    }

    //Fetch the Payment Status
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess()){
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        String strDate = dateFormat.format(currentTime);
                        String itemname = item;
                        PatientID = MainActivity.getPatientObject().getUid();
                        Transaction.put("name", itemname);
                        Transaction.put("item",getAppointmentType+" appointment, Quantity 1, "+"LKR "+TotalFees);
                        Transaction.put("date", strDate);
                        Transaction.put("price", "LKR "+TotalFees);
                        Transaction.put("description",itemname+" from "+start+" to "+End+" Appointment number : "+New_NoAppValue);
                        Transaction.put("patientID",PatientID);
                        databaseReference.child("Transaction").child("IDA " + strDate).setValue(Transaction);
                        msg = "Activity result:" + response.getData().toString();
                        //after successful payment book appointment
                        Log.d(TAG, "Generated appointmentKey: " + appointmentKey);


                        uploadAppointment(email, getPatientName, doctorName, day, start, End, getAppointmentType, location, New_NoAppValue, PatientID);
                        uploadDoctorAppointment( doctorName, getPatientName, email,day, appointmentKey, getAppointmentType, location, New_NoAppValue,start, End, PatientID);

                        updateAvailability(doctorName, location, date, New_NoAppValue);

                        phonenum = MainActivity.getPatientObject().getMobile();
                        if (isOneDayBeforeAppointmentDate(date)) {
                            sendSMS(phonenum, "Your Appointment at " + location + " with " + doctorName + "is Tomorrow, Please Don't forget !");
                        }
                    }
                    else
                        msg = "Result:" + response.toString();
                else
                    msg = "Result: no response";
                Log.d(TAG, msg);
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null)
                    Toast.makeText(requireContext(), response.toString(), Toast.LENGTH_SHORT).show();

                else
                    Toast.makeText(requireContext(), "User canceled the request", Toast.LENGTH_SHORT).show();
            }
        }
    }

}

