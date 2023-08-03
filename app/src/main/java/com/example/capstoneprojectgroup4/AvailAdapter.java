package com.example.capstoneprojectgroup4;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AvailAdapter  extends RecyclerView.Adapter<AvailViewHolder> {

    private ArrayList<Availability> availabilities;

    public AvailAdapter(ArrayList<Availability> availabilities) {
        this.availabilities = availabilities;
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


    }

    @Override
    public int getItemCount() {
        return availabilities.size();
    }
}