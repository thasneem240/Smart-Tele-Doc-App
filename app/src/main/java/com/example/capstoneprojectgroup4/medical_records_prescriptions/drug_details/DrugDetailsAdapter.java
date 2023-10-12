package com.example.capstoneprojectgroup4.medical_records_prescriptions.drug_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;

import java.util.ArrayList;

public class DrugDetailsAdapter extends RecyclerView.Adapter<DrugDetailsViewHolder>{

    ArrayList<PrescriptionDrugObject> prescriptionDrugObjects;

    public DrugDetailsAdapter(ArrayList<PrescriptionDrugObject> prescriptionDrugObjects){
        this.prescriptionDrugObjects  = prescriptionDrugObjects;
    }

    @NonNull
    @Override
    public DrugDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_med_details,parent,false);
        DrugDetailsViewHolder drugDetailsViewHolder = new DrugDetailsViewHolder(view);
        return drugDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugDetailsViewHolder holder, int position) {
        holder.medName.setText(prescriptionDrugObjects.get(position).getNameOfTheDrug());
        holder.brandName.setText(prescriptionDrugObjects.get(position).getBrandName());
        holder.strength.setText(prescriptionDrugObjects.get(position).getStrength());
        holder.amount.setText(prescriptionDrugObjects.get(position).getAmount()+"");
        holder.notes.setText(prescriptionDrugObjects.get(position).getMedicineNotes());
    }

    @Override
    public int getItemCount() {
        return prescriptionDrugObjects.size();
    }
}
