package com.example.capstoneprojectgroup4.front_end;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.capstoneprojectgroup4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserDetail extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static UserDetail newInstance(String param1, String param2) {
        UserDetail fragment = new UserDetail();
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

        View v = inflater.inflate(R.layout.fragment_user_detail, container, false);
        Button continueB = v.findViewById(R.id.continueButton);

        Spinner dateSpinner = v.findViewById(R.id.Datespinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.date_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);

        dateSpinner.setSelection(adapter.getPosition("Date"));




        Spinner monthSpinner = v.findViewById(R.id.Monthspinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.month_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);

        dateSpinner.setSelection(adapter.getPosition("Month"));



        Spinner yearSpinner = v.findViewById(R.id.Yearspinner);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.year_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(adapter);

        dateSpinner.setSelection(adapter.getPosition("Year"));

        Spinner heightSpinner = v.findViewById(R.id.Heightspinner);
        ArrayAdapter<CharSequence> heightAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.height_items, android.R.layout.simple_spinner_item);
        heightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        heightSpinner.setAdapter(heightAdapter);

        heightSpinner.setSelection(heightAdapter.getPosition("Height | cm"));
        Spinner weightSpinner = v.findViewById(R.id.Weightspinner);
        ArrayAdapter<CharSequence> weightAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.weight_items, android.R.layout.simple_spinner_item);
        weightAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        weightSpinner.setAdapter(weightAdapter);

        weightSpinner.setSelection(weightAdapter.getPosition("Weight | kg"));

        continueB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MainMenu mainMenu = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, mainMenu).commit();
            }
        });




        return v;
    }
}