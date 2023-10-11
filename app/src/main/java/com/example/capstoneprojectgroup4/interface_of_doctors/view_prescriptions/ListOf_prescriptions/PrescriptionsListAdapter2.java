package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOf_prescriptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.DetailedPrescription2;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;
import com.example.capstoneprojectgroup4.medical_records_prescriptions.DetailedPrescription;

import java.util.ArrayList;

public class PrescriptionsListAdapter2 extends RecyclerView.Adapter<PrescriptionsListViewHolder2> {
    ArrayList<PrescriptionObject> prescriptionObjectList;

    public PrescriptionsListAdapter2(ArrayList<PrescriptionObject> prescriptionObjectList){
        this.prescriptionObjectList = prescriptionObjectList;
    }
    @NonNull
    @Override
    public PrescriptionsListViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_prescriptions_records,parent,false);
        PrescriptionsListViewHolder2 prescriptionsListViewHolder2 = new PrescriptionsListViewHolder2(view);
        return prescriptionsListViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionsListViewHolder2 holder, int position) {
        holder.date.setText(prescriptionObjectList.get(position).getWrittenOn());
        holder.doctor.setText(prescriptionObjectList.get(position).getDoctorName());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrescriptionObject prescriptionObject = prescriptionObjectList.get(position);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                DetailedPrescription2 detailedPrescription2 = new DetailedPrescription2(prescriptionObject);
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, detailedPrescription2).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return prescriptionObjectList.size();
    }
}
