
package com.example.capstoneprojectgroup4.transaction;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

import java.util.List;
import java.util.Objects;


public class TransactionHistory extends AppCompatActivity {
    public ArrayList <TransactionHistoryData> data;
    public Button homePage;
    public Button chatBot;

    public ImageView backButton;

    public Button appointments;
    public Button userProfile;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Transaction");
    public REcyclerAdapter adapter;


    public TransactionHistory(){

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);
        backButton = findViewById(R.id.backButtonTransHistory);
        homePage = findViewById(R.id.homePageButton);
        chatBot = findViewById(R.id.chatBotButton);
        appointments = findViewById(R.id.appointmentButton);
        userProfile = findViewById(R.id.userProfileButton);



        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Activity = new Intent(TransactionHistory.this, MainActivity2.class);
                startActivity(Activity);
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
                    TransactionHistoryData singleData= data.get(data.size() - 1);
                    if(!Objects.equals(singleData.getPatientID(), MainActivity.getPatientObject().getUid())){
                        data.remove(data.size() - 1);
                    }


                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "loadPost:onCancelled", error.toException());
            }
        });

        homePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Activity = new Intent(TransactionHistory.this, MainActivity2.class);
                startActivity(Activity);
            }
        });

        chatBot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chatbotActivity = new Intent(TransactionHistory.this, ChatbotActivity.class);
                startActivity(chatbotActivity);
            }
        });

        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent senderIntent = new Intent(TransactionHistory.this, MainActivity2.class);
                senderIntent.putExtra("Page","appointmentsList");
                startActivity(senderIntent);
            }
        });


        userProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent senderIntent = new Intent(TransactionHistory.this, MainActivity2.class);
                senderIntent.putExtra("Page","patientDetails");
                startActivity(senderIntent);

            }
        });
    }



}
