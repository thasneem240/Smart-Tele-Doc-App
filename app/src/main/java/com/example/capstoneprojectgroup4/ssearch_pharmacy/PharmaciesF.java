package com.example.capstoneprojectgroup4.ssearch_pharmacy;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.best_price.listOf_prescriptions.ListOfPrescriptionsFragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PharmaciesF#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PharmaciesF extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public FirebaseDatabase firebaseDatabase;


    Button searchButton;
    Toolbar toolbar;
    Button orderButton;
    private LocationManager locationManager;
    private LocationListener locationListener;
    Button bestPrice;
    public static final String TAG = "PharmaciesF"; // Add this TAG field



    RadioGroup radioGroup;
    public TextView etPharmName ;
    public int searchType=-1;

    public TextView etPharmLocation;
    TextView etPharmDrugs ;
    RecyclerView recyclerView;


    Button search;
    public PharmacyAdapter pharmacyAdapter;
    private SearchView searchView;
    public FragmentManager fm;


    public PharmaciesF() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PharmciesF.
     */
    // TODO: Rename and change types and number of parameters
    public static PharmaciesF newInstance(String param1, String param2) {
        PharmaciesF fragment = new PharmaciesF();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        firebaseDatabase = FirebaseDatabase.getInstance();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacies, container, false);
        TextView find = view.findViewById(R.id.FindNearby);
         locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

         locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // Handle location updates here
                // Get the user's current latitude and longitude
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Create a URI for launching Google Maps with nearby pharmacies
                Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=pharmacy&radius=1000");

                // Create an Intent to open Google Maps
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used
                startActivity(mapIntent);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check for location permission
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, request current location
                    fusedLocationProviderClient.getLastLocation()
                            .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                                @Override
                                public void onSuccess(Location location) {
                                    if (location != null) {
                                        // Get the user's current latitude and longitude
                                        double latitude = location.getLatitude();
                                        double longitude = location.getLongitude();

                                        // Create a URI for launching Google Maps with nearby pharmacies
                                        Uri gmmIntentUri = Uri.parse("geo:" + latitude + "," + longitude + "?q=pharmacy&radius=1000");

                                        // Create an Intent to open Google Maps
                                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                                        mapIntent.setPackage("com.google.android.apps.maps"); // Ensure Google Maps is used
                                        startActivity(mapIntent);
                                    }
                                }
                            });
                } else {
                    // Permission not granted, request it
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                }
            }
        });



        etPharmName= view.findViewById(R.id.searchPharmName);
        etPharmLocation = view.findViewById(R.id.searchPharmLoc);
       // etPharmDrugs = view.findViewById(R.id.searchDrugs);
        recyclerView = view.findViewById(R.id.pharmrv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ImageView backButton = view.findViewById(R.id.backButtonPharma);
        bestPrice = view.findViewById(R.id.Button_ViewPrescriptions);

        bestPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                ListOfPrescriptionsFragment listOfPrescriptionsFragment = new ListOfPrescriptionsFragment();
                fm.beginTransaction().replace(R.id.fragmentContainerView, listOfPrescriptionsFragment).commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                MainMenu searchDoctors = new MainMenu();
                fm.beginTransaction().replace(R.id.fragmentContainerView, searchDoctors).commit();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        search = view.findViewById(R.id.pharmsearchButton);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSearch();
            }
        });

        return view;
    }

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    // Handle the permission request result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, you can now request location updates
                try {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            } else {
                // Permission denied, handle it as needed (e.g., show a message)
                Toast.makeText(getActivity(), "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String removeWhitespaceAndToLower(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("\\s", "").toLowerCase();
    }

    public void performSearch() {
        String nameEd = etPharmName.getText().toString().trim();
        String locationEd = etPharmLocation.getText().toString().trim();

        Query query = FirebaseDatabase.getInstance().getReference().child("Pharmacies");

        if (!nameEd.isEmpty() && locationEd.isEmpty())
        {
            Log.d(TAG, "Search by Name: " + nameEd);
            searchType = 0;
        }else {
            Log.d(TAG, "Search by Location: " + locationEd);
            searchType = 1;
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Pharmacy> pharmacies = new ArrayList<>();
                for (DataSnapshot doctorSnapshot : snapshot.getChildren()) {
                    String name = (String) doctorSnapshot.child("Name").getValue();
                    String location = (String) doctorSnapshot.child("Address").getValue();
                    String phoneNum = (String) doctorSnapshot.child("PhoneNumber").getValue();
                    String maps = (String) doctorSnapshot.child("Maps").getValue();

                    if (searchType == 0 && name != null && removeWhitespaceAndToLower(name).startsWith(removeWhitespaceAndToLower(nameEd))) {
                        com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy doctor = new com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy(name, location, phoneNum, maps);
                        pharmacies.add(doctor);
                    } else if (searchType == 1 && location != null && removeWhitespaceAndToLower(location).startsWith(removeWhitespaceAndToLower(locationEd))) {
                        com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy doctor = new com.example.capstoneprojectgroup4.ssearch_pharmacy.Pharmacy(name, location, phoneNum, maps);
                        pharmacies.add(doctor);

                    }
                }
                PharmacyAdapter pharmacyAdapter = new PharmacyAdapter(getContext(),pharmacies);
                recyclerView.setAdapter(pharmacyAdapter);
                pharmacyAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setEtPharmName(TextView textView) {
        this.etPharmName = textView;
    }

    public void setEtPharmLocation(TextView textView) {
        this.etPharmLocation = textView;
    }
}