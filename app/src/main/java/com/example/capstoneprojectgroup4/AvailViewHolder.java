package com.example.capstoneprojectgroup4;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailViewHolder extends RecyclerView.ViewHolder {

    private TextView textDay;
    private TextView textTime;
    private TextView textNoAppointments;

    public AvailViewHolder(@NonNull View itemView) {
        super(itemView);

        textDay = itemView.findViewById(R.id.textDay);
        textTime = itemView.findViewById(R.id.textTime);
        textNoAppointments = itemView.findViewById(R.id.textNoAppointments);
    }

    public void bind(Availability session) {

        textDay.setText(session.getDay());
        textTime.setText(session.getStartTime() + " " + session.getEndTime());
        textNoAppointments.setText("No. of Appointments "  + session.getNoApp());


    }
}