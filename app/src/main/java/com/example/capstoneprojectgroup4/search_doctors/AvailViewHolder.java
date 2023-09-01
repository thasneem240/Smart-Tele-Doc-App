package com.example.capstoneprojectgroup4.search_doctors;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.search_doctors.Availability;

public class AvailViewHolder extends RecyclerView.ViewHolder {

    public TextView textDay;

    public TextView textDate;

    private TextView textTime;
    private TextView textNoAppointments;

    public AvailViewHolder(@NonNull View itemView) {
        super(itemView);

        textDay = itemView.findViewById(R.id.textDay);
        textTime = itemView.findViewById(R.id.textTime);
        textNoAppointments = itemView.findViewById(R.id.textNoAppointments);
        textDate = itemView.findViewById(R.id.textDate);

    }

    public void bind(Availability session) {

        textDay.setText(session.getDay());
        textDate.setText(session.getDate());
        textTime.setText(session.getStartTime() + " " + session.getEndTime());
        textNoAppointments.setText("No. of Appointments "  + session.getNoApp());


    }
}