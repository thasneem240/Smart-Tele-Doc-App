package com.example.capstoneprojectgroup4;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransactionHistory extends AppCompatActivity {
    ArrayList <TransactionHistoryData> data;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    REcyclerAdapter adapter;


    public TransactionHistory(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        RecyclerView rv= findViewById(R.id.recView);
        data = new ArrayList<TransactionHistoryData>();
        EventChangeListener();
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new REcyclerAdapter(data);
        rv.setAdapter(adapter);
    }

    private void EventChangeListener() {
        db.collection("Transactions")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                data.add(dc.getDocument().toObject(TransactionHistoryData.class));
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}