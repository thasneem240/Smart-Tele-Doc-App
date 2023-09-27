package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.android.gms.tasks.OnFailureListener;


public class Activity_ListLabReports extends AppCompatActivity
{
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // Initialize a list to store the firebaseLabReports
        List<LabReport> fireBaseLabReports  = new ArrayList<>();
        final boolean[] isRetrieved = {false};

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_lab_reports);

        Button retrieveLabReports = findViewById(R.id.retrieveLabReports);
        Button listLabReports = findViewById(R.id.listLabReports);
        RecyclerView recyclerView = findViewById(R.id.lab_report_recycler_view);


        retrieveLabReports.setVisibility(View.VISIBLE);
        listLabReports.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);

        retrieveLabReports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                retrieveLabReports(fireBaseLabReports);

                Toast.makeText(Activity_ListLabReports.this,"Successfully retrieved " +
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
                    Toast.makeText(Activity_ListLabReports.this,"First Retrieve the Lab Reports!!! ", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void downloadImageFromURI(LabReport labReport, ImageView imageViewTest)
    {
        Glide.with(this).load(labReport.getImageUri()).into(imageViewTest);
    }





    private void enableReportsRecyclerView(List<LabReport> fireBaseLabReports, RecyclerView recyclerView)
    {
        LabReportAdapter adapter = new LabReportAdapter(fireBaseLabReports); // labReports is a List of LabReport objects
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void retrieveLabReports(List<LabReport> labReportList)
    {
    // Reference to the Firebase Storage folder where lab reports are stored
        storageReference = FirebaseStorage.getInstance().getReference("Lab_Reports");

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

//                           boolean containsUid = id.contains(uID);
//                            if(containsUid)
//                            {
//                                item.getDownloadUrl(),...........
//                            }

                            // Get the download URL for the image
                            item.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>()
                                    {
                                        @Override
                                        public void onSuccess(Uri uri)
                                        {
                                            // Create an LabReport Object and add it to the list
                                            LabReport labReport = new LabReport(id, uri);
                                            labReportList.add(labReport);

                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener()
                                    {
                                        @Override
                                        public void onFailure(@NonNull Exception exception)
                                        {
                                            // Handle any errors that may occur during URL retrieval

                                            Toast.makeText(Activity_ListLabReports.this,
                                                    "Failed to Retrieve the URI", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        // Handle any errors that may occur during listing

                        Toast.makeText(Activity_ListLabReports.this,
                                "Failed to Listing", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    private class LabReportAdapter extends RecyclerView.Adapter<LabReportAdapter.ViewHolder>
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
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.viewholder_labreport, parent, false);
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
           // holder.labReportImage.setImageURI(imageUri);
        }

        @Override
        public int getItemCount()
        {
            return labReports.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
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




}