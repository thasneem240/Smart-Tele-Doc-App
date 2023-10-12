package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.drug_details;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;

import java.util.ArrayList;

public class DrugDetailsAdapter2 extends RecyclerView.Adapter<DrugDetailsViewHolder2>{

    ArrayList<PrescriptionDrugObject> prescriptionDrugObjects;

    public DrugDetailsAdapter2(ArrayList<PrescriptionDrugObject> prescriptionDrugObjects){
        this.prescriptionDrugObjects  = prescriptionDrugObjects;
    }

    @NonNull
    @Override
    public DrugDetailsViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_med_details,parent,false);
        DrugDetailsViewHolder2 drugDetailsViewHolder2 = new DrugDetailsViewHolder2(view);
        return drugDetailsViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugDetailsViewHolder2 holder, int position) {
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
