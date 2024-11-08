package edu.uncc.assignment13.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.assignment13.R;
import edu.uncc.assignment13.databinding.FragmentContactSummaryBinding;
import edu.uncc.assignment13.models.Contact;

public class ContactSummaryFragment extends Fragment {
    private static final String ARG_PARAM_CONTACT = "ARG_PARAM_CONTACT";
    private Contact mContact;

    public ContactSummaryFragment() {
        // Required empty public constructor
    }

    public static ContactSummaryFragment newInstance(Contact contact) {
        ContactSummaryFragment fragment = new ContactSummaryFragment();
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

    FragmentContactSummaryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactSummaryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contact Summary");

        binding.textViewName.setText(mContact.name);
        binding.textViewCell.setText(mContact.phone);
        binding.textViewEmail.setText(mContact.email);
        binding.textViewType.setText(mContact.phoneType);
        binding.textViewGroup.setText(mContact.group);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.doneContactSummary();
            }
        });

        binding.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteContactFromSummary(mContact);
            }
        });
    }

    ContactSummaryListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactSummaryListener) {
            mListener = (ContactSummaryListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContactSummaryListener");
        }
    }

    public interface ContactSummaryListener {
        void doneContactSummary();
        void deleteContactFromSummary(Contact contact);
    }
}