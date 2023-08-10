package com.example.capstoneprojectgroup4;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationsAdapter extends RecyclerView.Adapter<LocationsAdapter.LocationViewHolder> {
    private ArrayList<String> locations;
    private String doctorName;
    private String specialization;

    public LocationsAdapter(ArrayList<String> locations, String doctorName, String specialization) {
        this.locations = locations;
        this.doctorName = doctorName;
        this.specialization = specialization;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.location_item, parent, false);
        return new LocationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position) {
        String location = locations.get(position);
        holder.Location.setText(location);
        holder.Name.setText(doctorName);
        holder.Specialization.setText(specialization);

       holder.Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                DocAvailF fragment = DocAvailF.newInstance(doctorName, location);
                fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        TextView Location;
        TextView Name;
        TextView Specialization;

        LocationViewHolder(@NonNull View itemView) {
            super(itemView);
            Location = itemView.findViewById(R.id.textLocation);
            Name = itemView.findViewById(R.id.textName);
            Specialization = itemView.findViewById(R.id.textSpecialization);
        }
    }
}