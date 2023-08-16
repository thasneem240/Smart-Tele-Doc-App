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
import com.example.capstoneprojectgroup4.prescriptions.PrescriptionObject;

import java.util.ArrayList;

public class ViewPrescriptionsAdapter extends RecyclerView.Adapter<ViewPrescriptionsViewHolder> {
    ArrayList<PrescriptionObject> prescriptionObjectList;

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
        holder.date.setText(prescriptionObjectList.get(position).getDateTime());
        holder.doctor.setText(prescriptionObjectList.get(position).getDoctorName());

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selectedDrugs = prescriptionObjectList.get(position).getSelectedDrugs();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                EditPrescriptionFragment editPrescriptionFragment = new EditPrescriptionFragment(selectedDrugs);
                fm.beginTransaction().replace(R.id.fragment_container, editPrescriptionFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return prescriptionObjectList.size();
    }
}
