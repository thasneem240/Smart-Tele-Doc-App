package com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.select_from_the_list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.DrugData;

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
                PrescriptionDrugObject prescriptionDrugObject = new PrescriptionDrugObject();
                prescriptionDrugObject.setNameOfTheDrug(holder.drugName.getText().toString());

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                DrugData drugData = new DrugData(prescriptionDrugObject, position);
//                fm.beginTransaction().remove(fm.findFragmentById(R.id.FragmentContainerView_SelectTheDrug)).commit();
//                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugData).commit();

                fm.beginTransaction().replace(R.id.fragmentContainerPrescription, drugData).commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfDrugs.size();
    }
}
