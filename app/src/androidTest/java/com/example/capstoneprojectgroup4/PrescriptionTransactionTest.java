package com.example.capstoneprojectgroup4;

import android.app.Activity;
import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.example.capstoneprojectgroup4.ssearch_pharmacy.PrescriptionTransaction;

import lk.payhere.androidsdk.PHConstants;
import lk.payhere.androidsdk.model.InitRequest;

public class PrescriptionTransactionTest {

    private PrescriptionTransaction prescriptionTransaction;
    private InitRequest mockRequest;
    private Activity mockActivity;
    private Intent mockIntent;

    @Before
    public void setUp() {
        prescriptionTransaction = new PrescriptionTransaction();
        mockRequest = new InitRequest();
        mockActivity = mock(Activity.class);
        mockIntent = mock(Intent.class);
    }

    @Test
    public void testTemp() {
        prescriptionTransaction.temp(mockRequest);


    }

    @Test
    public void testOnActivityResult() {
        int requestCode = 110;
        int resultCode = Activity.RESULT_OK;
        String mockData = "Mock data";
        when(mockIntent.hasExtra(PHConstants.INTENT_EXTRA_RESULT)).thenReturn(true);
        when(mockIntent.getSerializableExtra(PHConstants.INTENT_EXTRA_RESULT)).thenReturn(mockData);
        when(mockIntent.getIntExtra("requestCode", -1)).thenReturn(requestCode);
        when(mockIntent.getIntExtra("resultCode", -1)).thenReturn(resultCode);

        prescriptionTransaction.onActivityResult(requestCode, resultCode, mockIntent);


    }
}
