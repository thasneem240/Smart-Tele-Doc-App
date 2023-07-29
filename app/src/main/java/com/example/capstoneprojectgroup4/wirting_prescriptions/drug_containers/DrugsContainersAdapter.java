package com.example.capstoneprojectgroup4.wirting_prescriptions.drug_containers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.wirting_prescriptions.WritingPrescriptionActivity;

import java.util.ArrayList;

public class DrugsContainersAdapter extends RecyclerView.Adapter<DrugsContainersViewHolder>{
    WritingPrescriptionActivity writingPrescriptionActivity;
    int numberOfContainers;
    ArrayList<String> selectedDrugs;

    public DrugsContainersAdapter(ArrayList<String> selectedDrugs){
        this.selectedDrugs = selectedDrugs;
    }

    @NonNull
    @Override
    public DrugsContainersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_drugs_containers,parent,false);
        DrugsContainersViewHolder drugsContainersViewHolder = new DrugsContainersViewHolder(view);
        writingPrescriptionActivity =  (WritingPrescriptionActivity) parent.getContext();

        return drugsContainersViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DrugsContainersViewHolder holder, int position) {
            holder.drugsNames.setText(selectedDrugs.get(position));

    }

    @Override
    public int getItemCount() {
        return selectedDrugs.size();
    }
}
