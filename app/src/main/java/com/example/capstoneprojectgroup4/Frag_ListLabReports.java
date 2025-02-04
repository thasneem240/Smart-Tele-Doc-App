package com.example.capstoneprojectgroup4;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_ListLabReports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_ListLabReports extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private StorageReference storageReference;
    private String uId;
    private String userType = "Patient";

    public Frag_ListLabReports()
    {
        // Required empty public constructor
    }

    public Frag_ListLabReports(String userType)
    {
        this.userType = userType;
    }



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_ListLabReports.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_ListLabReports newInstance(String param1, String param2) {
        Frag_ListLabReports fragment = new Frag_ListLabReports();
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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_lab_reports, container, false);


        if(userType.equalsIgnoreCase("Patient"))
        {
            uId = MainActivity.getPatientObject().getUid();
        }
        else // For Doctors
        {
            uId = DoctorsActivity.getAppointmentObject().getPatientUserId();
        }

        // Initialize a list to store the firebaseLabReports
        List<LabReport> fireBaseLabReports  = new ArrayList<>();
        final boolean[] isRetrieved = {false};


        Button retrieveLabReports = view.findViewById(R.id.retrieveLabReports);
        Button listLabReports = view.findViewById(R.id.listLabReports);
        RecyclerView recyclerView = view.findViewById(R.id.lab_report_recycler_view);

        ImageView backButton = view.findViewById(R.id.backButtonListLabReports);

        retrieveLabReports.setVisibility(View.VISIBLE);
        listLabReports.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);


        backButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(userType.equalsIgnoreCase("Patient"))
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Frag_LabReports fragLabReports = new Frag_LabReports();

                    fm.beginTransaction().replace(R.id.fragmentContainerView, fragLabReports).commit();
                }
                else
                {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();

                    fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();
                }




            }
        });




        retrieveLabReports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                retrieveLabReports(fireBaseLabReports);

                Toast.makeText(getActivity(),"Successfully retrieved " +
                        "all the lab reports from firebase", Toast.LENGTH_SHORT).show();

                isRetrieved[0] = true;

            }
        });

        listLabReports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(isRetrieved[0])
                {
                    retrieveLabReports.setVisibility(View.GONE);
                    listLabReports.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);

                    enableReportsRecyclerView(fireBaseLabReports,recyclerView);
                }
                else
                {
                    Toast.makeText(getActivity(),"First Retrieve the Lab Reports!!! ", Toast.LENGTH_SHORT).show();
                }


            }
        });



        return view;
    }

    private void downloadImageFromURI(LabReport labReport, ImageView imageViewTest)
    {
        Glide.with(getActivity()).load(labReport.getImageUri()).into(imageViewTest);
    }


    public void enableReportsRecyclerView(List<LabReport> fireBaseLabReports, RecyclerView recyclerView)
    {
        //Create Adapter for the recyclerview
        LabReportAdapter adapter = new LabReportAdapter(fireBaseLabReports); // labReports is a List of LabReport objects

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Hook it up

        recyclerView.setAdapter(adapter);

    }


    public void retrieveLabReports(List<LabReport> labReportList)
    {
        // Reference to the Firebase Storage folder where lab reports are stored
        storageReference = FirebaseStorage.getInstance().getReference("Lab_Reports");
        final int[] reportNo = {1};

        storageReference.listAll()
                .addOnSuccessListener(new OnSuccessListener<ListResult>()
                {
                    @Override
                    public void onSuccess(ListResult listResult)
                    {
                        for (StorageReference item : listResult.getItems())
                        {
                            // Generate an ID for each image
                            String id = item.getName();

                           boolean containsUid = id.contains(uId);

                           // Retrieve Reports of the specific user
                            if(containsUid)
                            {
                                // Get the download URL for the image
                                item.getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>()
                                        {
                                            @Override
                                            public void onSuccess(Uri uri)
                                            {
                                                // Create an LabReport Object and add it to the list

//                                                LabReport labReport = new LabReport(id, uri);
//                                                labReportList.add(labReport);

                                                String title = "Report " + reportNo[0];
                                                LabReport labReport = new LabReport(title, uri);
                                                labReportList.add(labReport);

                                                reportNo[0]++;

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener()
                                        {
                                            @Override
                                            public void onFailure(@NonNull Exception exception)
                                            {
                                                // Handle any errors that may occur during URL retrieval

                                                Toast.makeText(getActivity(),
                                                        "Failed to Retrieve the URI", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        // Handle any errors that may occur during listing

                        Toast.makeText(getActivity(),
                                "Failed to Listing", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private class LabReportAdapter extends RecyclerView.Adapter<ViewHolder>
    {
        private List<LabReport> labReports;

        public LabReportAdapter(List<LabReport> labReports)
        {
            this.labReports = labReports;
        }



        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_labreport, parent, false);
            return new ViewHolder(view);
        }



        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position)
        {
            LabReport labReport = labReports.get(position);

            String title = labReport.getTitle();
            Uri imageUri = labReport.getImageUri();


            holder.labReportTitle.setText(title);

            downloadImageFromURI(labReport,holder.labReportImage);
        }



        @Override
        public int getItemCount()
        {
            return labReports.size();
        }


    }

    private class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView labReportImage;
        public TextView labReportTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            labReportImage = itemView.findViewById(R.id.lab_report_image);
            labReportTitle = itemView.findViewById(R.id.lab_report_title);
        }
    }



}