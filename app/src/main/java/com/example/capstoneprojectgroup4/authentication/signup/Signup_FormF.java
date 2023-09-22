//
//package com.example.capstoneprojectgroup4.authentication.signup;
//
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.capstoneprojectgroup4.R;
//import com.example.capstoneprojectgroup4.authentication.AuthenticationHomeF;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link Signup_FormF#newInstance} factory method to
// * create an instance of this fragment.
// */
//
//public class Signup_FormF extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private FirebaseAuth mAuth;
//    FirebaseFirestore db;
//    FirebaseUser currentUser;
//
//    public Signup_FormF() {
//        // Required empty public constructor
//    }
///**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment SignupPageTwoF.
//     */
//
//    // TODO: Rename and change types and number of parameters
//    public static Signup_FormF newInstance(String param1, String param2) {
//        Signup_FormF fragment = new Signup_FormF();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View v = inflater.inflate(R.layout.fragment_signup_form, container, false);
//
//        mAuth = FirebaseAuth.getInstance();
//        db = FirebaseFirestore.getInstance();
//        currentUser = mAuth.getCurrentUser();
//
//        TextView fullName_ = v.findViewById(R.id.fullname_edittext);
//        TextView nic_ = v.findViewById(R.id.nic_edittext);
//        TextView mobileNumber_ = v.findViewById(R.id.mobilenumber_edittext);
//        TextView dateOfBirth_ = v.findViewById(R.id.dob_edittext);
//        TextView gender_ = v.findViewById(R.id.gender_edittext);
//        Button register = v.findViewById(R.id.next_button);
//        Button homeButton = v.findViewById(R.id.home_button);
//        CheckBox checkBox = v.findViewById(R.id.terms);
//
//        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if(compoundButton.isChecked()){
//                    Toast.makeText(getActivity(), "Checked", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(getActivity(), "Unchecked ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String fullName = fullName_.getText().toString();
//                String nic = nic_.getText().toString();
//                String mobileNumber = mobileNumber_.getText().toString();
//                String dateOfBirth = dateOfBirth_.getText().toString();
//                String gender = gender_.getText().toString();
//
////                progressBar.setVisibility(View.VISIBLE);
//
//                if(TextUtils.isEmpty(fullName)){
//                    Toast.makeText(getActivity(), "Please enter the Full name", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(nic)){
//                    Toast.makeText(getActivity(), "Please enter the nic", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(mobileNumber)){
//                    Toast.makeText(getActivity(), "Please enter the mobile number", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(dateOfBirth)){
//                    Toast.makeText(getActivity(), "Please enter the mobile number", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(TextUtils.isEmpty(gender)){
//                    Toast.makeText(getActivity(), "Please enter the mobile number", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                if(checkBox.isChecked()){
//
//                }
//                else{
//                    Toast.makeText(getActivity(), "Please indicate that you accept the Terms and Conditions", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//
//                // Create a new user with a first and last name
//                Map<String, Object> user = new HashMap<>();
//                user.put("Full name", fullName);
//                user.put("NIC", nic);
//                user.put("Mobile number", mobileNumber);
//                user.put("Date of birth", dateOfBirth);
//                user.put("Gender", gender);
//
//                db.collection("users")
//                        .document(currentUser.getUid())
//                        .set(user)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(getActivity(), "Successfully registered.", Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });
//
//        homeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager fm = getActivity().getSupportFragmentManager();
//                AuthenticationHomeF startupFormF = new AuthenticationHomeF();
//                fm.beginTransaction().replace(R.id.fragment_container, startupFormF).commit();
//
//            }
//        });
//
//        return v;
//    }
//}
