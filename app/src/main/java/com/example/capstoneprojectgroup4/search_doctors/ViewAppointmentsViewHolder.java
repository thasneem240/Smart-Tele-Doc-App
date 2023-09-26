package com.example.capstoneprojectgroup4.search_doctors;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstoneprojectgroup4.R;


public class ViewAppointmentsViewHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView doctorNameTextView;
    public TextView dayTextView;
    public TextView typeTextView;
    public TextView cancelTextView;

    public RecyclerView recyclerAppView;


    public ViewAppointmentsViewHolder(@NonNull View itemView) {
        super(itemView);

        doctorNameTextView = itemView.findViewById(R.id.DoctorName);
        dayTextView = itemView.findViewById(R.id.Day);
        typeTextView = itemView.findViewById(R.id.Type);
        cancelTextView = itemView.findViewById(R.id.Cancel);
        recyclerAppView = itemView.findViewById(R.id.recyclerAppView);

    }
}
