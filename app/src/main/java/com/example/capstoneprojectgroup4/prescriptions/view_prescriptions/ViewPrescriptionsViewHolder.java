package com.example.capstoneprojectgroup4.prescriptions.view_prescriptions;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class ViewPrescriptionsViewHolder extends RecyclerView.ViewHolder {
    TextView date, doctor;
    public ViewPrescriptionsViewHolder(@NonNull View itemView) {
        super(itemView);

        date = itemView.findViewById(R.id.text_date);
        doctor = itemView.findViewById(R.id.text_doctor);
    }
}
