
package com.example.capstoneprojectgroup4.ssearch_pharmacy;

import static android.content.ContentValues.TAG;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.transaction.TransactionHistory;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import lk.payhere.androidsdk.PHConfigs;
import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.PHMainActivity;
import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.Item;
import lk.payhere.androidsdk.model.StatusResponse;


public class PrescriptionTransaction extends AppCompatActivity {
    private static final int PAYHERE_REQUEST = 110;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private Map<String, Object> Transaction = new HashMap<>();


    private String getPatientName;

    private String phonenum;


    private String PatientID;

    private String email;

    String item = null;

    String description = null;
    double price = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_transaction);
        ImageView previousButton = findViewById(R.id.backButtonPT);
        getPatientName = MainActivity.getPatientObject().getFirstName();
        String getPatientLastName = MainActivity.getPatientObject().getLastName();
        email = MainActivity.getPatientObject().getEmail();
        phonenum = MainActivity.getPatientObject().getMobile();
        String address = MainActivity.getPatientObject().getAddress();
        String city = MainActivity.getPatientObject().getCity();
        String country = MainActivity.getPatientObject().getCountry();

        TextView Item = findViewById(R.id.EditText_Item);
        TextView Price = findViewById(R.id.EditText_Price);
        TextView descr = findViewById(R.id.EditText_Descrip);

        Intent recive = getIntent();
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Objects.equals(recive.getStringExtra("Chatbot"), "true")){
                    finish();
                }
                else {
                    FragmentManager fm = PrescriptionTransaction.this.getSupportFragmentManager();
                    fm.popBackStack("DocAvailF", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }

            }
        });

        Item.setText(recive.getStringExtra("ITEM"));
        Price.setText(recive.getStringExtra("PRICE"));
        if (Objects.equals(recive.getStringExtra("Chatbot"), "true")){
            item = "Prescription purchase from "+recive.getStringExtra("ITEM");
        }
        else {
            item = recive.getStringExtra("ITEM");
        }
        price = Double.parseDouble(recive.getStringExtra("PRICE"));

        Button button = findViewById(R.id.PaymentButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                description = descr.getText().toString();
                if (item == null || price == 0.0){
                    String text = "Item and price not specified";
                    Toast.makeText(PrescriptionTransaction.this, text, Toast.LENGTH_SHORT).show();
                    item = Item.getText().toString();
                    price = Double.parseDouble(Price.getText().toString());
                }

                if (description == ""){
                    String text = "Please provide a description of the item you purchased.";
                    Toast.makeText(PrescriptionTransaction.this, text, Toast.LENGTH_SHORT).show();
                    description = descr.getText().toString();
                }

                InitRequest req = new InitRequest();
                req.setMerchantId("1223432");
                req.setMerchantSecret("MTczNTk3NTUzNDEzMjE5ODgyNTAzNzk2MzAxNTgzMzEwNTk2NTgw");// Merchant ID
                req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
                req.setAmount(price);             // Final Amount to be charged
                req.setOrderId("230000121");        // Unique Reference ID
                req.setItemsDescription(item);  // Item description title
                req.setCustom1("This is the custom message 1");
                req.setCustom2("This is the custom message 2");
                req.getCustomer().setFirstName(getPatientName);
                req.getCustomer().setLastName(getPatientLastName);
                req.getCustomer().setEmail(email);
                req.getCustomer().setPhone(phonenum);
                req.getCustomer().getAddress().setAddress(address);
                req.getCustomer().getAddress().setCity(city);
                req.getCustomer().getAddress().setCountry(country);
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYHERE_REQUEST && data != null && data.hasExtra(PHConstants.INTENT_EXTRA_RESULT)) {
            PHResponse<StatusResponse> response = (PHResponse<StatusResponse>) data.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT);
            if (resultCode == Activity.RESULT_OK) {
                String msg;
                if (response != null)
                    if (response.isSuccess()){
                        Date currentTime = Calendar.getInstance().getTime();
                        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
                        String strDate = dateFormat.format(currentTime);
                        String itemname = item;
                        PatientID = MainActivity.getPatientObject().getUid();
                        Transaction.put("name", itemname);
                        Transaction.put("item",itemname+", Quantity 1, "+"LKR "+price);
                        Transaction.put("date", strDate);
                        Transaction.put("price", "LKR "+price);
                        Transaction.put("description",description);
                        Transaction.put("patientID",PatientID);
                        myRef.child("Transaction").child("IDM " + strDate).setValue(Transaction);
                        msg = "Activity result:" + response.getData().toString();
                        //after successful payment book appointment

                    }

                    else
                        msg = "Result:" + response.toString();
                else
                    msg = "Result: no response";
                Log.d(TAG, msg);
                Toast.makeText( this,msg, Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (response != null)
                    Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this, "User canceled the request", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
