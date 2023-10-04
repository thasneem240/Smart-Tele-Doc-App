package com.example.capstoneprojectgroup4.best_price.edit_howMuch;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class EditHowMuchViewHolder extends RecyclerView.ViewHolder {
    TextView medicineName, editButton;
    ImageView doneButton;
    EditText editDosage;
    ImageView removeButton;
    public EditHowMuchViewHolder(@NonNull View itemView) {
        super(itemView);

        medicineName = itemView.findViewById(R.id.TextView_PatientName);
        editDosage = itemView.findViewById(R.id.edit_dosage);
        editButton = itemView.findViewById(R.id.button_edit);
        doneButton = itemView.findViewById(R.id.button_done);
        removeButton = itemView.findViewById(R.id.Button_select);
    }
}
