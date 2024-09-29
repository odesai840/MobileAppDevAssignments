// Assignment 06
// EditUserFragment.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment06;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.assignment06.databinding.FragmentEditUserBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditUserFragment extends Fragment {

    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";

    private User mUser;

    public EditUserFragment() {
        // Required empty public constructor
    }

    public static EditUserFragment newInstance(User user) {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_PARAM_USER);
        }
    }

    FragmentEditUserBinding binding;
    String selectedRole;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.editTextName.setText(mUser.name);
        binding.editTextEmail.setText(mUser.email);
        selectedRole = mUser.role;
        if (selectedRole.equals("Student")){
            binding.radioGroupRoles.check(R.id.studentRadio);
        } else if(selectedRole.equals("Employee")){
            binding.radioGroupRoles.check(R.id.employeeRadio);
        } else if (selectedRole.equals("Other")) {
            binding.radioGroupRoles.check(R.id.otherRadio);
        }

        binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.cancelEditUser();
            }
        });

        binding.submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User updatedUser = createUserWithInfo();
                if(updatedUser != null){
                    mListener.submitToProfile(updatedUser);
                }
            }
        });

        binding.radioGroupRoles.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.studentRadio) {
                    selectedRole = "Student";
                } else if (i == R.id.employeeRadio) {
                    selectedRole = "Employee";
                } else if (i == R.id.otherRadio) {
                    selectedRole = "Other";
                }
            }
        });
    }

    EditUserFragmentListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (EditUserFragmentListener) context;
    }

    public interface EditUserFragmentListener {
        void submitToProfile(User user);
        void cancelEditUser();
    }

    private User createUserWithInfo(){
        String userName = binding.editTextName.getText().toString();
        String userEmail = binding.editTextEmail.getText().toString();

        if (userName.isEmpty()) {
            Toast.makeText(getActivity(), "Name is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (userEmail.isEmpty()) {
            Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_SHORT).show();
            return null;
        }
        if (selectedRole == null || selectedRole.isEmpty()) {
            Toast.makeText(getActivity(), "Role is required", Toast.LENGTH_SHORT).show();
            return null;
        }

        return new User(userName, userEmail, selectedRole);
    }
}