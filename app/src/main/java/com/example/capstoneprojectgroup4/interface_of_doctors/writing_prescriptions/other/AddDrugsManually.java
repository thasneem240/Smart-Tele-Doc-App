package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.drug_containers.DrugsContainers;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDrugsManually#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDrugsManually extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddDrugsManually() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDrugsManually.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDrugsManually newInstance(String param1, String param2) {
        AddDrugsManually fragment = new AddDrugsManually();
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
        View v = inflater.inflate(R.layout.fragment_add_drugs_manually, container, false);

        EditText medicineNotes = v.findViewById(R.id.edittext_add_drugs_manually);
        Button addDrugsManually = v.findViewById(R.id.Button_AddTheDrug);
        ImageView backButton = v.findViewById(R.id.imageView_BackButton3);


        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        medicineNotes.requestFocus();
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);



        addDrugsManually.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrescriptionDrugObject prescriptionDrugObject = new PrescriptionDrugObject();
                prescriptionDrugObject.setNameOfTheDrug(medicineNotes.getText().toString());

                WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) getContext();
                writingPrescriptionActivity.setWrittenByManually();
                writingPrescriptionActivity.setSelectedDrug(prescriptionDrugObject);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();


            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();
            }
        });

        return v;
    }
}