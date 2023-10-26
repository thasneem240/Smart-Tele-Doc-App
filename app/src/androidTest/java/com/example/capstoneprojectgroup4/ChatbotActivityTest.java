package com.example.capstoneprojectgroup4;

import android.content.Intent;

import com.example.capstoneprojectgroup4.chatbot.ChatbotActivity;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryResult;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ChatbotActivityTest {

    @Mock
    private ChatbotActivity chatbotActivity;

    @Before
    public void setUp() {
        chatbotActivity = mock(ChatbotActivity.class);
    }

    @Test
    public void testSetUpBot() {
        chatbotActivity.setUpBot();

        // Add assertions as necessary
    }

    @Test
    public void testSendMessageToBot() {
        String message = "Test message";
        chatbotActivity.sendMessageToBot(message);


    }

    @Test
    public void testCallback() {
        // Mock the DetectIntentResponse
        DetectIntentResponse mockResponse = mock(DetectIntentResponse.class);
        QueryResult queryResult = mock(QueryResult.class);
        when(mockResponse.getQueryResult()).thenReturn(queryResult);
        when(queryResult.getFulfillmentText()).thenReturn("Mock fulfillment text");

        // Call the method
        chatbotActivity.callback(mockResponse);

        // Verify that the appropriate method was called
        verify(chatbotActivity).callback(mockResponse);
    }

    @Test
    public void testDialogFlowParameters() {
        // Mock the DetectIntentResponse
        DetectIntentResponse mockResponse = mock(DetectIntentResponse.class);
        QueryResult queryResult = mock(QueryResult.class);
        when(mockResponse.getQueryResult()).thenReturn(queryResult);
        when(queryResult.getAction()).thenReturn("Mock action");

        // Mock the Intent
        Intent mockIntent = mock(Intent.class);
        when(mockIntent.getStringExtra("DOCINT")).thenReturn("true");

        // Call the method
        chatbotActivity.DialogFlowParameters(mockResponse);

        // Verify that the appropriate method was called
        verify(chatbotActivity).DialogFlowParameters(mockResponse);
    }
}
