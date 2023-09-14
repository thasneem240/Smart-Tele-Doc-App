package com.example.capstoneprojectgroup4.front_end;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;


import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.prescriptions.view_prescriptions.ViewPrescriptionsFragment;
import com.example.capstoneprojectgroup4.search_doctors.SearchDocF;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static MainMenu newInstance(String param1, String param2) {
        MainMenu fragment = new MainMenu();
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
        View v = inflater.inflate(R.layout.fragment_main_menu, container, false);
        Button doctorButton = v.findViewById(R.id.doctor_Button);
        Button recordsButton = v.findViewById(R.id.records_Button);
        Button pharmacyButton = v.findViewById(R.id.pharma_Button);
        ImageView pharmaciesImageView = v.findViewById(R.id.pharmacySquare);
        Button pharmaciesButton = v.findViewById(R.id.pharma_Button);
        ImageView chatBot = v.findViewById(R.id.Ai_square);
        Button chatbotButton = v.findViewById(R.id.aiButton);
        doctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                SearchDocF searchDoctors = new SearchDocF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();            }
        });

        pharmacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                PharmaciesF searchDoctors = new PharmaciesF();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();            }
        });

        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatbotActivity = new Intent(getActivity(), ChatbotActivity.class);
                startActivity(chatbotActivity);
            }
        });
        chatbotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatbotActivity = new Intent(getActivity(), ChatbotActivity.class);
                startActivity(chatbotActivity);
            }
        });
        pharmaciesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ViewPrescriptionsFragment viewPrescriptionsFragment = new ViewPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, viewPrescriptionsFragment).commit();
            }
        });
        pharmacyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ViewPrescriptionsFragment viewPrescriptionsFragment = new ViewPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, viewPrescriptionsFragment).commit();
            }
        });

        recordsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MedicalRecords searchDoctors = new MedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();

            }
        });

        return v;
    }
}