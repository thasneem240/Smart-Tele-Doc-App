package com.example.capstoneprojectgroup4.best_price.edit_howMuch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;

import java.util.ArrayList;

public class EditHowMuchAdapter extends RecyclerView.Adapter<EditHowMuchViewHolder> {
    ArrayList<PrescriptionDrugObject> selectedDrugs;
    public EditHowMuchAdapter(ArrayList<PrescriptionDrugObject> selectedDrugs){
        this.selectedDrugs = selectedDrugs;
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
        int originalAmount = selectedDrugs.get(position).getAmount();

        holder.medicineName.setText(nameOfTheDrug + " " + strength);
        holder.editDosage.setText(originalAmount+"");

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.editDosage.setEnabled(true);
            }
        });
        holder.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int newAmount = Integer.parseInt(holder.editDosage.getText().toString());

                if(newAmount <= originalAmount){
                    selectedDrugs.get(position).setAmount(newAmount);
                    holder.editDosage.setEnabled(false);

                }
                else{
                    Toast.makeText(view.getContext(), "New amount need to be less than the original amount "+originalAmount, Toast.LENGTH_SHORT).show();
                }
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
