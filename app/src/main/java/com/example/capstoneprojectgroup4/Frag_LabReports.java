package com.example.capstoneprojectgroup4;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.net.Uri;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import android.widget.Toast;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;

import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Frag_LabReports#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Frag_LabReports extends Fragment
{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // Variable Declarations
    private Button selectFileButton;
    private ImageView firebaseImage;
    private Button uploadButton;
    private Button listReports;
    private Uri imageUri;
    private String fileName = null;

    private StorageReference storageReference;
    private ProgressDialog progressDialog;
    private Drawable drawable = null;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private   final boolean[] isSelected = {false};

    public Frag_LabReports()
    {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Frag_LabReports.
     */
    // TODO: Rename and change types and number of parameters
    public static Frag_LabReports newInstance(String param1, String param2)
    {
        Frag_LabReports fragment = new Frag_LabReports();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lab_reports, container, false);


        selectFileButton = view.findViewById(R.id.selectFileButton);
        firebaseImage = view.findViewById(R.id.firebaseImage);

        uploadButton = view.findViewById(R.id.uploadButton);
        listReports = view.findViewById(R.id.listReport);

        ImageView backButton = view.findViewById(R.id.backButtonLabReports);

        drawable = firebaseImage.getDrawable();

        /* Grab the  UI Variables from Layout file */

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MedicalRecords searchDoctors = new MedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        selectFileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectImage();
            }
        });


        uploadButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isSelected[0])
                {
                    uploadFile();
                }
                else
                {
                    Toast.makeText(getActivity(),"Select the File Before Upload!!!! ", Toast.LENGTH_SHORT).show();
                }


            }
        });

        listReports.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getActivity(),Activity_ListLabReports.class);
                startActivity(intent);
            }
        });

        return  view;
    }

    private void selectImage()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,100);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && data != null && data.getData() != null)
        {
            imageUri = data.getData();
            firebaseImage.setImageURI(imageUri);
            isSelected[0] = true;
        }
    }


    private void uploadFile()
    {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Uploading File.....");
        progressDialog.show();


        String uId = MainActivity.getPatientObject().getUid();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
        Date now = new Date();

        String fileTitle = formatter.format(now);
        String fileTitleWithUID = uId + "_" + fileTitle;

        storageReference = FirebaseStorage.getInstance().getReference("Lab_Reports/" + fileTitleWithUID);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        firebaseImage.setImageDrawable(drawable);
                        isSelected[0] = false;

                        Toast.makeText(getActivity(),"Successfully uploaded", Toast.LENGTH_SHORT).show();

                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        firebaseImage.setImageDrawable(drawable);
                        isSelected[0] = false;

                        if(progressDialog.isShowing())
                        {
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getActivity(),"Failed to upload", Toast.LENGTH_SHORT).show();

                    }
                });

    }

}