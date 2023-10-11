package com.example.capstoneprojectgroup4.interface_of_doctors.ListOfPatients_writingPrescription;

import static android.app.PendingIntent.getActivity;

import static com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.WritingPrescriptionActivity;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListOfPatientsAdapter extends RecyclerView.Adapter<ListOfPatientsViewHolder> {
    ArrayList<AppointmentObject> appointmentObjects;
    ListOfPatientsAdapter(ArrayList<AppointmentObject> appointmentObjects){
        this.appointmentObjects = appointmentObjects;
    }
    @NonNull
    @Override
    public ListOfPatientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_patients_for_each_doctor,parent,false);
        ListOfPatientsViewHolder listOfPatientsViewHolder = new ListOfPatientsViewHolder(view);
        return listOfPatientsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfPatientsViewHolder holder, int position) {
        holder.patientName.setText(appointmentObjects.get(position).getPatientName());
        holder.dob.setText("Appointment date " + appointmentObjects.get(position).getDate());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openWritingPrescription(appointmentObjects.get(position).getPatientUserId(), view);  // retrievePatientObjectAndOpenWritingPrescription();
            }
        });
    }

    private void openWritingPrescription(String patientUserId, View view){
        FirebaseDatabase firebaseDatabase;
        DatabaseReference databaseReference;

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(patientUserId);

        databaseReference.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                PatientObject patientObject = dataSnapshot.getValue(PatientObject.class);

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Intent intent = new Intent(activity, WritingPrescriptionActivity.class);
                intent.putExtra("patientObject", patientObject);
                activity.startActivity(intent);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "Error in the database. Please try again.");
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentObjects.size();
    }
}
