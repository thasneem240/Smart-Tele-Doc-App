package com.example.capstoneprojectgroup4.interface_of_doctors;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.Activity_Agora_AudioConference;
import com.example.capstoneprojectgroup4.Activity_Agora_VideoConference;
import com.example.capstoneprojectgroup4.Frag_Remote_Consultation;
import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.search_doctors.AppointmentItem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class DoctorViewAppointmentsAdapter extends RecyclerView.Adapter<DoctorViewAppointmentsViewHolder>
{
    private List<DoctorAppointmentItemList> appointmentItemList;
    private Context context;
    private FragmentManager fragmentManager;
    public DoctorViewAppointmentsAdapter(List<DoctorAppointmentItemList> appointmentItemList,
                                         Context context, FragmentManager fragmentManager)
    {
        this.appointmentItemList = appointmentItemList;
        this.context = context;
        this.fragmentManager = fragmentManager;
    }


    @NonNull
    @Override
    public DoctorViewAppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctor_appointments, parent, false);
        return new DoctorViewAppointmentsViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull DoctorViewAppointmentsViewHolder holder, int position)
    {
        DoctorAppointmentItemList appointmentItem = appointmentItemList.get(position);




        // Set the data to the views in the ViewHolder
        holder.doctorNameTextView.setText(appointmentItem.getPatientName());
        holder.dayTextView.setText(appointmentItem.getDate());
        holder.typeTextView.setText(appointmentItem.getAppointmentType());
        holder.appointmentNumberTextView.setText("Appointment Number: " +appointmentItem.getAppointmentNumber());
        holder.locationTextView.setText(appointmentItem.getLocation());


        CardView doctorAppointmentCardView = holder.doctorAppointmentCardView;
        holder.time.setText("Time: " +appointmentItem.getStartTime()+ "-"+ appointmentItem.getEndTime());
        holder.time.setText("Time: " +appointmentItem.getStartTime()+ "-"+ appointmentItem.getEndTime());

        doctorAppointmentCardView.setOnClickListener(new View.OnClickListener()
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

                        // Audio Conference
                        if(appointmentType.equalsIgnoreCase("Voice"))
                        {
                            startAudioConference();
                        }
                        else
                        {
                            startVideoConference();
                        }

                    }

                }



            }
        });

    }

    private boolean checkDateAndTime(DoctorAppointmentItemList appointmentItem)
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


    private void startVideoConference()
    {
        // Create an Intent to specify the target activity
        Intent intent = new Intent(context, Activity_Agora_VideoConference.class);

        // Start the target activity using the Intent
        context.startActivity(intent);
    }



    private void startAudioConference()
    {
        // Create an Intent to specify the target activity
        Intent intent = new Intent(context, Activity_Agora_AudioConference.class);

        // Start the target activity using the Intent
        context.startActivity(intent);
    }







    @Override
    public int getItemCount()
    {
        return appointmentItemList.size();
    }





}
