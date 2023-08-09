package com.example.capstoneprojectgroup4;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_Manage_Patient_Record#newInstance} factory method to
 * create an instance of this fragment.
 * Fragment To Manage Patients Records
 */
public class Frag_Manage_Patient_Record extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    /* Initialize Variables */

    private Button button_PatientDetails;
    private Button button_MedicalHistory;
    private Button button_LabReports;
    private Button button_ConsNotes;
    private Button button_Prescriptions;

    public Frag_Manage_Patient_Record()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Manage_Patient_Record.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_Manage_Patient_Record newInstance(String param1, String param2)
    {
        Frag_Manage_Patient_Record fragment = new Frag_Manage_Patient_Record();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manage__patient__record, container, false);

        /* Grab the  UI Variables from Layout file */

        button_PatientDetails = view.findViewById(R.id.button_PatientDetails);
        button_MedicalHistory = view.findViewById(R.id.button_MedicalHistory);
        button_LabReports = view.findViewById(R.id.button_LabReports);
        button_ConsNotes = view.findViewById(R.id.button_ConsNotes);
        button_Prescriptions = view.findViewById(R.id.button_Prescriptions);

        button_PatientDetails.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = Activity_Common.getIntent(getActivity(),"Frag_PatientDetails");
                startActivity(intent);
            }
        });



        button_MedicalHistory.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = Activity_Common.getIntent(getActivity(),"Frag_MedicalHistory");
                startActivity(intent);
            }
        });


        return view;
    }
}