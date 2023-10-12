package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.ListOfPatients_writingPrescription.AppointmentObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.drug_containers.DrugsContainers;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.select_from_the_list.SelectTheDrug;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchWordByWord#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchWordByWord extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<String> list;

    public SearchWordByWord(ArrayList<String> list) {
        this.list = list;
    }

    public SearchWordByWord() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchWordByWord.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchWordByWord newInstance(String param1, String param2) {
        SearchWordByWord fragment = new SearchWordByWord();
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
        View v = inflater.inflate(R.layout.fragment_search_word_by_word, container, false);

        EditText nameOfTheDrug = v.findViewById(R.id.EditText_name_of_the_drug);
        ImageView backButton = v.findViewById(R.id.imageView_BackButton);

        FragmentManager fm = getActivity().getSupportFragmentManager();

        SelectTheDrug selectTheDrug = new SelectTheDrug(list);
        fm.beginTransaction().replace(R.id.FrameLayout_FragmentContainer, selectTheDrug).commit();

        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        nameOfTheDrug.requestFocus();
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        nameOfTheDrug.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int charSequencePosition, int backward, int forward) {
                SelectTheDrug selectTheDrug = new SelectTheDrug(filterPatientNames(list, charSequence.toString()));
                fm.beginTransaction().replace(R.id.FrameLayout_FragmentContainer, selectTheDrug).commit();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrugData drugData = new DrugData();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugData).commit();
            }
        });

        return v;
    }

    private ArrayList<String> filterPatientNames(ArrayList<String> list, String searchText) {
        ArrayList<String> output = new ArrayList<>();

        for(String s : list){
            if(s.toLowerCase().contains(searchText.toLowerCase())){
                output.add(s);
            }
        }

        return output;
    }

}