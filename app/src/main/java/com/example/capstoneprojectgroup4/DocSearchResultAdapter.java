package com.example.capstoneprojectgroup4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DocSearchResultAdapter extends RecyclerView.Adapter<DocSearchViewHolder> {
    private ArrayList<Doctors> doctors;

    public DocSearchResultAdapter(ArrayList<Doctors> doctors) {
        this.doctors = doctors;
    }

    @NonNull
    @Override
    public DocSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.searchresult, parent, false);
        return new DocSearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DocSearchViewHolder holder, int position) {
        Doctors doctor = doctors.get(position);


        LinearLayoutManager layoutManager = new LinearLayoutManager(holder.itemView.getContext());
        holder.recyclerViewLocations.setLayoutManager(layoutManager);
        LocationsAdapter locationsAdapter = new LocationsAdapter(doctor.getLocations(), doctor.getName(), doctor.getSpecialization());
        holder.recyclerViewLocations.setAdapter(locationsAdapter);
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }
}

