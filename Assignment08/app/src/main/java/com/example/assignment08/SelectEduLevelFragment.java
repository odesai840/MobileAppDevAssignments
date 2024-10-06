// Assignment 08
// SelectEduLevelFragment.java
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment08.databinding.FragmentSelectEduLevelBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelectEduLevelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectEduLevelFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SelectEduLevelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectEduLevelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectEduLevelFragment newInstance(String param1, String param2) {
        SelectEduLevelFragment fragment = new SelectEduLevelFragment();
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

    FragmentSelectEduLevelBinding binding;
    String edu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectEduLevelBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for(String edu : Data.educationLevels){
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setText(edu);
            binding.eduRadioGroup.addView(radioButton);
        }

        binding.eduRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = group.findViewById(checkedId);
                if (selectedRadioButton != null) {
                    edu = selectedRadioButton.getText().toString();
                }
            }
        });

        binding.cancelEduButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelEduSelection();
            }
        });

        binding.selectEduButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edu == null){
                    Toast.makeText(getActivity(),"Edu level must be selected",Toast.LENGTH_SHORT).show();
                } else {
                    mListener.sendSelectedEdu(edu);
                }
            }
        });
    }

    EduSelectionListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (EduSelectionListener) context;
    }

    public interface EduSelectionListener {
        void cancelEduSelection();
        void sendSelectedEdu(String edu);
    }
}