package edu.uncc.assignment13.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import edu.uncc.assignment13.R;
import edu.uncc.assignment13.databinding.FragmentCreateContactBinding;
import edu.uncc.assignment13.models.Contact;

public class CreateContactFragment extends Fragment {
    public CreateContactFragment() {
        // Required empty public constructor
    }

    private String selectedPhoneType, selectedGroup;

    public void setSelectedPhoneType(String selectedPhoneType) {
        this.selectedPhoneType = selectedPhoneType;
    }

    public void setSelectedGroup(String selectedGroup) {
        this.selectedGroup = selectedGroup;
    }

    FragmentCreateContactBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateContactBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Contact");

        if(selectedPhoneType != null) {
            binding.textViewPhoneType.setText(selectedPhoneType);
        } else {
            binding.textViewPhoneType.setText("N/A");
        }

        if(selectedGroup != null) {
            binding.textViewGroup.setText(selectedGroup);
        } else {
            binding.textViewGroup.setText("N/A");
        }

        binding.buttonSelectGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectGroup();
            }
        });

        binding.buttonSelectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSelectPhoneType();
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelCreateContact();
            }
        });

        binding.buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = binding.editTextName.getText().toString();
                String email = binding.editTextEmail.getText().toString();
                String phone = binding.editTextPhone.getText().toString();

                if(name.isEmpty() || email.isEmpty() || phone.isEmpty()){
                    Toast.makeText(getActivity(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedPhoneType == null){
                    Toast.makeText(getActivity(), "Please select phone type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedGroup == null){
                    Toast.makeText(getActivity(), "Please select group", Toast.LENGTH_SHORT).show();
                    return;
                }

                //create new contact and send it to the main acitivty through the listener
                // void doneCreateContact(Contact contact);

            }
        });
    }

    CreateContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof CreateContactListener){
            mListener = (CreateContactListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement CreateContactListener");
        }
    }

    public interface CreateContactListener{
        void gotoSelectPhoneType();
        void gotoSelectGroup();
        void doneCreateContact(Contact contact);
        void cancelCreateContact();
    }
}