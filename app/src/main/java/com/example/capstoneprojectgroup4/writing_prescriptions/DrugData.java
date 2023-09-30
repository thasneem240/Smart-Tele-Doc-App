package com.example.capstoneprojectgroup4.writing_prescriptions;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers.DrugsContainers;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DrugData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DrugData extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<DatabaseDrugObject> listOfDrugData;
    PrescriptionDrugObject selectedDrug;
    int position;
    public DrugData(ArrayList<DatabaseDrugObject> listOfDrugData){
        this.listOfDrugData = listOfDrugData;
    }
    public DrugData(PrescriptionDrugObject selectedDrug, int position){
        this.selectedDrug = selectedDrug;
    }

    public DrugData() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DrugData.
     */
    // TODO: Rename and change types and number of parameters
    public static DrugData newInstance(String param1, String param2) {
        DrugData fragment = new DrugData();
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
        View v = inflater.inflate(R.layout.fragment_drug_data, container, false);

        Button nameOfTheDrug = v.findViewById(R.id.Button_NameOfTheDrug);
        Button brandName = v.findViewById(R.id.Button_BrandName);
        Button add = v.findViewById(R.id.Button_Add);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        if(selectedDrug == null){

        }
        else{
            nameOfTheDrug.setText(selectedDrug.getNameOfTheDrug());
        }

        nameOfTheDrug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> listOfDrugNames = new ArrayList<>();

                for (DatabaseDrugObject databaseDrugObject : listOfDrugData) {
                    listOfDrugNames.add(databaseDrugObject.getNameOfTheDrug());
                }

                SearchWordByWord searchWordByWord = new SearchWordByWord(listOfDrugNames);
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, searchWordByWord).commit();
            }
        });

        brandName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> listOfBrandNames = listOfDrugData.get(position).getBrandNames();

                SearchWordByWord searchWordByWord = new SearchWordByWord(listOfBrandNames);
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, searchWordByWord).commit();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) view.getContext();
                writingPrescriptionActivity.setSelectedDrug(selectedDrug);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
//                fm.beginTransaction().remove(fm.findFragmentById(R.id.FragmentContainerView_SelectTheDrug)).commit();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();
            }
        });

        return v;
    }
}