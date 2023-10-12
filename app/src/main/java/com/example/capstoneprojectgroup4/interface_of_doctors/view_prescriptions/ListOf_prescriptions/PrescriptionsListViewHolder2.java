package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOf_prescriptions;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class PrescriptionsListViewHolder2 extends RecyclerView.ViewHolder {
    TextView date, doctor;
    Button select;
    public PrescriptionsListViewHolder2(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.TextView_PatientName);
        doctor = itemView.findViewById(R.id.TextView_Dob);
        select = itemView.findViewById(R.id.Button_select);
    }
}
