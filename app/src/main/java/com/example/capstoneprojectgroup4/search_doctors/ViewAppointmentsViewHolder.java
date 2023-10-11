package com.example.capstoneprojectgroup4.search_doctors;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.capstoneprojectgroup4.R;


public class ViewAppointmentsViewHolder extends RecyclerView.ViewHolder {

    public CardView currentAppointmentCardView;
    public TextView doctorNameTextView;
    public TextView dayTextView;

    public TextView locTextView;

    public TextView appNoTextView;

    public TextView time;



    public TextView typeTextView;
    public Button CancelButton;

    public RecyclerView recyclerAppView;


    public ViewAppointmentsViewHolder(@NonNull View itemView) {
        super(itemView);

        doctorNameTextView = itemView.findViewById(R.id.DoctorName);
        dayTextView = itemView.findViewById(R.id.Day);
        typeTextView = itemView.findViewById(R.id.Type);
        locTextView = itemView.findViewById(R.id.Location);
        appNoTextView = itemView.findViewById(R.id.AppNumber);
        CancelButton = itemView.findViewById(R.id.Cancel);
        time = itemView.findViewById(R.id.Time);

        currentAppointmentCardView = itemView.findViewById(R.id.currentAppointmentCardView);

        recyclerAppView = itemView.findViewById(R.id.recyclerAppView);

    }
}
