package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.drug_containers.DrugsContainers;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.select_from_the_list.SelectTheDrug;

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
    PrescriptionDrugObject selectedDrug;
    int position;

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
        Button add = v.findViewById(R.id.Button_Add);
        Spinner brands = v.findViewById(R.id.Spinner_AvailableBrands);
        Spinner strengths = v.findViewById(R.id.Spinner_AvailbleStrenghts);
        EditText medicineNotes = v.findViewById(R.id.editText_MedicineNotes);
        EditText howMuchMedicines = v.findViewById(R.id.edittext_how_much);

        ImageView back = v.findViewById(R.id.ImageView_BackButton2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();
            }
        });

        FragmentManager fm = getActivity().getSupportFragmentManager();

        ArrayList<DatabaseDrugObject> listOfDrugData;
        WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) getContext();
        listOfDrugData = writingPrescriptionActivity.getListOfDrugData();

        if(selectedDrug != null){
            nameOfTheDrug.setText(selectedDrug.getNameOfTheDrug());

            ArrayList<String> listOfBrandNames = listOfDrugData.get(position).getBrandNames();
            ArrayAdapter<String> arrayAdapterBrands = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listOfBrandNames);
            arrayAdapterBrands.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brands.setAdapter(arrayAdapterBrands);

            ArrayList<String> listOfStrengths = listOfDrugData.get(position).getAvailableStrengths();
            ArrayAdapter<String> arrayAdapterStrengths = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listOfStrengths);
            arrayAdapterStrengths.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            strengths.setAdapter(arrayAdapterStrengths);
        }
        else{
            ArrayList<String> emptyArrayList = new ArrayList<>();
            emptyArrayList.add("Please select a drug first");
            ArrayAdapter<String> emptyArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, emptyArrayList);
            emptyArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            brands.setAdapter(emptyArrayAdapter);
            strengths.setAdapter(emptyArrayAdapter);
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

        brands.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectedDrug != null){
                    selectedDrug.setBrandName(adapterView.getItemAtPosition(i).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        strengths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(selectedDrug != null){
                    selectedDrug.setStrength(adapterView.getItemAtPosition(i).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                selectedDrug.setAmount(Integer.parseInt(howMuchMedicines.getText().toString()));
                if(selectedDrug == null){
                    Toast.makeText(writingPrescriptionActivity, "Please select a drug, the name and the strength", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedDrug.getNameOfTheDrug().equals("")){
                    Toast.makeText(writingPrescriptionActivity, "Please select a drug, the name and the strength", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedDrug.getBrandName().equals("")){
                    Toast.makeText(writingPrescriptionActivity, "Please select the name of the brand.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(selectedDrug.getStrength().equals("")){
                    Toast.makeText(writingPrescriptionActivity, "Please select the strength", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if(selectedDrug.getAmount() == 0){
//                    Toast.makeText(writingPrescriptionActivity, "Please enter How much from this medicine.", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                selectedDrug.setMedicineNotes(medicineNotes.toString());

                WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) view.getContext();
                writingPrescriptionActivity.setSelectedDrug(selectedDrug);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();
            }
        });

        return v;
    }
}