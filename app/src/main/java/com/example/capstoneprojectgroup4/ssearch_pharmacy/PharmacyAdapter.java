package com.example.capstoneprojectgroup4.ssearch_pharmacy;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;

import java.util.ArrayList;

public class PharmacyAdapter extends RecyclerView.Adapter<PharmacyViewHolder> {
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private ArrayList<Pharmacy> options;
    private Context context;



    public PharmacyAdapter(Context context, ArrayList<Pharmacy> options) {
        this.context = context;
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
                // Check if location permissions are granted
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    // Location permissions are granted
                    openGoogleMapsWithLocation(pharmacy.getMaps());
                } else {
                    // Request location permissions
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return options.size();
    }

    // Handle the result of the location permission request
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permissions granted
                openGoogleMapsWithLocation(options.get(0).getMaps()); // You need to specify which pharmacy's maps URL to use here
            } else {
                // Location permissions denied, handle this case
                // You can display a message or take appropriate action here
            }
        }
    }

    private void openGoogleMapsWithLocation(String googleMapUrl) {
        if (googleMapUrl != null && !googleMapUrl.isEmpty()) {
            // Create an Intent to open Google Maps
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(googleMapUrl));
            intent.setPackage("com.google.android.apps.maps"); // Specify the Google Maps package

            // Check if Google Maps app is available, if not, open the link in a web browser
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







}
