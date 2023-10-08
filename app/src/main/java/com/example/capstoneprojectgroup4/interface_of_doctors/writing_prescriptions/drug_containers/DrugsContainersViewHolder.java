package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.drug_containers;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class DrugsContainersViewHolder extends RecyclerView.ViewHolder{
    TextView medicineName, howMuch, doneButton;
    EditText how;
    Button removeButton;

    public DrugsContainersViewHolder(@NonNull View itemView) {
        super(itemView);

        medicineName = itemView.findViewById(R.id.textView_medicineName);
        howMuch = itemView.findViewById(R.id.textView_howMuch);
    }
}
