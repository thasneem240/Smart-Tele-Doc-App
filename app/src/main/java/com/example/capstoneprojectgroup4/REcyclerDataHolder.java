package com.example.capstoneprojectgroup4;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class REcyclerDataHolder extends RecyclerView.ViewHolder {
    public TextView nameTextBox;


    public REcyclerDataHolder(@NonNull View itemView) {
        super(itemView);
        nameTextBox = itemView.findViewById(R.id.itemname);


    }
}
