package com.example.capstoneprojectgroup4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StartupF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StartupF extends Fragment {

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
    Button extra1Button;
    Button extra2Button;
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
        extra1Button = v.findViewById(R.id.extra1_button);
        extra2Button = v.findViewById(R.id.extra2_button);

        authenticationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                fm = getActivity().getSupportFragmentManager();
//                WelcomeF welcomeF = new WelcomeF();
//                fm.beginTransaction().replace(R.id.fragment_container, welcomeF).commit();

            }
        });

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fm = getActivity().getSupportFragmentManager();
//                WelcomeF welcomeF = new WelcomeF();
//                fm.beginTransaction().replace(R.id.fragment_container, welcomeF).commit();

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

        extra1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fm = getActivity().getSupportFragmentManager();
//                WelcomeF welcomeF = new WelcomeF();
//                fm.beginTransaction().replace(R.id.fragment_container, welcomeF).commit();

            }
        });

        extra2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                fm = getActivity().getSupportFragmentManager();
//                WelcomeF welcomeF = new WelcomeF();
//                fm.beginTransaction().replace(R.id.fragment_container, welcomeF).commit();

            }
        });

        return v;
    }
}