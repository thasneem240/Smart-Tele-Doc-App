package com.example.capstoneprojectgroup4.search_doctors;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.Frag_Remote_Consultation;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorAppointmentItemList;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.widget.Toast;

public class ViewAppointmentsAdapter extends RecyclerView.Adapter<ViewAppointmentsViewHolder> {

    private List<AppointmentItem> appointmentItemList;
    private Context context;
    private FragmentManager fragmentManager;

    public ViewAppointmentsAdapter(List<AppointmentItem> appointmentItemList, Context context,
                                   FragmentManager fragmentManager)
    {
        this.appointmentItemList = appointmentItemList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }

    public void removeAppointment(int position) {
        if (position >= 0 && position < appointmentItemList.size()) {
            appointmentItemList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @NonNull
    @Override
    public ViewAppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments, parent, false);
        return new ViewAppointmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewAppointmentsViewHolder holder, int position)
    {
        AppointmentItem appointmentItem = appointmentItemList.get(position);

        String docName = appointmentItem.getDoctorName().replaceAll("_", ".");


        // Set the data to the views in the ViewHolder
        holder.doctorNameTextView.setText(docName);
        holder.dayTextView.setText(appointmentItem.getDate());
        holder.typeTextView.setText(appointmentItem.getAppointmentType());
        holder.appNoTextView.setText("Appointment Number: " +appointmentItem.getAppointmentNumber());
        holder.locTextView.setText(appointmentItem.getLocation());
        holder.time.setText("Time: " +appointmentItem.getStartTime()+ "-"+ appointmentItem.getEndTime());


        CardView currentAppointmentCardView = holder.currentAppointmentCardView;


        currentAppointmentCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String appointmentType = appointmentItem.getAppointmentType();

//                if(true)
                if(checkDateAndTime(appointmentItem))
                {
                    if(appointmentType != null)
                    {

                        Frag_Remote_Consultation fragRemoteConsultation = new Frag_Remote_Consultation(appointmentItem);
                        fragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragRemoteConsultation).commit();

                    }

                }



            }
        });




        holder.CancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String patientKey = MainActivity.getPatientObject().getUid();
                    String appointmentKey = AppointmentKeyGenerator.getAppointmentKey();
                    String doctorName = AppointmentKeyGenerator.getDoctorName();
                    Log.d(TAG, "Cancel Appointment - appointmentKey: " + appointmentKey);
                    int itemPosition = holder.getBindingAdapterPosition(); // Use getBindingAdapterPosition()
                    cancelAppointment(appointmentKey, patientKey, itemPosition);
                    cancelDoctorAppointment(appointmentKey,doctorName);
                }
            });
    }


    private boolean checkDateAndTime(AppointmentItem appointmentItem)
    {
        boolean isValid = false;

        // Assuming appointmentDate, startTime, and endTime are strings in
        // the format "dd/MM/yyyy", "HH:mm", and "HH:mm" respectively

        // Get the current date and time
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();

        // Parse appointment date, start time, and end time strings to Date objects
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try
        {
            Date appointmentDate = dateFormat.parse(appointmentItem.getDate());
            Date startTime = timeFormat.parse(appointmentItem.getStartTime());
            Date endTime = timeFormat.parse(appointmentItem.getEndTime());

            Date currentDateWithoutTime = dateFormat.parse(dateFormat.format(currentDate));

            Log.d("currentDate: ", currentDate.toString() );
            Log.d("appointmentDate: ", appointmentDate.toString());
            Log.d("currentDateWithoutTime: ", currentDateWithoutTime.toString());

            // Check if the current date is equal to the appointment date
            if (appointmentDate.equals(currentDateWithoutTime))
            {
                Log.d("appointmentDate Equals currentDate ", appointmentDate.toString());


                Date currentTime = timeFormat.parse(timeFormat.format(currentDate));


                // Check if the current time is within the appointment start and end time
                if (currentTime.after(startTime) && currentTime.before(endTime))
                {
                    // Current time is within the appointment time slot
                    isValid = true;
                    Toast.makeText(context, "Appointment Started .", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Current time is outside the appointment time slot

                    Log.d("current Time in Else Statement: ", currentTime.toString() );
                    Log.d("End Time in Else Statement: ", endTime.toString() );

                    if(currentTime.after(endTime))
                    {
                        // Show error message
                        Toast.makeText(context, "Appointment time has passed .", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        // Show error message
                        Toast.makeText(context, "Appointment time has not started yet.", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            else
            {
                // Current date is not the same as the appointment date

                if(appointmentDate.after(currentDate))
                {
                    String toastMessage = "Your appointment is scheduled for " +
                            new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(appointmentDate) +
                            " from " + appointmentItem.getStartTime() + " to " + appointmentItem.getEndTime();

                    // Show error message
                    Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // Show error message
                    Toast.makeText(context, "Appointment Date Already Passed.", Toast.LENGTH_SHORT).show();
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return  isValid;
    }






    public void cancelAppointment(String appointmentKey, String patientKey,int position) {
        Log.d(TAG, "Cancel Appointment - patientKey: " + patientKey);
        if (appointmentKey == null || patientKey == null) {
            Log.e(TAG, "Cancel Appointment - appointmentKey or patientKey is null");
            return;
        }
        // Construct the reference path to the appointment
        DatabaseReference appointmentRef = FirebaseDatabase.getInstance()
                .getReference("Appointment Data")
                .child(patientKey)
                .child(appointmentKey);

        // Delete the appointment data
        appointmentRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CancelAppointment", "Successfully Deleted Patient Appointment");
                        removeAppointment(position); // Remove from the adapter's data list
                        //notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle any errors that occurred during deletion.
                        // You can show a toast or log the error.
                        Log.e("CancelAppointment", "Error deleting appointment", e);
                    }
                });
    }

    public void cancelDoctorAppointment(String appointmentKey, String DoctorName) {

        if (appointmentKey == null || DoctorName == null) {
            Log.e(TAG, "Cancel Appointment - appointmentKey or DoctorName is null");
            return;
        }
        // Construct the reference path to the appointment
        DatabaseReference appointmentRef = FirebaseDatabase.getInstance()
                .getReference("Doctor Appointments")
                .child(DoctorName)
                .child(appointmentKey);

        // Delete the appointment data
        appointmentRef.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.e("CancelAppointment", "Successfully Deleted Doctor Appointment");


                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("CancelAppointment", "Error deleting appointment for Doctor", e);
                    }
                });
    }

    @Override
    public int getItemCount()
    {
        return appointmentItemList.size();
    }



}