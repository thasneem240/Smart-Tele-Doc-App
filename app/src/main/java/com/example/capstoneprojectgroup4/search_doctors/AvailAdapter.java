package com.example.capstoneprojectgroup4.search_doctors;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;

public class AvailAdapter  extends RecyclerView.Adapter<AvailViewHolder> {

    private ArrayList<Availability> availabilities;
    private String doctorName;
    private String Day;
    private String date;

    private int noApp ;


    public AvailAdapter(ArrayList<Availability> availabilities, String doctorName, String Day, int noApp, String date) {
        this.availabilities = availabilities;
        this.doctorName = doctorName;
        this.Day = Day;
        this.noApp = noApp;
        this.date = date;
    }

    @NonNull
    @Override
    public AvailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_availability.xml layout and return a new AvailViewHolder
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.avail, parent, false);
        return new AvailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailViewHolder holder, int position) {
        Availability availability = availabilities.get(position);

        holder.bind(availability);
        //String Day = String.valueOf(holder.textDay);

        holder.textDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();

                //BookAppointmentF fragment = new BookAppointmentF();
                BookAppointmentF fragment = BookAppointmentF.newInstance(doctorName,date, String.valueOf(noApp));
                fm.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit();
            }
        });


    }

    @Override
    public int getItemCount() {
        return availabilities.size();
    }



}


