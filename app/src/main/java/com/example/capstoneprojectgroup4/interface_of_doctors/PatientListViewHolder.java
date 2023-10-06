package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;


public class PatientListViewHolder extends RecyclerView.ViewHolder {
    private TextView textPatientName;
    Button select;

    public PatientListViewHolder(@NonNull View itemView) {
        super(itemView);
        textPatientName = itemView.findViewById(R.id.textPatientName);
        select = itemView.findViewById(R.id.select_patientlist);

    }

    public void bind(String patientName) {
        textPatientName.setText(patientName);
    }
}