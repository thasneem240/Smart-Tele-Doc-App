package com.example.capstoneprojectgroup4.chatbot;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMainMenu;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.example.capstoneprojectgroup4.search_doctors.SearchDocF;
import com.example.capstoneprojectgroup4.transaction.TransactionHistory;
import com.example.capstoneprojectgroup4.search_doctors.BookAppointmentF;
import com.example.capstoneprojectgroup4.chatbot.adapters.ChatAdapter;
import com.example.capstoneprojectgroup4.chatbot.helpers.SendMessageInBg;
import com.example.capstoneprojectgroup4.chatbot.interfaces.BotReply;
import com.example.capstoneprojectgroup4.chatbot.models.Message;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PrescriptionTransaction;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;


import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;


public class ChatbotActivity extends AppCompatActivity implements BotReply {

  RecyclerView chatView;
  ChatAdapter chatAdapter;
  List<Message> messageList = new ArrayList<>();
  EditText editMessage;
  ImageButton btnSend;

  ImageView backButton;

  //dialogFlow
  private SessionsClient sessionsClient;
  private SessionName sessionName;
  private String uuid = UUID.randomUUID().toString();
  Intent recive ;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chatbot);
    backButton = findViewById(R.id.ChatbotBackbutton);
    chatView = findViewById(R.id.chatView);
    editMessage = findViewById(R.id.editMessage);
    btnSend = findViewById(R.id.btnSend);
    chatAdapter = new ChatAdapter(messageList, this);
    chatView.setAdapter(chatAdapter);
    recive = getIntent();

    // Welcome message

    if (Objects.equals(recive.getStringExtra("DOCINT"), "true")){
      messageList.add(new Message("Hi, I'm the Teledoc AI chatbot.\n" +
              "I am able to  help you to navigate to the pages in the application.\n" , true));
      messageList.add(new Message("Please choose the page that you want to visit.", true));
    }
    else {
      messageList.add(new Message("Hi, I'm the Teledoc AI chatbot.\n" , true));
      messageList.add(new Message("I am able to  help you to make an appointment, purchase medicine, diagnose symptoms and to navigate to the pages in the application.\n" , true));
      messageList.add(new Message("Please choose what you want me to do.", true));
    }


    chatAdapter.notifyDataSetChanged();
    Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);

    backButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });

    btnSend.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        String message = editMessage.getText().toString();

        if (!message.isEmpty()) {
          messageList.add(new Message(message, false));
          editMessage.setText("");
          sendMessageToBot(message);
          Objects.requireNonNull(chatView.getAdapter()).notifyDataSetChanged();
          Objects.requireNonNull(chatView.getLayoutManager())
              .scrollToPosition(messageList.size() - 1);
        } else {
          Toast.makeText(ChatbotActivity.this, "Please enter text!", Toast.LENGTH_SHORT).show();
        }
      }
    });

    setUpBot();
  }

  public void setUpBot() {
    try {
      InputStream stream = this.getResources().openRawResource(R.raw.credential);
      GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
          .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
      String projectId = ((ServiceAccountCredentials) credentials).getProjectId();
      SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
      SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
          FixedCredentialsProvider.create(credentials)).build();
      sessionsClient = SessionsClient.create(sessionsSettings);
      sessionName = SessionName.of(projectId, uuid);


      Log.d(TAG, "projectId : " + projectId);
    } catch (Exception e) {
      Log.d(TAG, "setUpBot: " + e.getMessage());
    }
  }

  public void sendMessageToBot(String message) {
    QueryInput input = QueryInput.newBuilder()
        .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
    new SendMessageInBg(this, sessionName, sessionsClient, input).execute();
  }

  @Override
  public void callback(DetectIntentResponse returnResponse) {
     if(returnResponse!=null) {
       String doctor = null;

       String botReply = returnResponse.getQueryResult().getFulfillmentText();
       DialogFlowParameters(returnResponse);

       if(!botReply.isEmpty()){
         messageList.add(new Message(botReply, true));
         chatAdapter.notifyDataSetChanged();
         Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);
       }else {
         Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show();
       }
     } else {
       Toast.makeText(this, "failed to connect!", Toast.LENGTH_SHORT).show();
     }
  }

  public void DialogFlowParameters(DetectIntentResponse returnResponse){
    String doctor = null;
    String location = null;
    String drug = null;
    String quantity = null;
    String price = null;


    if((recive.getStringExtra("DOCINT")== null)){


      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-OpenTransactionHistorypage")){
        Intent intent = new Intent(this, TransactionHistory.class);
        startActivity(intent);
      }

      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-DoctorSearchpage")){
        Intent senderIntent = new Intent(this, MainActivity2.class);
        senderIntent.putExtra("Page","searchDoctor");
        startActivity(senderIntent);

      }

      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-OpenPharmacySearchpage")){
        Intent senderIntent = new Intent(this, MainActivity2.class);
        senderIntent.putExtra("Page","searchPharm");
        startActivity(senderIntent);

      }

      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-OpenPatientRecordsPage")){
        Intent senderIntent = new Intent(this, MainActivity2.class);
        senderIntent.putExtra("Page","patientRecords");
        startActivity(senderIntent);

      }

      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-PatientDetailspage")){
        Intent senderIntent = new Intent(this, MainActivity2.class);
        senderIntent.putExtra("Page","patientDetails");
        startActivity(senderIntent);
      }

      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-MedicalHistorypage")){
        Intent senderIntent = new Intent(this, MainActivity2.class);
        senderIntent.putExtra("Page","medicalHistory");
        startActivity(senderIntent);
      }

      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-OpenPriscriptsionlistpage")){
        Intent senderIntent = new Intent(this, MainActivity2.class);
        senderIntent.putExtra("Page","prescriptionsPage");
        startActivity(senderIntent);
      }

      if (returnResponse.getQueryResult().getAction().equals("AppNavigation.AppNavigation-LabReportspage")){
        Intent senderIntent = new Intent(this, MainActivity2.class);
        senderIntent.putExtra("Page","labReport");
        startActivity(senderIntent);
      }

      if(returnResponse.getQueryResult().getAction().equals("AppointmentBooking.AppointmentBooking-Makinganappointment")){
        doctor = returnResponse.getQueryResult().getParameters().getFieldsMap().get("doctor").getStringValue()+"";
        location = returnResponse.getQueryResult().getParameters().getFieldsMap().get("location").getStringValue()+"";
        if (doctor!="" & location!="" & doctor!=null & location!=null ){
          Intent senderIntent = new Intent(this, MainActivity2.class);
          senderIntent.putExtra("Page","Makinganappointment");
          senderIntent.putExtra("doc",doctor);
          senderIntent.putExtra("loc",location);
          startActivity(senderIntent);
        }
      }

      if (returnResponse.getQueryResult().getAction().equals("BuyMedicine")){
        drug = returnResponse.getQueryResult().getParameters().getFieldsMap().get("drug").getStringValue()+"";
        quantity = returnResponse.getQueryResult().getParameters().getFieldsMap().get("quantity").getStringValue()+"";
        Log.d("DialogFlow***", drug);
        Log.d("DialogFlow***", quantity);
        if (drug!="" & quantity!="" & drug!=null & quantity!=null ){
          Random ran = new Random();
          double next = ran.nextInt(46);
          double result = 500 + (next * 100);
          if (result > 5000) {
            result = 5000;
          }
          price = Double.toString(result);
          String item = drug + " " + quantity;

          Intent senderIntent = new Intent( this, PrescriptionTransaction.class);
          senderIntent.putExtra("ITEM", item);
          senderIntent.putExtra("PRICE",price);
          senderIntent.putExtra("Chatbot","true");
          startActivity(senderIntent);

        }

      }
    }
    else {
      if (returnResponse.getQueryResult().getAction().equals("OpenUpcomingAppointmentspage")){
        Intent senderIntent = new Intent(this, DoctorsActivity.class);
        senderIntent.putExtra("Page","Appointmentspage");
        startActivity(senderIntent);
      }

      if (returnResponse.getQueryResult().getAction().equals("OpenWritePrescriptionpage")){
        Intent senderIntent = new Intent(this, DoctorsActivity.class);
        senderIntent.putExtra("Page","WritePrescriptionpage");
        startActivity(senderIntent);
      }

      if (returnResponse.getQueryResult().getAction().equals("OpenListofPatientspage")){
        Intent senderIntent = new Intent(this, DoctorsActivity.class);
        senderIntent.putExtra("Page","ListofPatientspage");
        startActivity(senderIntent);
      }
      if (returnResponse.getQueryResult().getAction().equals("OpenDoctorProfilepage")){
        Intent senderIntent = new Intent(this, DoctorsActivity.class);
        senderIntent.putExtra("Page","DoctorProfilepage");
        startActivity(senderIntent);
      }
     /* if (returnResponse.getQueryResult().getAction().equals("OpenConferencingpage")){
        Intent senderIntent = new Intent(this, DoctorsActivity.class);
        senderIntent.putExtra("Page","Conferencingpage");
        startActivity(senderIntent);
      }*/
    }
  }
}


