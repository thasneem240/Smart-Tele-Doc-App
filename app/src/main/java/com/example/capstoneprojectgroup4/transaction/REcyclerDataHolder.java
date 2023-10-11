package com.example.capstoneprojectgroup4.transaction;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class REcyclerDataHolder extends RecyclerView.ViewHolder {
    public TextView nameTextBox;
    public TextView dateTextBox;
    public TextView priceTextBox;


    public REcyclerDataHolder(@NonNull View itemView) {
        super(itemView);
        nameTextBox = itemView.findViewById(R.id.itemname);
        dateTextBox = itemView.findViewById(R.id.TDate);
        priceTextBox = itemView.findViewById(R.id.TPrice);


    }
}
