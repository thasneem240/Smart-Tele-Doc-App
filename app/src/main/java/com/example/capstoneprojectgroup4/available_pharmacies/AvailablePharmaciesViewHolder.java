package com.example.capstoneprojectgroup4.available_pharmacies;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class AvailablePharmaciesViewHolder extends RecyclerView.ViewHolder {
    TextView pharmacy_name, totalPrice;
    public AvailablePharmaciesViewHolder(@NonNull View itemView) {
        super(itemView);

        totalPrice = itemView.findViewById(R.id.text_totalPrice);
        pharmacy_name = itemView.findViewById(R.id.medicineName_textView);
    }

}