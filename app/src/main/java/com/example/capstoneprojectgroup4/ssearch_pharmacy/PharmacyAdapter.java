package com.example.capstoneprojectgroup4.ssearch_pharmacy;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyViewHolder> {
    private ArrayList<Pharmacy> options;
    private Context context; // Add a context variable

    public PharmacyAdapter(Context context, ArrayList<Pharmacy> options) {
        this.context = context; // Initialize the context
        this.options = options;
    }
    @NonNull
    @Override
    public PharmacyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pharmacies, parent, false);
        return new PharmacyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PharmacyViewHolder holder, int position) {
        Pharmacy pharmacy = options.get(position);

        holder.Name.setText(pharmacy.getName());
        holder.Address.setText(pharmacy.getAddress());
        holder.PhoneNumber.setText(pharmacy.getPhoneNumber());
        holder.Maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the Google Maps URL from your Pharmacy object
                String googleMapUrl = pharmacy.getMaps(); // Use the correct method to obtain the URL

                if (googleMapUrl != null && !googleMapUrl.isEmpty()) {
                    // Create an Intent to open Google Maps
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapUrl));
                    intent.setPackage("com.google.android.apps.maps"); // Specify the Google Maps package

                    // Check if Google Maps app is available, if not, open a web browser
                    if (intent.resolveActivity(context.getPackageManager()) != null) {
                        context.startActivity(intent);
                    } else {
                        // If Google Maps app is not available, open the link in a web browser
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapUrl));
                        context.startActivity(webIntent);
                    }
                } else {
                    // Handle the case where the Google Maps URL is missing or empty
                    // You can display a message or take appropriate action here
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return options.size(); // Return the number of items in the ArrayList
    }
}
