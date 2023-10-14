package com.example.capstoneprojectgroup4;

import android.content.Intent;


import com.example.capstoneprojectgroup4.interface_of_doctors.DoctorViewAppointments;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;



import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;



public class VideoConferenceTest
{

    @Mock
    private Intent mockIntent;
    @Mock
    private Activity_Agora_VideoConference mockActivity;
    @Mock
    private ViewAppointments mockViewAppointments;
    @Mock
    private DoctorViewAppointments mockDoctorViewAppointments;

    @Spy
    @InjectMocks
    private Activity_Agora_VideoConference spyActivity;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        when(mockActivity.getIntent()).thenReturn(mockIntent);
        when(mockIntent.getStringExtra("userType")).thenReturn("Patient");

        doNothing().when(mockActivity).onBackPressed();

    }

    @Test
    public void onCreateTest() {
        spyActivity.onCreate(null);

        verify(spyActivity).checkSelfPermission();
        verify(spyActivity).setupVideoSDKEngine();
    }

    @Test
    public void onBackPressedTest() {
        spyActivity.onBackPressed();

        // Verify if the correct FragmentTransaction methods are called based on userType
        verify(mockActivity).getSupportFragmentManager();


        // Reset mockIntent
        when(mockIntent.getStringExtra("userType")).thenReturn("Doctor");

        spyActivity.onBackPressed();

        // Verify if the correct FragmentTransaction methods are called based on userType
        verify(mockActivity).getSupportFragmentManager();

    }
}

