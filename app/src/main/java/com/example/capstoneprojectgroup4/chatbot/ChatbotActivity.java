package com.example.capstoneprojectgroup4.chatbot;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.search_doctors.BookAppointmentF;
import com.example.capstoneprojectgroup4.chatbot.adapters.ChatAdapter;
import com.example.capstoneprojectgroup4.chatbot.helpers.SendMessageInBg;
import com.example.capstoneprojectgroup4.chatbot.interfaces.BotReply;
import com.example.capstoneprojectgroup4.chatbot.models.Message;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


public class ChatbotActivity extends AppCompatActivity implements BotReply {

  RecyclerView chatView;
  ChatAdapter chatAdapter;
  List<Message> messageList = new ArrayList<>();
  EditText editMessage;
  ImageButton btnSend;

  //dialogFlow
  private SessionsClient sessionsClient;
  private SessionName sessionName;
  private String uuid = UUID.randomUUID().toString();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chatbot);
    chatView = findViewById(R.id.chatView);
    editMessage = findViewById(R.id.editMessage);
    btnSend = findViewById(R.id.btnSend);

    chatAdapter = new ChatAdapter(messageList, this);
    chatView.setAdapter(chatAdapter);

    // Welcome message
    messageList.add(new Message("Hi, I'm Teledoc AI chatbot.\n" +
            "\tI can help you to make an appointment.\n" +
            "\tTell me your name?", true));
    chatAdapter.notifyDataSetChanged();
    Objects.requireNonNull(chatView.getLayoutManager()).scrollToPosition(messageList.size() - 1);

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

  private void setUpBot() {
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

  private void sendMessageToBot(String message) {
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
    String patient = null;
    String dateAndTime = null;
    String medicine = null;
    String pharmacy = null;
    String transaction = null;

    if(returnResponse.getQueryResult().getParameters().getFieldsMap().containsKey("patient"))
      patient = returnResponse.getQueryResult().getParameters().getFieldsMap().get("patient").getStringValue()+"";

    if(returnResponse.getQueryResult().getParameters().getFieldsMap().containsKey("date-time"))
      dateAndTime = returnResponse.getQueryResult().getParameters().getFieldsMap().get("date-time").getStringValue()+"";

    if(returnResponse.getQueryResult().getParameters().getFieldsMap().containsKey("doctor")){
      if(returnResponse.getQueryResult().getParameters().getFieldsMap().get("doctor").getStructValue().getFieldsMap().containsKey("name")){
        doctor = returnResponse.getQueryResult().getParameters().getFieldsMap().get("doctor").getStructValue().getFieldsMap().get("name").getStringValue();
      }
    }

    Log.d("DialogFlow***", String.format("Patient = %s\nDate and time = %s\nDoctor = %s", patient+"", dateAndTime, doctor));

    if(patient!="" & doctor!="" & dateAndTime!="" &
            patient!=null & doctor!=null & dateAndTime!=null){

      BookAppointmentF.uploadAppointmentSecond(patient, doctor, dateAndTime);
      Log.d("DialogFlow***", "Done");
    }








    if(returnResponse.getQueryResult().getParameters().getFieldsMap().containsKey("medicine"))
      medicine = returnResponse.getQueryResult().getParameters().getFieldsMap().get("medicine").getStringValue()+"";

    if(returnResponse.getQueryResult().getParameters().getFieldsMap().containsKey("date-time"))
      dateAndTime = returnResponse.getQueryResult().getParameters().getFieldsMap().get("date-time").getStringValue()+"";

    if(returnResponse.getQueryResult().getParameters().getFieldsMap().containsKey("pharmacy")){
      if(returnResponse.getQueryResult().getParameters().getFieldsMap().get("pharmacy").getStructValue().getFieldsMap().containsKey("name")){
        pharmacy = returnResponse.getQueryResult().getParameters().getFieldsMap().get("pharmacy").getStructValue().getFieldsMap().get("name").getStringValue();
      }
    }

    Log.d("DialogFlow***", String.format("medicine = %s\nDate and time = %s\npharmacy = %s", medicine+"", dateAndTime, pharmacy));

    if(medicine!="" & pharmacy!="" & dateAndTime!="" &
            medicine!=null & pharmacy!=null & dateAndTime!=null){

      BookAppointmentF.uploadAppointmentSecond(patient, doctor, dateAndTime);
      Log.d("DialogFlow***", "Done");
    }








    if(returnResponse.getQueryResult().getParameters().getFieldsMap().containsKey("transaction"))
      transaction = returnResponse.getQueryResult().getParameters().getFieldsMap().get("transaction").getStringValue()+"";




    Log.d("DialogFlow***", String.format("transaction = %s", transaction));

    if(transaction!=""  &
    transaction!=null ){

      BookAppointmentF.uploadAppointmentSecond(transaction, doctor, dateAndTime);
      Log.d("DialogFlow***", "Done");
    }
  }
}


