package com.example.capstoneprojectgroup4;

import android.content.Intent;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

import com.example.capstoneprojectgroup4.transaction.TransactionHistory;

@RunWith(AndroidJUnit4.class)
public class TransactionHistoryTest {

    private TransactionHistory transactionHistory;

    @Before
    public void setUp() {
        ActivityScenario<TransactionHistory> activityScenario = ActivityScenario.launch(TransactionHistory.class);
        activityScenario.onActivity(activity -> transactionHistory = activity);
    }

    @Test
    public void testPreconditions() {
        assertNotNull("transactionHistory is null", transactionHistory);

    }

    @Test
    public void testBackButtonOnClick() {
        Button backButton = transactionHistory.findViewById(R.id.backButtonTransHistory);
        assertNotNull("backButton is null", backButton);
        backButton.performClick();

    }

    @Test
    public void testHomePageButtonOnClick() {
        Button homePageButton = transactionHistory.findViewById(R.id.homePageButton);
        assertNotNull("homePageButton is null", homePageButton);
        homePageButton.performClick();

    }

    @Test
    public void testChatBotButtonOnClick() {
        Button chatBotButton = transactionHistory.findViewById(R.id.chatBotButton);
        assertNotNull("chatBotButton is null", chatBotButton);
        chatBotButton.performClick();

    }

    @Test
    public void testAppointmentsButtonOnClick() {
        Button appointmentsButton = transactionHistory.findViewById(R.id.appointmentButton);
        assertNotNull("appointmentsButton is null", appointmentsButton);
        appointmentsButton.performClick();

    }

    @Test
    public void testUserProfileButtonOnClick() {
        Button userProfileButton = transactionHistory.findViewById(R.id.userProfileButton);
        assertNotNull("userProfileButton is null", userProfileButton);
        userProfileButton.performClick();

    }


}
