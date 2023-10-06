package com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_DoctorsView;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class ListOfPatientsViewHolder extends RecyclerView.ViewHolder {
    TextView patientName;
    TextView dob;
    Button select;
    public ListOfPatientsViewHolder(@NonNull View itemView) {
        super(itemView);

        patientName =itemView.findViewById(R.id.TextView_PatientName);
        dob = itemView.findViewById(R.id.TextView_Dob);
        select = itemView.findViewById(R.id.Button_select);
    }
}