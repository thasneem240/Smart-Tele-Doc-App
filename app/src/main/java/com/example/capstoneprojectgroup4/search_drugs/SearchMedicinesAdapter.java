package com.example.capstoneprojectgroup4.search_drugs;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class SearchMedicinesAdapter extends RecyclerView.Adapter<SearchMedicinesViewHolder> {
    FirebaseDatabase database;
    ArrayList<String> availablePharmacies;
    int pharmacyCount;

    public SearchMedicinesAdapter(ArrayList<String> availablePharmacies){
        this. availablePharmacies = availablePharmacies;
        pharmacyCount = availablePharmacies.size();
    }

    @NonNull
    @Override
    public SearchMedicinesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_available_pharmacies,parent,false);
        SearchMedicinesViewHolder searchMedicinesViewHolder = new SearchMedicinesViewHolder(view);
        return searchMedicinesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMedicinesViewHolder holder, int position) {
        holder.pharmacy_name.setText(String.valueOf(availablePharmacies.get(position)));
    }

    @Override
    public int getItemCount() {
        return pharmacyCount;
    }
}
