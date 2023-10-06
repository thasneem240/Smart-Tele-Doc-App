package com.example.capstoneprojectgroup4.best_price.listOf_prescriptions;

import static android.app.PendingIntent.getActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.best_price.edit_howMuch.EditHowMuchFragment;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;

import java.util.ArrayList;

public class ListOfPrescriptionsAdapter extends RecyclerView.Adapter<ListOfPrescriptionsViewHolder> {
    ArrayList<PrescriptionObject> prescriptionObjectList;

    public ListOfPrescriptionsAdapter(ArrayList<PrescriptionObject> prescriptionObjectList){
        this.prescriptionObjectList = prescriptionObjectList;
    }
    @NonNull
    @Override
    public ListOfPrescriptionsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_prescriptions,parent,false);
        ListOfPrescriptionsViewHolder listOfPrescriptionsViewHolder = new ListOfPrescriptionsViewHolder(view);
        return listOfPrescriptionsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfPrescriptionsViewHolder holder, int position) {
        holder.date.setText(prescriptionObjectList.get(position).getWrittenOn());
        holder.doctor.setText(prescriptionObjectList.get(position).getDoctorName());

        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<PrescriptionDrugObject> selectedDrugs = prescriptionObjectList.get(position).getSelectedDrugs();

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                EditHowMuchFragment editHowMuchFragment = new EditHowMuchFragment(selectedDrugs);
                fm.beginTransaction().replace(R.id.fragmentContainerView, editHowMuchFragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return prescriptionObjectList.size();
    }
}
