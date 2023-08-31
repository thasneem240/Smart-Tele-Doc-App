
package com.example.capstoneprojectgroup4;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.capstoneprojectgroup4.authentication.PatientObject;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionHistory extends AppCompatActivity {
    ArrayList <TransactionHistoryData> data;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Transaction");
    REcyclerAdapter adapter;


    public TransactionHistory(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        RecyclerView rv= findViewById(R.id.recView);
        data = new ArrayList<TransactionHistoryData>();

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new REcyclerAdapter(data);
        rv.setAdapter(adapter);


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<DataSnapshot> childrenList = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    childrenList.add(snapshot);
                }


                for (int i = childrenList.size() - 1; i >= 0; i--) {
                    DataSnapshot Snapshot = childrenList.get(i);
                    data.add(Snapshot.getValue(TransactionHistoryData.class)) ;
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }


        });
    }




}
