package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.drug_details;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

public class DrugDetailsViewHolder2 extends RecyclerView.ViewHolder {
    TextView medName;
    TextView brandName;
    TextView strength;
    TextView amount;
    TextView notes;
    public DrugDetailsViewHolder2(@NonNull View itemView) {
        super(itemView);

        medName = itemView.findViewById(R.id.textView_medName);
        brandName = itemView.findViewById(R.id.textView_brandName);
        strength = itemView.findViewById(R.id.textView_strength);
        amount = itemView.findViewById(R.id.textView_amount);
        notes = itemView.findViewById(R.id.textView_medNotes);
    }
}
