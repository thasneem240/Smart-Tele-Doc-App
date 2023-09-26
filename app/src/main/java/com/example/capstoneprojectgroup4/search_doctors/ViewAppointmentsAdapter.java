package com.example.capstoneprojectgroup4.search_doctors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.List;

public class ViewAppointmentsAdapter extends RecyclerView.Adapter<ViewAppointmentsViewHolder> {

    private List<AppointmentItem> appointmentItemList;

    public ViewAppointmentsAdapter(List<AppointmentItem> appointmentItemList) {
        this.appointmentItemList = appointmentItemList;
    }

    @NonNull
    @Override
    public ViewAppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments, parent, false);
        return new ViewAppointmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAppointmentsViewHolder holder, int position) {
        AppointmentItem appointmentItem = appointmentItemList.get(position);

        // Set the data to the views in the ViewHolder
        holder.doctorNameTextView.setText(appointmentItem.getDoctorName());
        holder.dayTextView.setText(appointmentItem.getDate());
        holder.typeTextView.setText(appointmentItem.getAppointmentType());

        // You can also set click listeners or perform any other actions here.
    }

    @Override
    public int getItemCount() {
        return appointmentItemList.size();
    }
}
