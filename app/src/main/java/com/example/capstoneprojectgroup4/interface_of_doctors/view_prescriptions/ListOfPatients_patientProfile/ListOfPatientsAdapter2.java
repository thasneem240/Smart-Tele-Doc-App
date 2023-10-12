package com.example.capstoneprojectgroup4.interface_of_doctors.view_prescriptions.ListOfPatients_patientProfile;

import static com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.ListOfPatients_writingPrescription.AppointmentObject;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.WritingPrescriptionActivity;
import com.example.capstoneprojectgroup4.patient_authentication.PatientObject;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListOfPatientsAdapter2 extends RecyclerView.Adapter<ListOfPatientsViewHolder2> {
    ArrayList<AppointmentObject> appointmentObjects;
    ListOfPatientsAdapter2(ArrayList<AppointmentObject> appointmentObjects){
        this.appointmentObjects = appointmentObjects;
    }
    @NonNull
    @Override
    public ListOfPatientsViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_patients_for_each_doctor,parent,false);
        ListOfPatientsViewHolder2 listOfPatientsViewHolder2 = new ListOfPatientsViewHolder2(view);
        return listOfPatientsViewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfPatientsViewHolder2 holder, int position) {
        holder.patientName.setText(appointmentObjects.get(position).getPatientName());
        holder.dob.setText("Appointment date " + appointmentObjects.get(position).getDate());
        holder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                FragmentManager fm = activity.getSupportFragmentManager();
                DoctorsActivity.setAppointmentObject(appointmentObjects.get(position));
                DoctorMedicalRecords doctorMedicalRecords = new DoctorMedicalRecords();
                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, doctorMedicalRecords).commit();

//                FragmentManager fm = activity.getSupportFragmentManager();
//                PrescriptionsListFragment2 prescriptionsListFragment2 = new PrescriptionsListFragment2(appointmentObjects.get(position).getPatientUserId());
//                fm.beginTransaction().replace(R.id.fragmentContainerDoctorsActivity, prescriptionsListFragment2).commit();

//                openWritingPrescription(appointmentObjects.get(position).getPatientUserId(), view);  // retrievePatientObjectAndOpenWritingPrescription();
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
