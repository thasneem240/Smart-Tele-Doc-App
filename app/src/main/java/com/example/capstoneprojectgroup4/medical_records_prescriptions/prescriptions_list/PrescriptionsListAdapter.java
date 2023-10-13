package com.example.capstoneprojectgroup4.medical_records_prescriptions.prescriptions_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.best_price.edit_howMuch.EditHowMuchFragment;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;
import com.example.capstoneprojectgroup4.medical_records_prescriptions.DetailedPrescription;

import java.util.ArrayList;

public class PrescriptionsListAdapter extends RecyclerView.Adapter<PrescriptionsListViewHolder> {
    ArrayList<PrescriptionObject> prescriptionObjectList;

    public PrescriptionsListAdapter(ArrayList<PrescriptionObject> prescriptionObjectList){
        this.prescriptionObjectList = prescriptionObjectList;
    }
    @NonNull
    @Override
    public PrescriptionsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_prescriptions_records,parent,false);
        PrescriptionsListViewHolder prescriptionsListViewHolder = new PrescriptionsListViewHolder(view);
        return prescriptionsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionsListViewHolder holder, int position) {
        holder.date.setText(prescriptionObjectList.get(position).getWrittenOn());
        holder.doctor.setText(prescriptionObjectList.get(position).getDoctorName());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PrescriptionObject prescriptionObject = prescriptionObjectList.get(position);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                DetailedPrescription detailedPrescription = new DetailedPrescription(prescriptionObject);
                fm.beginTransaction().replace(R.id.fragmentContainerView, detailedPrescription).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return prescriptionObjectList.size();
    }
}
