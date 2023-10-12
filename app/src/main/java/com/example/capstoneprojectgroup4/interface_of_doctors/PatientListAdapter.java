package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.best_price.edit_howMuch.EditHowMuchFragment;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;

import java.util.ArrayList;
import java.util.List;


public class PatientListAdapter extends RecyclerView.Adapter<PatientListViewHolder>
{
        private List<String> patientNames;

        public PatientListAdapter(List<String> patientNames)
        {
                this.patientNames = patientNames;
        }

        @NonNull
        @Override
        public PatientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_list, parent, false);
                return new PatientListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PatientListViewHolder holder, int position)
        {
                String patientName = patientNames.get(position);
                holder.bind(patientName);
                holder.select.setOnClickListener(new View.OnClickListener()
                {
                        @Override
                        public void onClick(View view) {
                                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                                FragmentManager fm = activity.getSupportFragmentManager();
                                DoctorMedicalRecords doctorAvailability = new DoctorMedicalRecords();
                                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorAvailability).commit();
                        }
                });

        }

        @Override
        public int getItemCount()
        {
                return patientNames.size();
        }


        public void setPatientNames(List<String> patientNames)
        {
                this.patientNames = patientNames;
                notifyDataSetChanged();
        }
}