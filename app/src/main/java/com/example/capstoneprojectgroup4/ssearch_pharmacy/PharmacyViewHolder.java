package com.example.capstoneprojectgroup4.ssearch_pharmacy;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;


    public class PharmacyViewHolder extends RecyclerView.ViewHolder{
        TextView Name,Address, PhoneNumber;

        public PharmacyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = (TextView)itemView.findViewById(R.id.textName);
            Address = (TextView)itemView.findViewById(R.id.textD);
            PhoneNumber = (TextView)itemView.findViewById(R.id.textPhone);
        }
    }

