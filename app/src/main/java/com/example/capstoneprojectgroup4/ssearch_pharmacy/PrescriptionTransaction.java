
package com.example.capstoneprojectgroup4;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;


public class PrescriptionTransaction extends AppCompatActivity {
    private static final int PAYHERE_REQUEST = 110;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    Map<String, Object> Transaction = new HashMap<>();

    String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_transaction);


        TextView Item = findViewById(R.id.Item2);
        TextView Price = findViewById(R.id.Price2);




        Button button = findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                item = Item.getText().toString();
                double price = Double.parseDouble(Price.getText().toString());
                InitRequest req = new InitRequest();
                req.setMerchantId("1223432");
                req.setMerchantSecret("MTczNTk3NTUzNDEzMjE5ODgyNTAzNzk2MzAxNTgzMzEwNTk2NTgw");// Merchant ID
                req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
                req.setAmount(price);             // Final Amount to be charged
                req.setOrderId("230000121");        // Unique Reference ID
                req.setItemsDescription(item);  // Item description title
                req.setCustom1("This is the custom message 1");
                req.setCustom2("This is the custom message 2");
                req.getCustomer().setFirstName("Saman");
                req.getCustomer().setLastName("Perera");
                req.getCustomer().setEmail("samanp@gmail.com");
                req.getCustomer().setPhone("+94771234567");
                req.getCustomer().getAddress().setAddress("No.1, Galle Road");
                req.getCustomer().getAddress().setCity("Colombo");
                req.getCustomer().getAddress().setCountry("Sri Lanka");
                req.getItems().add(new Item(null, item, 1, price));

                temp(req);

            }
        });
    }
    public void temp(InitRequest req)
    {
        Intent intent = new Intent(this, PHMainActivity.class);
        intent.putExtra(PHConstants.INTENT_EXTRA_DATA, req);
        PHConfigs.setBaseUrl(PHConfigs.SANDBOX_URL);
        startActivityForResult(intent, PAYHERE_REQUEST); //unique request ID e.g. "11001"
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        TextView textView = (TextView) findViewById(R.id.textView3);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess()){
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        String strDate = dateFormat.format(currentTime);
                        String itemname = strDate + " " + item;
                        Transaction.put("name", itemname);
                        myRef.child("Transaction").child("IDP " + strDate).setValue(Transaction);
                        msg = "Activity result:" + response.getData().toString();
                    }

                    else
                        msg = "Result:" + response.toString();
                else
                    msg = "Result: no response";
                Log.d(TAG, msg);
                textView.setText(msg);
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null)
                    textView.setText(response.toString());
                else
                    textView.setText("User canceled the request");
            }
        }
    }
}
