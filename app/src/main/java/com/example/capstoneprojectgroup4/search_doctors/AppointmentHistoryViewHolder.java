package com.example.capstoneprojectgroup4.search_doctors;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstoneprojectgroup4.R;

public class AppointmentHistoryViewHolder extends RecyclerView.ViewHolder {

    TextView doctorNameTextView, locationTextView, dayTextView,timeTextView;
    Button rebook;

    public AppointmentHistoryViewHolder(@NonNull View itemView) {
        super(itemView);

        doctorNameTextView = itemView.findViewById(R.id.DoctorName2);
        locationTextView = itemView.findViewById(R.id.Location2);
        dayTextView = itemView.findViewById(R.id.Day2);
        rebook = itemView.findViewById(R.id.ReBook);
        timeTextView = itemView.findViewById(R.id.TimeTXT);

    }
}
