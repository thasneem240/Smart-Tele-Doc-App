package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.capstoneprojectgroup4.ssearch_pharmacy.PrescriptionTransaction;
import com.example.capstoneprojectgroup4.transaction.AppointmentTransaction;
import com.example.capstoneprojectgroup4.transaction.TransactionHistory;

public class ResultActivity extends AppCompatActivity
{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, AppointmentTransaction.class);
                startActivity(intent);

            }
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this, PrescriptionTransaction.class);
                startActivity(intent);


            }
        });


        Button button5 = findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
               Intent intent = new Intent(ResultActivity.this, TransactionHistory.class);
               startActivity(intent);


            }
        });



    }



}