package com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.select_the_drug;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.WritingPrescriptionActivity;
import com.example.capstoneprojectgroup4.prescriptions.writing_prescriptions.drug_containers.DrugsContainers;

import java.util.ArrayList;

public class SelectTheDrugAdapter extends RecyclerView.Adapter<SelectTheDrugViewHolder> {
    ArrayList<String> listOfDrugs;
    int drugsCount;

    public SelectTheDrugAdapter(ArrayList<String> listOfDrugs){
        this.listOfDrugs = listOfDrugs;
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
                WritingPrescriptionActivity writingPrescriptionActivity =  (WritingPrescriptionActivity) view.getContext();
                writingPrescriptionActivity.setSelectedDrug2s(holder.drugName.getText().toString());

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                DrugsContainers drugsContainers = new DrugsContainers();
                fm.beginTransaction().remove(fm.findFragmentById(R.id.FragmentContainerView_SelectTheDrug)).commit();
                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugsContainers).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfDrugs.size();
    }
}
