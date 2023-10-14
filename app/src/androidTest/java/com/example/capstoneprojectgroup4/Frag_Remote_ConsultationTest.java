package com.example.capstoneprojectgroup4;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.capstoneprojectgroup4.search_doctors.AppointmentItem;
import com.example.capstoneprojectgroup4.search_doctors.ViewAppointments;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.example.capstoneprojectgroup4.home.MainActivity;






public class Frag_Remote_ConsultationTest
{

    @Mock
    private View mockView;
    @Mock
    private EditText mockPatientNameEditText;
    @Mock
    private EditText mockIssueEditText;
    @Mock
    private Button mockVideoConferenceButton;
    @Mock
    private FragmentManager mockFragmentManager;
    @Mock
    private FragmentTransaction mockFragmentTransaction;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private DatabaseReference mockDatabaseReference;
    @Mock
    private Intent mockIntent;

    @InjectMocks
    private MainActivity mockMainActivity;

    private Frag_Remote_Consultation fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);


        when(mockMainActivity.getPatientObject().getFirstName()).thenReturn("Test Patient");

        fragment = new Frag_Remote_Consultation();



        AppointmentItem mockAppointmentItem = Mockito.mock(AppointmentItem.class);
        when(mockAppointmentItem.getAppointmentType()).thenReturn("Video");


    }

    @Test
    public void onCreateViewTest() {
        when(mockVideoConferenceButton.getText()).thenReturn("Start Video Conference");

        fragment.onCreateView(Mockito.mock(LayoutInflater.class), Mockito.mock(ViewGroup.class), null);

        verify(mockPatientNameEditText).setText("Test Patient");
        verify(mockVideoConferenceButton).setOnClickListener(Mockito.any(View.OnClickListener.class));
    }

    @Test
    public void backButtonRemoteConsClickTest() {
        View view = Mockito.mock(View.class);
        when(view.getId()).thenReturn(1);
        when(mockFragmentManager.beginTransaction()).thenReturn(mockFragmentTransaction);


        verify(mockFragmentManager).beginTransaction();
        verify(mockFragmentTransaction).replace(Mockito.anyInt(), Mockito.any(ViewAppointments.class));
    }

    @Test
    public void startVideoConferenceTest() {
        fragment.startVideoConference();

        verify(mockMainActivity).startActivity(mockIntent);
    }

    @Test
    public void startAudioConferenceTest() {
        fragment.startAudioConference();

        verify(mockMainActivity).startActivity(mockIntent);
    }

    @Test
    public void saveToRealTimeDataBaseTest() {
        MedicalHistoryItem mockMedicalHistoryItem = Mockito.mock(MedicalHistoryItem.class);

        when(mockIssueEditText.getText().toString()).thenReturn("Test Issue");

        when(mockFirebaseDatabase.getInstance()).thenReturn(mockFirebaseDatabase);
        when(mockFirebaseDatabase.getReference("Patient's Medical History")).thenReturn(mockDatabaseReference);
        when(mockDatabaseReference.child(Mockito.anyString())).thenReturn(mockDatabaseReference);
        when(mockDatabaseReference.child("medicalRecords")).thenReturn(mockDatabaseReference);

        fragment.saveToRealTimeDataBase("Test Issue");

        verify(mockDatabaseReference).push();
    }
}

