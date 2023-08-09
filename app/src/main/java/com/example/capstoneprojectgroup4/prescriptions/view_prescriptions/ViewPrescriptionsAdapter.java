package com.example.capstoneprojectgroup4.prescriptions.view_prescriptions;

import static android.app.PendingIntent.getActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.prescriptions.edit_prescription.EditPrescriptionFragment;
import com.example.capstoneprojectgroup4.writing_prescriptions.drug_containers.PrescriptionObject;

import java.util.ArrayList;
import java.util.Map;

public class ViewPrescriptionsAdapter extends RecyclerView.Adapter<ViewPrescriptionsViewHolder> {
    ArrayList<Map.Entry<String, Object>> prescriptionsList;
    ArrayList<PrescriptionObject> prescriptionObjectList;
    PrescriptionObject prescriptionObject;

    public ViewPrescriptionsAdapter(ArrayList<PrescriptionObject> prescriptionObjectList){
        this.prescriptionObjectList = prescriptionObjectList;
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
        Map.Entry<String, Object> entry = prescriptionsList.get(position);
        Map<Integer, Object> eachPrescription = (Map) entry.getValue();
        holder.date.setText(eachPrescription.get("Date")+"");
        holder.doctor.setText(eachPrescription.get("Doctor's name")+"");

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                ArrayList<Map<String, Object>> selectedDrugs = (ArrayList<Map<String, Object>>) eachPrescription.get("Selected drugs");
                EditPrescriptionFragment editPrescriptionFragment = new EditPrescriptionFragment(selectedDrugs);
                fm.beginTransaction().replace(R.id.fragment_container, editPrescriptionFragment).commit();
            }
        });

        prescriptionObject = prescriptionObjectList.get(position);
        holder.date.setText(prescriptionObject.getDateTime()+"");
        holder.doctor.setText(prescriptionObject.getDoctorName()+"");
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                ArrayList<String> selectedDrugs = (ArrayList<String>) prescriptionObject.getSelectedDrugs();
                EditPrescriptionFragment editPrescriptionFragment = new EditPrescriptionFragment(selectedDrugs);
                fm.beginTransaction().replace(R.id.fragment_container, editPrescriptionFragment).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return prescriptionsList.size();
    }
}
