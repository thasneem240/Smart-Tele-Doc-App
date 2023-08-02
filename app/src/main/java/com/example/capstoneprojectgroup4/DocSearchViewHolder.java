package com.example.capstoneprojectgroup4;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DocSearchViewHolder extends RecyclerView.ViewHolder {
    public TextView Location;
    public TextView Name;
    public TextView Specialization;
    public RecyclerView recyclerViewLocations;

    public DocSearchViewHolder(@NonNull View itemView) {
        super(itemView);
        Location = itemView.findViewById(R.id.textLocation);
        Name = itemView.findViewById(R.id.textName);
        Specialization = itemView.findViewById(R.id.textSpecialization);
        recyclerViewLocations = itemView.findViewById(R.id.recyclerViewLocations);
    }
}

