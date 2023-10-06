
package com.example.capstoneprojectgroup4.transaction;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.REcyclerAdapter;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.List;


public class TransactionHistory extends AppCompatActivity {
    ArrayList <TransactionHistoryData> data;
    Button homePage;
    Button chatBot;

    ImageView backButton;

    Button appointments;
    Button userProfile;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Transaction");
    REcyclerAdapter adapter;


    public TransactionHistory(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        backButton = findViewById(R.id.backButtonTransHistory);




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatbotActivity = new Intent(TransactionHistory.this, MainActivity2.class);
                startActivity(chatbotActivity);
            }
        });






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
