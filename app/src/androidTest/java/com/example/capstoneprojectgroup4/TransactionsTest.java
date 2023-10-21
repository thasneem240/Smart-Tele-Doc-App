package com.example.capstoneprojectgroup4;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.net.Uri;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.front_end.MedicalRecords;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

public class TransactionsTest {
  /*  @Mock
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
    @Mock
    private FirebaseStorage mockFirebaseStorage;
    @Mock
    private StorageReference mockStorageReference;
    @Mock
    private StorageReference mockItemReference;

    @Mock
    private RecyclerView mockRecyclerView;

    @Mock
    private RecyclerView.ViewHolder mockViewHolder;

    private Frag_ListLabReports fragment;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new Frag_ListLabReports();
        when(mockContext.getApplicationContext()).thenReturn(mockContext);

        when(mockContext.getSystemService(Mockito.anyString())).thenReturn(mockFirebaseStorage);
        when(mockFirebaseStorage.getReference()).thenReturn(mockStorageReference);

        when(mockStorageReference.child(Mockito.anyString())).thenReturn(mockItemReference);

        when(mockFragmentTransaction.replace(Mockito.anyInt(), Mockito.any(Frag_ListLabReports.class))).thenReturn(mockFragmentTransaction);
    }



    @Test
    public void enableReportsRecyclerViewTest() {
        List<LabReport> labReports = new ArrayList<>();
        labReports.add(new LabReport("Title", mockUri));

        fragment.enableReportsRecyclerView(labReports, mockRecyclerView);

        verify(mockRecyclerView).setLayoutManager(Mockito.any(LinearLayoutManager.class));
    }

    @Test
    public void retrieveLabReportsTest() {
        fragment.retrieveLabReports(new ArrayList<>());

        verify(mockStorageReference).listAll();
        verify(mockItemReference).getDownloadUrl();
    }

    @Test
    public void LabReportAdapterTest() {
        List<LabReport> labReports = new ArrayList<>();
        labReports.add(new LabReport("Title", mockUri));

    }*/
}
