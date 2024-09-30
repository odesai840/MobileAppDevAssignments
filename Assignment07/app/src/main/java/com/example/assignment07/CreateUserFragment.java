// Assignment 07
// CreateUserFragment.java
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
import android.widget.Toast;

import com.example.assignment07.databinding.FragmentCreateUserBinding;
import com.example.assignment07.databinding.FragmentMainBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateUserFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";



    // TODO: Rename and change types of parameters
    private User mUser;

    public CreateUserFragment() {
        // Required empty public constructor
    }

    FragmentCreateUserBinding binding;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment CreateUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateUserFragment newInstance(User user) {
        CreateUserFragment fragment = new CreateUserFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(ARG_PARAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    CreateUserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateUserFragmentListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.countryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private User createUserWithInfo(){
        String userName = binding.enterName.getText().toString();
        String userEmail = binding.enterEmail.getText().toString();
        String userCountry = binding.countrySelected.getText().toString();
        String userDoB = binding.DoBSelected.getText().toString();
        String age;


        if (userName.isEmpty()) {
            Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (userEmail.isEmpty()) {
            Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        try {
            Double.valueOf(binding.enterAge.getText().toString());
            age = binding.enterAge.getText().toString();
            if (age.isEmpty()){
                Toast.makeText(getActivity(),"Age is required",Toast.LENGTH_SHORT).show();
                return null;
            }
        } catch (NumberFormatException e){
            Toast.makeText(getActivity(), "Age must be a number", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (userCountry.equals("N/A")) {
            Toast.makeText(getActivity(),"Country is required",Toast.LENGTH_SHORT).show();
            return null;
        }
        if (userDoB.equals("N/A")) {
            Toast.makeText(getActivity(),"Date of Birth is required",Toast.LENGTH_SHORT).show();
            return null;
        }

        return new User(userName, userEmail, age, userCountry, userDoB);
    }



    public interface CreateUserFragmentListener {
        void gotoProfile(User user);
        void gotoCountry(User user);
        void gotoDoB(User user);
    }

}