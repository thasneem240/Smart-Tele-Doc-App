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

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
    private  int New_NoAppValue;
    private String patientKey;
    private String appointmentKey;
    private Button UploadAppointment ;
    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference;
    public AppHistoryBookAppointmentF() {
        // Required empty public constructor
    }


    public static AppHistoryBookAppointmentF newInstance(String doctorName, String date, String day,String start, String End, String noApp, String location) {
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_app_history_book_appointment, container, false);
        ImageView previousButton = view.findViewById(R.id.backButtonAppoint2);

        TextView noAppTextView = view.findViewById(R.id.textAppointmentNumberValue2);
        TextView doctorNameTextView = view.findViewById(R.id.textDoctorNameValue2);
        TextView dayTextView = view.findViewById(R.id.textDateTimeValue);
        // Set the doctor's name and day to the TextViews
        doctorNameTextView.setText(doctorName);
        dayTextView.setText(day + " "+ start+"-"+ End);
        New_NoAppValue = Integer.valueOf(noApp) + 1;
        noAppTextView.setText(String.valueOf(New_NoAppValue));
        // Get patient's name and set it to the patientName TextView
        String patientName = MainActivity.getPatientObject().getFirstName();
        TextView patientNameTextView = view.findViewById(R.id.textPatientNameValue2);
        patientNameTextView.setText(patientName);

        // Initialize appointmentType EditText and UploadAppointment Button
        EditText appointmentType = view.findViewById(R.id.textAppointmentType2);
        UploadAppointment = view.findViewById(R.id.buttonConfirmAppointment2);



        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack("AppHistoryDocAvailF", FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });

        return view;


    }
}