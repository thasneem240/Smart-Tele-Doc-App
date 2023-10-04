package com.example.capstoneprojectgroup4.best_price.listOf_prescriptions;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class ListOfPrescriptionsViewHolder extends RecyclerView.ViewHolder {
    TextView date, doctor;
    Button select;
    public ListOfPrescriptionsViewHolder(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.TextView_PatientName);
        doctor = itemView.findViewById(R.id.TextView_Dob);
        select = itemView.findViewById(R.id.Button_select);
    }
}
