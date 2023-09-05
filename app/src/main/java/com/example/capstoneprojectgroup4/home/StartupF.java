package com.example.capstoneprojectgroup4.home;
import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.Activity_Common;
import com.example.capstoneprojectgroup4.Activity_Remote_Consultation;
import com.example.capstoneprojectgroup4.PharmaciesF;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.ResultActivity;
import com.example.capstoneprojectgroup4.SearchDocF;
import com.example.capstoneprojectgroup4.authentication.AuthenticationHomeF;
import com.example.capstoneprojectgroup4.home.HomeFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartupF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartupF extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button authenticationButton;
    Button transactionButton;
    Button pharmacyButton;
    Button buttonMedicalRecords;
    Button searchdoc;

    Button consultations;



    Button searchDrugs;

    FragmentManager fm;

    public StartupF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment startup_page.
     */
    // TODO: Rename and change types and number of parameters
    public static StartupF newInstance(String param1, String param2) {
        StartupF fragment = new StartupF();
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
        View v = inflater.inflate(R.layout.fragment_startup_page, container, false);

        //Hello
        authenticationButton = v.findViewById(R.id.authentication_button);
        transactionButton = v.findViewById(R.id.transaction_button);
        pharmacyButton = v.findViewById(R.id.pharmacy_button);
        buttonMedicalRecords = v.findViewById(R.id.button_MedicalRecords);
        searchdoc = v.findViewById(R.id.searchDoc_button);
        searchDrugs = v.findViewById(R.id.search_drugs_button);
        consultations = v.findViewById(R.id.consultations);


        authenticationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                AuthenticationHomeF startupFormF = new AuthenticationHomeF();
                fm.beginTransaction().replace(R.id.fragment_container, startupFormF).commit();

            }
        });

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                  Intent intent = new Intent(getActivity(), ResultActivity.class);
                  startActivity(intent);

            }
        });

        pharmacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                PharmaciesF pharmaciesF = new PharmaciesF();
                fm.beginTransaction().replace(R.id.fragment_container, pharmaciesF).commit();

            }
        });

        searchdoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm = getActivity().getSupportFragmentManager();
                SearchDocF searchDocF = new SearchDocF();
                fm.beginTransaction().replace(R.id.fragment_container, searchDocF).commit();

            }
        });


        buttonMedicalRecords.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
//                fm = getActivity().getSupportFragmentManager();
//                WelcomeF welcomeF = new WelcomeF();
//                fm.beginTransaction().replace(R.id.fragment_container, welcomeF).commit();

                Intent intent = new Intent(getActivity(), Activity_Common.class);
                startActivity(intent);

            }
        });

        searchDrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm = getActivity().getSupportFragmentManager();
                HomeFragment homeFragment = new HomeFragment();
                fm.beginTransaction().replace(R.id.fragment_container, homeFragment).commit();
            }
        });


        consultations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(), Activity_Remote_Consultation.class);
                startActivity(intent);

            }
        });



        return v;
    }
}