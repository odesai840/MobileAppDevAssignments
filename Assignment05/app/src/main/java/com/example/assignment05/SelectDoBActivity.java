package com.example.assignment05;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class SelectDoBActivity extends AppCompatActivity {

    final static public String DATE_KEY = "selectedDate";

    CalendarView calendarView;
    Button cancelButton;
    Button submitButton;
    String selectedDate;
    TextView calendarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_do_bactivity);

        calendarView = findViewById(R.id.calendarView);
        cancelButton = findViewById(R.id.cancelButton);
        submitButton = findViewById(R.id.submitButton2);
        calendarTitle = findViewById(R.id.CalendarTitle);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);
        calendarView.setMaxDate(calendar.getTimeInMillis());

        long selectedDateInMillis = calendarView.getDate();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTimeInMillis(selectedDateInMillis);
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH);
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        selectedDate = (month + 1) + "/" + day + "/" + year;
        calendarTitle.setText(selectedDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                selectedDate = (month + 1) + "/" + day + "/" + year;
                calendarTitle.setText(selectedDate);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra(DATE_KEY, selectedDate);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}