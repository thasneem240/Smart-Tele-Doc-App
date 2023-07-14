package com.example.capstoneprojectgroup4.search_drugs;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class SearchMedicinesViewHolder extends RecyclerView.ViewHolder {
    TextView pharmacy_name;
    public SearchMedicinesViewHolder(@NonNull View itemView) {
        super(itemView);

        pharmacy_name = itemView.findViewById(R.id.pharmacyName);
    }

}