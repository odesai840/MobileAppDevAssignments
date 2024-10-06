package com.example.assignment08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment08.databinding.FragmentContactInfoBinding;
import com.example.assignment08.databinding.FragmentDemographicsBinding;

public class DemographicsFragment extends Fragment {
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";

    private User mUser;

    public DemographicsFragment() {
        // Required empty public constructor
    }

    public static DemographicsFragment newInstance(User user) {
        DemographicsFragment fragment = new DemographicsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentDemographicsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_PARAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDemographicsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.DoB.setText(mUser.DoB);
        binding.marital.setText(mUser.marital);
        binding.edu.setText(mUser.edu);
    }
}