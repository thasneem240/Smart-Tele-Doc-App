package com.example.capstoneprojectgroup4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Map;
import java.util.UUID;

public class Activity_NoteTaking extends AppCompatActivity
{

    private EditText noteEditText;
    private TextView displayNotesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_taking);

        noteEditText = findViewById(R.id.noteEditText);
        displayNotesTextView = findViewById(R.id.displayNotesTextView);

        Button saveNoteButton = findViewById(R.id.saveNoteButton);
        Button clearNoteButton = findViewById(R.id.clearNoteButton);

        // Load and display saved notes when the activity is created
        //displaySavedNotes();

        saveNoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                // Get the text from the EditText
                String note = noteEditText.getText().toString();

                // Save the note using SharedPreferences
                saveNoteToSharedPreferences(note);

                // Clear the EditText
                noteEditText.setText("");

                // Display the saved notes
                 displaySavedNotes();
            }
        });

        clearNoteButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // Clear the EditText
                noteEditText.setText("");
            }
        });

    }

    private void saveNoteToSharedPreferences (String note)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Generate a unique key for each note (you can use a timestamp or UUID)
        String uniqueKey = UUID.randomUUID().toString();

        // Save the note with the unique key
        editor.putString(uniqueKey, note);

        // Apply the changes
        editor.apply();
    }



    private void displaySavedNotes()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("Notes", MODE_PRIVATE);
        Map<String, ?> allNotes = sharedPreferences.getAll();

        // Concatenate and display all saved notes
        StringBuilder notesText = new StringBuilder();
        for (Map.Entry<String, ?> entry : allNotes.entrySet()) {
            String note = entry.getValue().toString();
            notesText.append(note).append("\n\n");
        }

        displayNotesTextView.setText(notesText.toString());
    }



}

