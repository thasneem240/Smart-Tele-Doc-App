package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.List;


public class DoctorViewAppointmentsAdapter extends RecyclerView.Adapter<DoctorViewAppointmentsViewHolder>{
    private List<DoctorAppointmentItemList> appointmentItemList;
    public DoctorViewAppointmentsAdapter(List<DoctorAppointmentItemList> appointmentItemList) {
        this.appointmentItemList = appointmentItemList;

    }


    @NonNull
    @Override
    public DoctorViewAppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_appointments, parent, false);
        return new DoctorViewAppointmentsViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewAppointmentsViewHolder holder, int position) {
        DoctorAppointmentItemList appointmentItem = appointmentItemList.get(position);




        // Set the data to the views in the ViewHolder
        holder.doctorNameTextView.setText(appointmentItem.getPatientName());
        holder.dayTextView.setText(appointmentItem.getDate());
        holder.typeTextView.setText(appointmentItem.getAppointmentType());
        holder.appointmentNumberTextView.setText("Appointment Number: " +appointmentItem.getAppointmentNumber());
        holder.locationTextView.setText(appointmentItem.getLocation());
        holder.time.setText("Time: " +appointmentItem.getStartTime()+ "-"+ appointmentItem.getEndTime());

    }

    @Override
    public int getItemCount() {
        return appointmentItemList.size();
    }
}
