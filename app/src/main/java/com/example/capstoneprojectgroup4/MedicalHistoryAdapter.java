package com.example.capstoneprojectgroup4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// MedicalHistoryAdapter.java
public class MedicalHistoryAdapter extends RecyclerView.Adapter<MedicalHistoryAdapter.ViewHolder>
{

    private List<MedicalHistoryItem> medicalHistoryItems;

    public MedicalHistoryAdapter(List<MedicalHistoryItem> medicalHistoryItems)
    {
        this.medicalHistoryItems = medicalHistoryItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_medical_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        MedicalHistoryItem item = medicalHistoryItems.get(position);
        holder.dateTextView.setText("Date: " + item.getDate());
        holder.descriptionTextView.setText("Description: " + item.getDescription());
    }

    @Override
    public int getItemCount()
    {
        return medicalHistoryItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView dateTextView;
        TextView descriptionTextView;

        ViewHolder(View itemView)
        {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
        }
    }
}
