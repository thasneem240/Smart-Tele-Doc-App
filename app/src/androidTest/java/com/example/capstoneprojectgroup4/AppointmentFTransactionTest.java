package com.example.capstoneprojectgroup4;

import android.app.Activity;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.capstoneprojectgroup4.search_doctors.BookAppointmentF;

import lk.payhere.androidsdk.PHResponse;
import lk.payhere.androidsdk.model.InitRequest;
import lk.payhere.androidsdk.model.StatusResponse;

public class AppointmentFTransactionTest {

    private static final String MOCK_RESPONSE_DATA = "Mock Response Data";

    @Mock
    private Intent mockIntent;

    private BookAppointmentF bookAppointmentF;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        bookAppointmentF = new BookAppointmentF();
    }

    @Test
    public void testTemp() {
        // Prepare mock data
        when(mockIntent.getSerializableExtra(any(String.class))).thenReturn(MOCK_RESPONSE_DATA);
        InitRequest req = new InitRequest();
        req.setMerchantId("MTczNTk3NTUzNDEzMjE5ODgyNTAzNzk2MzAxNTgzMzEwNTk2NTgw");       // Merchant ID
        req.setCurrency("LKR");             // Currency code LKR/USD/GBP/EUR/AUD
        req.setAmount(1000.00);             // Final Amount to be charged
        req.setOrderId("230000123");        // Unique Reference ID
        req.setItemsDescription("Door bell wireless");  // Item description title
        req.setCustom1("This is the custom message 1");
        req.setCustom2("This is the custom message 2");
        req.getCustomer().setFirstName("Saman");
        req.getCustomer().setLastName("Perera");
        req.getCustomer().setEmail("samanp@gmail.com");
        req.getCustomer().setPhone("+94771234567");
        req.getCustomer().getAddress().setAddress("No.1, Galle Road");
        req.getCustomer().getAddress().setCity("Colombo");
        req.getCustomer().getAddress().setCountry("Sri Lanka");
        // Call the method
        bookAppointmentF.temp(req);

        // Verify that the correct method is called
        verify(mockIntent).getSerializableExtra(any(String.class));

    }

    @Test
    public void testOnActivityResultWithSuccess() {
        // Prepare mock data
        PHResponse<StatusResponse> mockResponse = mock(PHResponse.class);
        when(mockResponse.isSuccess()).thenReturn(true);
        when(mockIntent.getSerializableExtra(any(String.class))).thenReturn(mockResponse);

        // Call the method
        bookAppointmentF.onActivityResult(BookAppointmentF.PAYHERE_REQUEST, Activity.RESULT_OK, mockIntent);

        // Verify that the correct methods are called and appropriate data is updated
        verify(mockIntent).getSerializableExtra(any(String.class));

    }

    @Test
    public void testOnActivityResultWithFailure() {
        // Prepare mock data
        PHResponse<StatusResponse> mockResponse = mock(PHResponse.class);
        when(mockResponse.isSuccess()).thenReturn(false);
        when(mockIntent.getSerializableExtra(any(String.class))).thenReturn(mockResponse);

        // Call the method
        bookAppointmentF.onActivityResult(BookAppointmentF.PAYHERE_REQUEST, Activity.RESULT_OK, mockIntent);

        // Verify that the correct methods are called and appropriate messages are displayed
        verify(mockIntent).getSerializableExtra(any(String.class));

    }

    @Test
    public void testOnActivityResultCanceled() {
        // Prepare mock data
        when(mockIntent.getSerializableExtra(any(String.class))).thenReturn(null);

        // Call the method
        bookAppointmentF.onActivityResult(BookAppointmentF.PAYHERE_REQUEST, Activity.RESULT_CANCELED, mockIntent);

        // Verify that the correct methods are called and appropriate messages are displayed
        verify(mockIntent).getSerializableExtra(any(String.class));

    }
}
