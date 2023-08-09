package com.example.capstoneprojectgroup4.prescriptions.edit_prescription;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;
import java.util.Map;

public class EditPrescriptionAdapter extends RecyclerView.Adapter<EditPrescriptionViewHolder > {
    ArrayList<String> selectedDrugs;
    public EditPrescriptionAdapter(ArrayList<Map<String, Object>> selectedDrugs){
        this.selectedDrugs = selectedDrugs;
    }

    @NonNull
    @Override
    public EditPrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_edit_prescription,parent,false);
        EditPrescriptionViewHolder editPrescriptionViewHolder = new EditPrescriptionViewHolder(view);
        return editPrescriptionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditPrescriptionViewHolder holder, int position) {
        String selectedDrug = selectedDrugs.get(position);
        holder.medicineName.setText(selectedDrug+"");
//        if(selectedDrug.containsKey("value"))
//            holder.editDosage.setText(selectedDrug.get("value")+"");

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.editDosage.setEnabled(true);
            }
        });
        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.editDosage.setEnabled(false);
//                selectedDrug.put("value", holder.editDosage.getText()+"");
            }
        });
        holder.removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.editDosage.setText(0+"");
                selectedDrug.put("value", holder.editDosage.getText()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedDrugs.size();
    }
}
