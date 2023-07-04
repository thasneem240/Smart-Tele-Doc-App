package com.example.capstoneprojectgroup4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class DoctorAdapter extends FirebaseRecyclerAdapter<Doctors, DoctorAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public DoctorAdapter(@NonNull FirebaseRecyclerOptions<Doctors> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Doctors model) {

        holder.Name.setText(model.getName());
        holder.Specialization.setText(model.getSpecialization());
        holder.Location.setText(model.getLocation());



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors, parent, false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Specialization, Location;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = (TextView)itemView.findViewById(R.id.textName);
            Specialization = (TextView)itemView.findViewById(R.id.textSpecialization);
            Location = (TextView)itemView.findViewById(R.id.textLocation);
        }
    }

}

