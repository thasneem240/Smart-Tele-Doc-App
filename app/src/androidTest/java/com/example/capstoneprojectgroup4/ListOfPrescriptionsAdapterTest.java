package com.example.capstoneprojectgroup4;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.example.capstoneprojectgroup4.best_price.PrescriptionDrugObject;
import com.example.capstoneprojectgroup4.best_price.edit_howMuch.EditHowMuchFragment;
import com.example.capstoneprojectgroup4.interface_of_doctors.writing_prescriptions.other.PrescriptionObject;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsAdapter;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ListOfPrescriptionsAdapterTest {

    @Mock
    private ListOfPrescriptionsViewHolder viewHolder;

    @Mock
    private View view;

    @Mock
    private Activity activity;

    @Mock
    private FragmentManager fragmentManager;

    @Mock
    private FragmentTransaction fragmentTransaction;

    @Mock
    private Toast toast;

    private ListOfPrescriptionsAdapter adapter;
    private ArrayList<PrescriptionObject> prescriptionList;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        prescriptionList = new ArrayList<>();

        adapter = new ListOfPrescriptionsAdapter(prescriptionList);
    }

    @Test
    public void testAdapterBindViewHolder() {
        int position = 0;
        PrescriptionObject prescription = new PrescriptionObject(/* Initialize with data */);
        prescriptionList.add(prescription);

        when(view.getContext()).thenReturn(activity);
        when(activity.getFragmentManager()).thenReturn(fragmentManager);
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);

        adapter.onBindViewHolder(viewHolder, position);

        verify(view).setVisibility(View.INVISIBLE);


        when(prescription.isManuallyWrittenDrugs()).thenReturn(false);
        when(prescription.getSelectedDrugs()).thenReturn(new ArrayList<PrescriptionDrugObject>());

        adapter.onBindViewHolder(viewHolder, position);

        verify(view).setOnClickListener(Mockito.any(View.OnClickListener.class));
    }
}
