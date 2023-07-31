package com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.writing_prescriptions.WritingPrescriptionActivity;

import java.util.ArrayList;
import java.util.Map;

public class DrugsContainersAdapter extends RecyclerView.Adapter<DrugsContainersViewHolder>{
    WritingPrescriptionActivity writingPrescriptionActivity;
    ArrayList<Map.Entry<String, Integer>> selectedDrugsList;


    public DrugsContainersAdapter(ArrayList<Map.Entry<String, Integer>> selectedDrugsList){
        this.selectedDrugsList = selectedDrugsList;
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
        Map.Entry<String, Integer> entry = selectedDrugsList.get(position);

            holder.drugsNames.setText(entry.getKey());

    }

    @Override
    public int getItemCount() {
        return selectedDrugsList.size();
    }
}
