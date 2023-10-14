package com.example.capstoneprojectgroup4;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorsActivity;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.other.DoctorMedicalRecords;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;

import com.google.firebase.database.DatabaseReference;


public class Frag_MedicalHistoryTest {

    @Mock
    private FragmentManager mockFragmentManager;
    @Mock
    private MedicalRecords mockMedicalRecordsFragment;
    @Mock
    private DoctorsActivity mockDoctorsActivity;
    @Mock
    private DoctorMedicalRecords mockDoctorMedicalRecordsFragment;
    @Mock
    private Context mockContext;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private DatabaseReference mockDatabaseReference;

    private Frag_MedicalHistory fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new Frag_MedicalHistory();
        when(mockFirebaseDatabase.getReference(any(String.class))).thenReturn(mockDatabaseReference);
        when(mockDatabaseReference.child(any(String.class))).thenReturn(mockDatabaseReference);
        when(mockDoctorsActivity.getSupportFragmentManager()).thenReturn(mockFragmentManager);
        when(mockFragmentManager.beginTransaction()).thenReturn(Mockito.mock(androidx.fragment.app.FragmentTransaction.class));
        when(mockDoctorsActivity.getResources()).thenReturn(Mockito.mock(android.content.res.Resources.class));

    }
}






