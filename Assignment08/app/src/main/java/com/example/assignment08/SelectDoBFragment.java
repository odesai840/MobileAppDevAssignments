// Assignment 08
// SelectDoBFragment.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.assignment08.databinding.FragmentSelectDoBBinding;

import java.util.Calendar;

public class SelectDoBFragment extends Fragment {

    String selectedDate;

    public SelectDoBFragment() {
        // Required empty public constructor
    }

    FragmentSelectDoBBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectDoBBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -18);

        binding.calendarView.setMaxDate(calendar.getTimeInMillis());

        long selectedDateInMillis = binding.calendarView.getDate();
        Calendar selectedCalendar = Calendar.getInstance();
        selectedCalendar.setTimeInMillis(selectedDateInMillis);
        int year = selectedCalendar.get(Calendar.YEAR);
        int month = selectedCalendar.get(Calendar.MONTH);
        int day = selectedCalendar.get(Calendar.DAY_OF_MONTH);
        selectedDate = (month + 1) + "/" + day + "/" + year;
        binding.CalendarTitle.setText(selectedDate);

        binding.calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                selectedDate = (month + 1) + "/" + day + "/" + year;
                binding.CalendarTitle.setText(selectedDate);
            }
        });

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelDoBSelection();
            }
        });

        binding.submitButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDate != null) {
                    mListener.sendSelectedDoB(selectedDate);
                } else {
                    Toast.makeText(getActivity(),"You must select a date!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    DoBSelectionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (DoBSelectionListener) context;
    }

    public interface DoBSelectionListener {
        void cancelDoBSelection();
        void sendSelectedDoB(String DoB);
    }
}