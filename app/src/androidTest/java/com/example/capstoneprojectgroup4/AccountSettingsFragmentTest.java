package com.example.capstoneprojectgroup4;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.PickerActions;
//import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.capstoneprojectgroup4.R;
import com.example.capstoneprojectgroup4.front_end.MainActivity2;
import com.example.capstoneprojectgroup4.front_end.MainMenu;
import com.example.capstoneprojectgroup4.home.MainActivity;
import com.example.capstoneprojectgroup4.patient_authentication.AccountSettings;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@LargeTest
public class AccountSettingsFragmentTest {
    @Mock
    private AccountSettings accountSettingsFragment;
    @Mock
    private FragmentManager fragmentManager;
    @Mock
    private FragmentTransaction fragmentTransaction;

    private FirebaseDatabase mockDatabase;
    private DatabaseReference mockReference;
    private FirebaseAuth mockAuth;
    private FirebaseUser mockUser;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockDatabase = mock(FirebaseDatabase.class);
        mockReference = mock(DatabaseReference.class);
        mockAuth = mock(FirebaseAuth.class);
        mockUser = mock(FirebaseUser.class);
        when(mockUser.getUid()).thenReturn("test_user_uid");

        when(accountSettingsFragment.getContext()).thenReturn(InstrumentationRegistry.getInstrumentation().getContext());
        when(accountSettingsFragment.getView()).thenReturn(mock(View.class));
        when(fragmentManager.beginTransaction()).thenReturn(fragmentTransaction);
        when(mockDatabase.getReference(any(String.class))).thenReturn(mockReference);
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);

        MainActivity.setFirebaseDatabase(mockDatabase);

        Application app = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        SharedPreferences sharedPreferences = app.getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("profileImageResource", R.drawable.female_avatar);
        editor.apply();
    }

    @Test
    public void testAccountSettingsFragment() {

        onView(withId(R.id.EditText_Email)).perform(typeText("test@example.com"));
        onView(withId(R.id.EditText_DoctorName)).perform(typeText("John"));
        onView(withId(R.id.EditText_LastName)).perform(typeText("Doe"));
        onView(withId(R.id.EditText_Nic)).perform(typeText("123456789V"));

        onView(withId(R.id.EditText_Dob)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(1990, 5, 15));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.Spinner_Gender)).perform(click());
        onView(withText("Male")).perform(click());

        onView(withId(R.id.EditText_MobileNumber)).perform(typeText("123456789"));
        onView(withId(R.id.EditText_Height)).perform(typeText("175"));
        onView(withId(R.id.EditText_Weight)).perform(typeText("70"));
        onView(withId(R.id.EditText_Country)).perform(typeText("Sri Lanka"));
        onView(withId(R.id.EditText_City)).perform(typeText("Colombo"));
        onView(withId(R.id.EditText_Address)).perform(typeText("123 Main St."));

        onView(withId(R.id.Button_Update)).perform(click());

        onView(withId(R.id.Button_Logout)).perform(click());

        Mockito.verify(mockDatabase).getReference("Users");
        Mockito.verify(mockAuth).signOut();
    }
}
