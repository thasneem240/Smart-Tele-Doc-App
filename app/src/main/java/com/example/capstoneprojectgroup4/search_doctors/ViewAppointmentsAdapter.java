package com.example.capstoneprojectgroup4.search_doctors;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.UUID;

public class ViewAppointmentsAdapter extends RecyclerView.Adapter<ViewAppointmentsViewHolder> {

    private List<AppointmentItem> appointmentItemList;

    public ViewAppointmentsAdapter(List<AppointmentItem> appointmentItemList) {
        this.appointmentItemList = appointmentItemList;

    }

    public void removeAppointment(int position) {
        if (position >= 0 && position < appointmentItemList.size()) {
            appointmentItemList.remove(position);
            notifyItemRemoved(position);
        }
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
        holder.appNoTextView.setText("Appointment Number: " +appointmentItem.getAppointmentNumber());
        holder.locTextView.setText(appointmentItem.getLocation());


        holder.CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String patientKey = MainActivity.getPatientObject().getUid();
                    String appointmentKey = AppointmentKeyGenerator.getAppointmentKey();
                    String doctorName = AppointmentKeyGenerator.getDoctorName();
                    Log.d(TAG, "Cancel Appointment - appointmentKey: " + appointmentKey);
                    int itemPosition = holder.getBindingAdapterPosition(); // Use getBindingAdapterPosition()
                    cancelAppointment(appointmentKey, patientKey, itemPosition);
                    cancelDoctorAppointment(appointmentKey,doctorName);
                }
            });
    }

    public void cancelAppointment(String appointmentKey, String patientKey,int position) {
        Log.d(TAG, "Cancel Appointment - patientKey: " + patientKey);
        if (appointmentKey == null || patientKey == null) {
            Log.e(TAG, "Cancel Appointment - appointmentKey or patientKey is null");
            return;
        }
        // Construct the reference path to the appointment
        DatabaseReference appointmentRef = FirebaseDatabase.getInstance()
                .getReference("Appointment Data")
                .child(patientKey)
                .child(appointmentKey);

        // Delete the appointment data
        appointmentRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CancelAppointment", "Successfully Deleted Patient Appointment");
                        removeAppointment(position); // Remove from the adapter's data list
                        //notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during deletion.
                        // You can show a toast or log the error.
                        Log.e("CancelAppointment", "Error deleting appointment", e);
                    }
                });
    }

    public void cancelDoctorAppointment(String appointmentKey, String DoctorName) {

        if (appointmentKey == null || DoctorName == null) {
            Log.e(TAG, "Cancel Appointment - appointmentKey or DoctorName is null");
            return;
        }
        // Construct the reference path to the appointment
        DatabaseReference appointmentRef = FirebaseDatabase.getInstance()
                .getReference("Doctor Appointments")
                .child(DoctorName)
                .child(appointmentKey);

        // Delete the appointment data
        appointmentRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CancelAppointment", "Successfully Deleted Doctor Appointment");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CancelAppointment", "Error deleting appointment for Doctor", e);
                    }
                });
    }

    @Override
    public int getItemCount() {
        return appointmentItemList.size();
    }
}