package com.example.capstoneprojectgroup4.front_end;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorAvailability#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorAvailability extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorAvailability() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorAvailability.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorAvailability newInstance(String param1, String param2) {
        DoctorAvailability fragment = new DoctorAvailability();
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
        View v = inflater.inflate(R.layout.fragment_doctor_availability, container, false);
        TextView textDay   = v.findViewById(R.id.textDay);

        textDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                BookAppointment searchDoctors = new BookAppointment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();            }
        });
        // Inflate the layout for this fragment
        return v;
    }
}