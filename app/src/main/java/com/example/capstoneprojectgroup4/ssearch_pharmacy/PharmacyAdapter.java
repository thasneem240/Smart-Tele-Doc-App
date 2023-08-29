package com.example.capstoneprojectgroup4.ssearch_pharmacy;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class PharmacyAdapter extends FirebaseRecyclerAdapter<Pharmacy, PharmacyAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public PharmacyAdapter(@NonNull FirebaseRecyclerOptions<Pharmacy> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Pharmacy model) {

        holder.Name.setText(model.getName());
        holder.Address.setText(model.getAddress());
        holder.PhoneNumber.setText(model.getPhoneNumber());



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacies, parent, false);
        return new myViewHolder(view);

    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Address, PhoneNumber;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = (TextView)itemView.findViewById(R.id.textName);
            Address = (TextView)itemView.findViewById(R.id.textD);
            PhoneNumber = (TextView)itemView.findViewById(R.id.textPhone);
        }
    }

}
