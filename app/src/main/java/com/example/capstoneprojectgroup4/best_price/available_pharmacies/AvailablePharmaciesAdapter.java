package com.example.capstoneprojectgroup4.best_price.available_pharmacies;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.ObjectPharmacyAndPrice;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PrescriptionTransaction;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.model.InitRequest;

public class AvailablePharmaciesAdapter extends RecyclerView.Adapter<AvailablePharmaciesViewHolder> {
    FirebaseDatabase database;
    ArrayList<ObjectPharmacyAndPrice> availablePharmacies;
    int pharmacyCount;

    public AvailablePharmaciesAdapter(ArrayList<ObjectPharmacyAndPrice> availablePharmacies){
        this.availablePharmacies = availablePharmacies;
        pharmacyCount = availablePharmacies.size();
    }

    @NonNull
    @Override
    public AvailablePharmaciesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_available_pharmacies,parent,false);
        AvailablePharmaciesViewHolder availablePharmaciesViewHolder = new AvailablePharmaciesViewHolder(view);
        return availablePharmaciesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AvailablePharmaciesViewHolder holder, int position) {
        holder.pharmacy_name.setText(String.valueOf(availablePharmacies.get(position).pharmacy));
        holder.totalPrice.setText("Rs. "+availablePharmacies.get(position).price);
        holder.pharmacy_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent senderIntent = new Intent(activity, PrescriptionTransaction.class);
                senderIntent.putExtra("ITEM",holder.pharmacy_name.getText());
                senderIntent.putExtra("PRICE","1200.00");
                activity.startActivity(senderIntent);

            }

        });

    }

    @Override
    public int getItemCount() {
        return pharmacyCount;
    }


}
