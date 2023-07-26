package com.example.capstoneprojectgroup4.wirting_prescriptions.drug_containers;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class DrugsContainersViewHolder extends RecyclerView.ViewHolder{
    TextView drug1;
    ImageButton addDrugsButton;

    public DrugsContainersViewHolder(@NonNull View itemView) {
        super(itemView);

         drug1 = itemView.findViewById(R.id.text_drug1);
         addDrugsButton = itemView.findViewById(R.id.button_add_drugs);
    }
}
