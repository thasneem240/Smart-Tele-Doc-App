package com.example.capstoneprojectgroup4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// PrescriptionAdapter.java
public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.ViewHolder>
{

    private List<PrescriptionItem> prescriptionItems;

    public PrescriptionAdapter(List<PrescriptionItem> prescriptionItems)
    {
        this.prescriptionItems = prescriptionItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_prescription, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        PrescriptionItem item = prescriptionItems.get(position);
        holder.medicationTextView.setText("Medication: " + item.getMedication());
        holder.dosageTextView.setText("Dosage: " + item.getDosage());
    }

    @Override
    public int getItemCount()
    {
        return prescriptionItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView medicationTextView;
        TextView dosageTextView;

        ViewHolder(View itemView)
        {
            super(itemView);
            medicationTextView = itemView.findViewById(R.id.medicationTextView);
            dosageTextView = itemView.findViewById(R.id.dosageTextView);
        }
    }
}
