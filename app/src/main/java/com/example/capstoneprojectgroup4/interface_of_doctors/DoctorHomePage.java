package com.example.capstoneprojectgroup4.interface_of_doctors;

import static androidx.fragment.app.FragmentManager.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_DoctorsView.ListOfPatientsFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorHomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorHomePage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorHomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorHomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorHomePage newInstance(String param1, String param2) {
        DoctorHomePage fragment = new DoctorHomePage();
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
        View v = inflater.inflate(R.layout.fragment_doctor_home_page, container, false);

        Button listOfPatients = v.findViewById(R.id.Button_listOfPrescriptions);

        listOfPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPatientsFragment listOfPatientsFragment = new ListOfPatientsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment).commit();

            }
        });


        return v;
    }
}