package com.example.capstoneprojectgroup4.interface_of_doctors.other;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOfPatients_patientProfile.ListOfPatientsFragment2;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.ListOfPatients_writingPrescription.ListOfPatientsFragment;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorMainMenu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorMainMenu extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DoctorMainMenu() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorMainMenu.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorMainMenu newInstance(String param1, String param2) {
        DoctorMainMenu fragment = new DoctorMainMenu();
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
        View v = inflater.inflate(R.layout.fragment_doctor_main_menu, container, false);

        ImageView writePrescription = v.findViewById(R.id.ImageView_writePrescription);
        Button writePrescriptionButton = v.findViewById(R.id.Button_writePrescription);
        Button listOfPatients = v.findViewById(R.id.ListofPatientsButton);
        ImageView listOfPatientsImage = v.findViewById(R.id.imageView_listOfPatients);
        Button AIHelpButton = v.findViewById(R.id.AIHelpButton);
        ImageView AIHelpSquare = v.findViewById(R.id.AIHelpSquare);

        TextView greetings = v.findViewById(R.id.good_morning2);

        greetings.setText("Hi, " + DoctorsActivity.getDoctorObject().getName());

        listOfPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                DoctorPatientProfiles listOfPatientsFragment = new DoctorPatientProfiles();
//                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment).commit();

                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPatientsFragment2 listOfPatientsFragment2 = new ListOfPatientsFragment2();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment2).commit();
            }
        });

        listOfPatientsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPatientsFragment2 listOfPatientsFragment2 = new ListOfPatientsFragment2();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment2).commit();
            }
        });


        writePrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPatientsFragment listOfPatientsFragment = new ListOfPatientsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment).commit();
            }
        });
        writePrescriptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPatientsFragment listOfPatientsFragment = new ListOfPatientsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, listOfPatientsFragment).commit();
            }
        });
        AIHelpSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatbotActivity = new Intent(getActivity(), ChatbotActivity.class);
                chatbotActivity.putExtra("DOCINT","true");
                startActivity(chatbotActivity);
            }
        });
        AIHelpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatbotActivity = new Intent(getActivity(), ChatbotActivity.class);
                chatbotActivity.putExtra("DOCINT","true");
                startActivity(chatbotActivity);
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {

            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this.getActivity(), callback);


        return v;

    }
}