package com.example.capstoneprojectgroup4.wirting_prescriptions.select_the_drug;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class SelectTheDrugViewHolder extends RecyclerView.ViewHolder {
    TextView drugName;
    public SelectTheDrugViewHolder(@NonNull View itemView) {
        super(itemView);

        drugName = itemView.findViewById(R.id.text_drug_name);
    }
}
