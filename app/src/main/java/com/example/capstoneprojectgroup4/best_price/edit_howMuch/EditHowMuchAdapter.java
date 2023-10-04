package com.example.capstoneprojectgroup4.best_price.edit_howMuch;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;

import java.util.ArrayList;

public class EditHowMuchAdapter extends RecyclerView.Adapter<EditHowMuchViewHolder> {
    ArrayList<PrescriptionDrugObject> selectedDrugs;
    public EditHowMuchAdapter(ArrayList<PrescriptionDrugObject> selectedDrugs){
        this.selectedDrugs = selectedDrugs;
        Log.d("key key", selectedDrugs.toString());
    }

    @NonNull
    @Override
    public EditHowMuchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_edit_how_much,parent,false);
        EditHowMuchViewHolder editHowMuchViewHolder = new EditHowMuchViewHolder(view);
        return editHowMuchViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EditHowMuchViewHolder holder, int position) {
        String nameOfTheDrug = selectedDrugs.get(position).getNameOfTheDrug();
        String strength = selectedDrugs.get(position).getStrength();
        int amount = selectedDrugs.get(position).getAmount();

        holder.medicineName.setText(nameOfTheDrug + " " + strength);
        holder.editDosage.setText(amount+"");

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
                selectedDrugs.get(position).setAmount(Integer.parseInt(holder.editDosage.getText().toString()));
            }
        });
        holder.removeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                holder.editDosage.setText(0+"");
                selectedDrugs.get(position).setAmount(Integer.parseInt(holder.editDosage.getText().toString()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return selectedDrugs.size();
    }
}
