package com.example.capstoneprojectgroup4.wirting_prescriptions.select_the_drug;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.wirting_prescriptions.drug_containers.DrugsContainers;

import java.util.ArrayList;

public class SelectTheDrugAdapter extends RecyclerView.Adapter<SelectTheDrugViewHolder> {
    ArrayList<String> listOfDrugs;
    int drugsCount;
    int p;
    FragmentManager fm;

    public SelectTheDrugAdapter(ArrayList<String> listOfDrugs, int position){
        this.listOfDrugs = listOfDrugs;
        p = position;
        drugsCount = listOfDrugs.size();
    }
    @NonNull
    @Override
    public SelectTheDrugViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_select_the_drug,parent,false);
        SelectTheDrugViewHolder selectTheDrugViewHolder = new SelectTheDrugViewHolder(view);
        return selectTheDrugViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTheDrugViewHolder holder, int position) {
        holder.drugName.setText(listOfDrugs.get(position));

        holder.drugName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();

                DrugsContainers drugsContainers = new DrugsContainers(holder.drugName.getText().toString(), p);
                fm.beginTransaction().replace(R.id.fragment_container, drugsContainers).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return drugsCount;
    }
}
