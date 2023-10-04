package com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_DoctorsView;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;
import com.example.capstoneprojectgroup4.writing_prescriptions.WritingPrescriptionActivity;

import java.util.ArrayList;

public class ListOfPatientsAdapter extends RecyclerView.Adapter<ListOfPatientsViewHolder> {
    ArrayList<PatientObject> patientsArrayList;
    ListOfPatientsAdapter(ArrayList<PatientObject> patientsArrayList){
        this.patientsArrayList = patientsArrayList;
    }
    @NonNull
    @Override
    public ListOfPatientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_patients_for_each_doctor,parent,false);
        ListOfPatientsViewHolder listOfPatientsViewHolder = new ListOfPatientsViewHolder(view);
        return listOfPatientsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfPatientsViewHolder holder, int position) {
        holder.patientName.setText(patientsArrayList.get(position).getFirstName());
        holder.dob.setText(patientsArrayList.get(position).getDob());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, WritingPrescriptionActivity.class);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return patientsArrayList.size();
    }
}
