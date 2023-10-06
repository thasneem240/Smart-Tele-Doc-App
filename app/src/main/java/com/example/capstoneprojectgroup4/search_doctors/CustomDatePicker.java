package com.example.capstoneprojectgroup4.search_doctors;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import java.util.Calendar;

public class CustomDatePicker extends DatePickerDialog {

    private Calendar minDateCalendar;

    public CustomDatePicker(Context context, OnDateSetListener listener, int year, int month, int day) {
        super(context, listener, year, month, day);

        // Initialize minDateCalendar with the current local date
        minDateCalendar = Calendar.getInstance();
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day) {
        // Create a Calendar object for the selected date
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, day);

        // Check if the selected date is before the current local date
        if (selectedDate.before(minDateCalendar)) {
            // Set the selected date to the minimum date (current local date)
            view.updateDate(minDateCalendar.get(Calendar.YEAR), minDateCalendar.get(Calendar.MONTH), minDateCalendar.get(Calendar.DAY_OF_MONTH));
        }
    }
}
