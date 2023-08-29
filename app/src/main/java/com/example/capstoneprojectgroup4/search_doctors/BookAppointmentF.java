
package com.example.capstoneprojectgroup4.search_doctors;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

        // Set the doctor's name and day to the TextViews
        doctorNameTextView.setText(doctorName);
        dayTextView.setText(day);
        noAppTextView.setText(noApp);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        patientName = view.findViewById(R.id.textPatientNameValue);
        UploadAppointment = view.findViewById(R.id.buttonConfirmAppointment);

        UploadAppointment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String getPatientName = patientName.getText().toString();

                HashMap<String, Object> hashMap = new HashMap<> ();
                hashMap.put("Doctor Name", doctorName);
                hashMap.put("Patient Name", getPatientName);
                hashMap.put("Day", day);


                databaseReference.child("Appointment Data")
                        //.child(getPatientName)
                        .setValue(hashMap);
                Toast.makeText(requireContext(), "Appointment Booked Successfully", Toast.LENGTH_SHORT).show();

            }

        });

        return view;
    }
}
