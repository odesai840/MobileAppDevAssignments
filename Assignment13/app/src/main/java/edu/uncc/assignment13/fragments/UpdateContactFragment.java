package edu.uncc.assignment13.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import edu.uncc.assignment13.databinding.FragmentCreateContactBinding;
import edu.uncc.assignment13.models.Contact;

public class UpdateContactFragment extends Fragment {
    private static final String ARG_PARAM_CONTACT = "ARG_PARAM_CONTACT";
    private Contact mContact;
    public UpdateContactFragment() {
        // Required empty public constructor
    }


    public static UpdateContactFragment newInstance(Contact contact) {
        UpdateContactFragment fragment = new UpdateContactFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM_CONTACT, contact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mContact = (Contact) getArguments().getSerializable(ARG_PARAM_CONTACT);
        }
    }

    public void setSelectedPhoneType(String selectedPhoneType) {
        mContact.setPhoneType(selectedPhoneType);
    }

    public void setSelectedGroup(String selectedGroup) {
        mContact.setGroup(selectedGroup);
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

        if(mContact.getPhoneType() != null) {
            binding.textViewPhoneType.setText(mContact.getPhoneType());
        } else {
            binding.textViewPhoneType.setText("N/A");
        }

        if(mContact.getGroup() != null) {
            binding.textViewGroup.setText(mContact.getGroup());
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
                mListener.cancelUpdateContact();
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

                if(mContact.getPhoneType() == null){
                    Toast.makeText(getActivity(), "Please select phone type", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(mContact.getGroup() == null){
                    Toast.makeText(getActivity(), "Please select group", Toast.LENGTH_SHORT).show();
                    return;
                }

                //update current contact and send it to the main acitivty through the listener
                // void doneUpdateContact(Contact contact);

            }
        });
    }

    UpdateContactListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof UpdateContactListener){
            mListener = (UpdateContactListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement UpdateContactListener");
        }
    }

    public interface UpdateContactListener{
        void gotoSelectPhoneType();
        void gotoSelectGroup();
        void doneUpdateContact(Contact contact);
        void cancelUpdateContact();
    }
}