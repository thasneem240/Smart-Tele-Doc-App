package com.example.capstoneprojectgroup4;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.google.firebase.storage.UploadTask;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class Frag_LabReportsTest
{

    @Mock
    private FragmentManager mockFragmentManager;
    @Mock
    private MedicalRecords mockMedicalRecordsFragment;
    @Mock
    private Context mockContext;
    @Mock
    private UploadTask.TaskSnapshot mockTaskSnapshot;
    @Mock
    private FragmentTransaction mockFragmentTransaction;
    @Mock
    private Uri mockUri;

    private Frag_LabReports fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new Frag_LabReports();
        when(mockContext.getApplicationContext()).thenReturn(mockContext);

        when(mockFragmentManager.beginTransaction()).thenReturn(mockFragmentTransaction);
        when(mockFragmentTransaction.replace(Mockito.anyInt(), Mockito.any(Frag_ListLabReports.class))).thenReturn(mockFragmentTransaction);



    }



    @Test
    public void onActivityResultTest() {
        fragment.onActivityResult(100, 0, null);
        // Add verification for the behavior when data is null
    }

}

