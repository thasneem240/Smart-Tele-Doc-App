package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.PatientLogin;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorLoginPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorLoginPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    FirebaseAuth auth;

    public DoctorLoginPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorLoginPage.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorLoginPage newInstance(String param1, String param2) {
        DoctorLoginPage fragment = new DoctorLoginPage();
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
        View v = inflater.inflate(R.layout.fragment_doctor_login_page, container, false);
        Button login = v.findViewById(R.id.login_button3);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prescriptionActivity = new Intent(getActivity(), MainActivity4.class);
                startActivity(prescriptionActivity);
            }
        });

        ImageView back = v.findViewById(R.id.backButtonDocLog);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent prescriptionActivity = new Intent(getActivity(), MainActivity.class);
                startActivity(prescriptionActivity);
            }
        });




      /*  TextView signup = v.findViewById(R.id.sign_up_link);
        EditText email_ = v.findViewById(R.id.EditText_Doctor_Enter_Reg_Layout);
        EditText password_ = v.findViewById(R.id.EditText_Doctor_Enter_Password);*/

        auth = FirebaseAuth.getInstance();
/*        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = email_.getText().toString();
                String password = password_.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getActivity(), "Please enter the email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(), "Please enter the password", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
//                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(getActivity(), "Login successful.", Toast.LENGTH_SHORT).show();

                                    FragmentManager fm = getActivity().getSupportFragmentManager();
                                    DoctorHomePage doctorHomePage = new DoctorHomePage();
                                    fm.beginTransaction().replace(R.id.fragment_container, doctorHomePage).commit();


                                } else {
                                    Toast.makeText(getActivity(), "Login failed. "+task.getException(), Toast.LENGTH_LONG).show();

                                }
                            }
                        });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        return v;
    }
}