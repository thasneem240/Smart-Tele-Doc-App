package com.example.capstoneprojectgroup4.medical_records_prescriptions.prescriptions_list;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class PrescriptionsListViewHolder extends RecyclerView.ViewHolder {
    TextView date, doctor;
    Button select;
    public PrescriptionsListViewHolder(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.TextView_PatientName);
        doctor = itemView.findViewById(R.id.TextView_Dob);
        select = itemView.findViewById(R.id.Button_select);
    }
}
