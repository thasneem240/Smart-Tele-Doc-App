package com.example.capstoneprojectgroup4;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsAdapter;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsFragment;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;
import com.example.capstoneprojectgroup4.ssearch_pharmacy.PharmaciesF;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListOfPrescriptionsFragmentTest {

    private ListOfPrescriptionsFragment fragment;
    @Mock
    private RecyclerView recyclerView;

    @Mock
    private ListOfPrescriptionsAdapter adapter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        fragment = new ListOfPrescriptionsFragment();
    }

    @Test
    public void testListOfPrescriptionsLoadedSuccessfully() {
        FirebaseDatabase mockDatabase = mock(FirebaseDatabase.class);
        DatabaseReference mockReference = mock(DatabaseReference.class);

        List<PrescriptionObject> mockPrescriptions = new ArrayList<>();

        DataSnapshot mockDataSnapshot = mock(DataSnapshot.class);

        when(mockDatabase.getReference("Prescriptions")).thenReturn(mockReference);
        when(mockReference.child(any(String.class))).thenReturn(mockReference);
        when(mockReference.child("Prescriptions")).thenReturn(mockReference);



        MainActivity.setFirebaseDatabase(mockDatabase);
        when(fragment.getActivity()).thenReturn(mock(MainActivity.class));
        when(fragment.getView()).thenReturn(mock(View.class));
        when(fragment.getActivity().getSupportFragmentManager()).thenReturn(mock(FragmentManager.class));
        when(fragment.getActivity().getSupportFragmentManager().beginTransaction()).thenReturn(mock(FragmentTransaction.class));

        when(recyclerView.getAdapter()).thenReturn(adapter);
        when(fragment.getView().findViewById(R.id.RecyclerView_ListOfPatients)).thenReturn(recyclerView);

        fragment.onCreateView(null, null, null);

        verify(recyclerView).setAdapter(adapter);
        verify(recyclerView).setLayoutManager(any(RecyclerView.LayoutManager.class));
    }
}
