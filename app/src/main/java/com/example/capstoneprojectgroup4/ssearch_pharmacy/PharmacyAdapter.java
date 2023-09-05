package com.example.capstoneprojectgroup4.ssearch_pharmacy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyViewHolder> {
    private ArrayList<Pharmacy> options;

    public PharmacyAdapter(ArrayList<Pharmacy> options) {
        this.options = options;
    }

    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacies, parent, false);
        return new PharmacyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
        Pharmacy pharmacy = options.get(position);

        holder.Name.setText(pharmacy.getName());
        holder.Address.setText(pharmacy.getAddress());
        holder.PhoneNumber.setText(pharmacy.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return options.size(); // Return the number of items in the ArrayList
    }
}
