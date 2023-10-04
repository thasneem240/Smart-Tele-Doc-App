package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.writing_prescriptions.WritingPrescriptionActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Create_or_View_Prescription#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Create_or_View_Prescription extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Create_or_View_Prescription() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Create_or_View_Prescription.
     */
    // TODO: Rename and change types and number of parameters
    public static Create_or_View_Prescription newInstance(String param1, String param2) {
        Create_or_View_Prescription fragment = new Create_or_View_Prescription();
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
        View v= inflater.inflate(R.layout.fragment_create_or__view__prescription, container, false);


        Button test = v.findViewById(R.id.CreatePresc);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prescriptionActivity = new Intent(getActivity(), WritingPrescriptionActivity.class);
                startActivity(prescriptionActivity);
            }
        });
        ImageView back = v.findViewById(R.id.backButtonCorW);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DoctorMainMenu doctorAvailability = new DoctorMainMenu();
                fm.beginTransaction().replace(R.id.docmenufragmentContainer, doctorAvailability).commit();
            }
        });




        return v;
    }
}