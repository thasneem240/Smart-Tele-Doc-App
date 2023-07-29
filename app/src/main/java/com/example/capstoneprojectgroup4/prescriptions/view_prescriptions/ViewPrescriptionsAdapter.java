package com.example.capstoneprojectgroup4.prescriptions.view_prescriptions;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.Map;

public class ViewPrescriptionsAdapter extends RecyclerView.Adapter<ViewPrescriptionsViewHolder> {
    Map<Integer, Object> prescriptions;
    Map<Integer, Object> eachPrescription;

    public ViewPrescriptionsAdapter(Map<Integer, Object> prescriptions){
        this.prescriptions = prescriptions;
    }
    @NonNull
    @Override
    public ViewPrescriptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_prescriptions,parent,false);
        ViewPrescriptionsViewHolder viewPrescriptionsViewHolder = new ViewPrescriptionsViewHolder(view);
        return viewPrescriptionsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewPrescriptionsViewHolder holder, int position) {
        eachPrescription = (Map) prescriptions.get(position);
        holder.date.setText(eachPrescription.get("Date")+"");
        holder.doctor.setText(eachPrescription.get("Doctor's name")+"");
    }

    @Override
    public int getItemCount() {
        return prescriptions.size();
    }
}
