package com.example.capstoneprojectgroup4.search_doctors;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.List;

public class AppointmentHistoryAdapter extends RecyclerView.Adapter<AppointmentHistoryViewHolder> {

    private List<AppointmentItem> appointmentItemList;

    public AppointmentHistoryAdapter(List<AppointmentItem> appointmentItemList) {
        this.appointmentItemList = appointmentItemList;
    }

    @NonNull
    @Override
    public AppointmentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_history, parent, false);
        return new AppointmentHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHistoryViewHolder holder, int position) {
        AppointmentItem appointmentItem = appointmentItemList.get(position);

        String docName = appointmentItem.getDoctorName().replaceAll("_", ".");

        // Set the data to the views in the ViewHolder
        holder.doctorNameTextView.setText(docName);
        holder.dayTextView.setText(appointmentItem.getDate());
        holder.locationTextView.setText(appointmentItem.getLocation());
        holder.timeTextView.setText("Time: " +appointmentItem.getStartTime()+ "-"+ appointmentItem.getEndTime());





        holder.rebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                AppHistoryDocAvailF fragment = AppHistoryDocAvailF.newInstance(docName, appointmentItem.getLocation());
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
            }
        });



    }

    @Override
    public int getItemCount() {
        return appointmentItemList.size();
    }


}
