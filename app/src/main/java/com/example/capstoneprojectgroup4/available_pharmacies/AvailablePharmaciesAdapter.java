package com.example.capstoneprojectgroup4.available_pharmacies;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AvailablePharmaciesAdapter extends RecyclerView.Adapter<AvailablePharmaciesViewHolder> {
    FirebaseDatabase database;
    ArrayList<String> availablePharmacies;
    int pharmacyCount;

    public AvailablePharmaciesAdapter(ArrayList<String> availablePharmacies){
        this. availablePharmacies = availablePharmacies;
        pharmacyCount = availablePharmacies.size();
    }

    @NonNull
    @Override
    public AvailablePharmaciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_available_pharmacies,parent,false);
        AvailablePharmaciesViewHolder availablePharmaciesViewHolder = new AvailablePharmaciesViewHolder(view);
        return availablePharmaciesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailablePharmaciesViewHolder holder, int position) {
        holder.pharmacy_name.setText(String.valueOf(availablePharmacies.get(position)));
    }

    @Override
    public int getItemCount() {
        return pharmacyCount;
    }
}
