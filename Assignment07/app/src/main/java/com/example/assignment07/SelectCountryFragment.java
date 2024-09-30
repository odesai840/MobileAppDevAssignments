// Assignment 07
// SelectCountryFragment.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment07;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.assignment07.databinding.FragmentSelectCountryBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectCountryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectCountryFragment extends Fragment {
    String country;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentSelectCountryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSelectCountryBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TO DO figure our how to select radio group
        //binding.countryRadioGroup.add
        country = "XYZ";

        binding.cancelCountryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelCountrySelection();
            }
        });

        binding.selectCountryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (country == null){
                    Toast.makeText(getActivity(),"Country must be selected",Toast.LENGTH_SHORT).show();
                } else {
                    mListener.sendSelectedCountry(country);
                }
            }
        });

    }


    SelectCountryFragment.CountrySelectionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCountryFragment.CountrySelectionListener) context;
    }

    public interface CountrySelectionListener {
        void cancelCountrySelection();
        void sendSelectedCountry(String country);
    }
}