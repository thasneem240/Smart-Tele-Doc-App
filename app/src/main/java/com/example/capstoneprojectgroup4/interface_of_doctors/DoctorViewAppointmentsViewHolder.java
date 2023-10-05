package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class DoctorViewAppointmentsViewHolder extends RecyclerView.ViewHolder {
    public TextView doctorNameTextView;
    public TextView locationTextView;
    public TextView dayTextView;
    public TextView appointmentNumberTextView;
    public TextView typeTextView;

    public DoctorViewAppointmentsViewHolder(@NonNull View itemView) {
        super(itemView);
        doctorNameTextView = itemView.findViewById(R.id.DoctorName);
        locationTextView = itemView.findViewById(R.id.Location);
        dayTextView = itemView.findViewById(R.id.Day);
        appointmentNumberTextView = itemView.findViewById(R.id.AppNumber);
        typeTextView = itemView.findViewById(R.id.Type);
    }
}
