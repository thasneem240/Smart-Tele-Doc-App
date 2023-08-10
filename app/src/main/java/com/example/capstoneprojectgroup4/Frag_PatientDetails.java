package com.example.capstoneprojectgroup4;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_PatientDetails#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_PatientDetails extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private EditText editText_Title;
    private EditText editText_FirName;
    private EditText editText_SecName;
    private EditText editText_NIC;
    private EditText editText_Email;
    private EditText editText_Address;


    public Frag_PatientDetails()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_PatientDetails.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_PatientDetails newInstance(String param1, String param2) {
        Frag_PatientDetails fragment = new Frag_PatientDetails();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patient_details, container, false);

        /* Grab the  UI Variables from Layout file */

        editText_Title = view.findViewById(R.id.editText_Title);
        editText_FirName = view.findViewById(R.id.editText_FirName);
        editText_SecName = view.findViewById(R.id.editText_SecName);
        editText_NIC = view.findViewById(R.id.editText_NIC);
        editText_Email = view.findViewById(R.id.editText_Email);
        editText_Address = view.findViewById(R.id.editText_Address);


        return view;
    }
}