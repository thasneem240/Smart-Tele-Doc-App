package com.example.capstoneprojectgroup4.search_doctors;
import static androidx.core.content.ContentProviderCompat.requireContext;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private String location;
    private double docPrice;


    private int noApp ;


    public AvailAdapter(ArrayList<Availability> availabilities, String doctorName, String Day, int noApp, String date, String location, double docPrice) {
        this.availabilities = availabilities;
        this.doctorName = doctorName;
        this.Day = Day;
        this.noApp = noApp;
        this.date = date;
        this.location = location;
        this.docPrice = docPrice;
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
        Log.d("AvailAdapter", "startTime: " + availability.getStartTime());
        Log.d("AvailAdapter", "endTime: " + availability.getEndTime());
        Log.d("AvailAdapter", "noapp: " + availability.getNoApp());
        Log.d("AvailAdapter", "Location: " + location);

        holder.bind(availability);
        String day = availability.getDay();

        String dateV = availability.getDate();
        String start = availability.getStartTime();
        String End = availability.getEndTime();
        double doctorprice = availability.getPrice();

        holder.textDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();



                if(availability.getNoApp() < 30)
                {
                    // Pass the selected date to the BookAppointmentF fragment
                    BookAppointmentF fragment = BookAppointmentF.newInstance(doctorName, day, dateV, start, End, String.valueOf(availability.getNoApp()), location, doctorprice);
                    fm.beginTransaction()
                            .replace(R.id.fragmentContainerView, fragment)
                            .addToBackStack("DocAvailF")
                            .commit();
                }
                else
                {
                    Toast.makeText(view.getContext(), "Doctor is fully booked", Toast.LENGTH_SHORT).show();

                }




                }
        });
    }


    @Override
    public int getItemCount() {
        return availabilities.size();
}



}