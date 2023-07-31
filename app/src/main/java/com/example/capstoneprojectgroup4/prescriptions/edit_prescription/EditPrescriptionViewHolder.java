package com.example.capstoneprojectgroup4.prescriptions.edit_prescription;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class EditPrescriptionViewHolder extends RecyclerView.ViewHolder {
    TextView medicineName, editButton, doneButton;
    EditText editDosage;
    Button removeButton;
    public EditPrescriptionViewHolder(@NonNull View itemView) {
        super(itemView);

        medicineName = itemView.findViewById(R.id.text_medicine);
        editDosage = itemView.findViewById(R.id.edit_dosage);
        editButton = itemView.findViewById(R.id.button_edit);
        doneButton = itemView.findViewById(R.id.button_done);
        removeButton = itemView.findViewById(R.id.text_select);
    }
}
