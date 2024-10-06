package com.example.assignment08;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment08.databinding.FragmentSelectMaritalStatusBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectMaritalStatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectMaritalStatusFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectMaritalStatusFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectMaritalStatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectMaritalStatusFragment newInstance(String param1, String param2) {
        SelectMaritalStatusFragment fragment = new SelectMaritalStatusFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentSelectMaritalStatusBinding binding;
    String marital;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectMaritalStatusBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for(String marital : Data.maritalStatus){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(marital);
            binding.maritalRadioGroup.addView(radioButton);
        }

        binding.maritalRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = group.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    marital = selectedRadioButton.getText().toString();
                }
            }
        });

        binding.cancelMaritalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelMaritalSelection();
            }
        });

        binding.selectMaritalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (marital == null){
                    Toast.makeText(getActivity(),"Edu level must be selected",Toast.LENGTH_SHORT).show();
                } else {
                    mListener.sendSelectedMarital(marital);
                }
            }
        });
    }

    MaritalSelectionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (MaritalSelectionListener) context;
    }

    public interface MaritalSelectionListener {
        void cancelMaritalSelection();
        void sendSelectedMarital(String marital);
    }
}