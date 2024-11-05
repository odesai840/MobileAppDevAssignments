package edu.uncc.assignment13.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.assignment13.R;
import edu.uncc.assignment13.databinding.ContactRowItemBinding;
import edu.uncc.assignment13.databinding.FragmentContactsBinding;
import edu.uncc.assignment13.models.Contact;

public class ContactsFragment extends Fragment {
    public ContactsFragment() {
        // Required empty public constructor
    }

    FragmentContactsBinding binding;
    ArrayList<Contact> mContacts = new ArrayList<>();
    ContactsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contacts");
        mContacts.clear();
        mContacts.addAll(mListener.getAllContacts());
        adapter = new ContactsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        binding.buttonAddNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateContact();
            }
        });

        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllContacts();
                mContacts.clear();
                mContacts.addAll(mListener.getAllContacts());
                adapter.notifyDataSetChanged();
            }
        });


    }

    class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{
        @NonNull
        @Override
        public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            ContactRowItemBinding itemBinding = ContactRowItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ContactsViewHolder(itemBinding);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
            holder.setupUI(mContacts.get(position));
        }

        @Override
        public int getItemCount() {
            return mContacts.size();
        }

        class ContactsViewHolder extends RecyclerView.ViewHolder{
            ContactRowItemBinding itemBinding;
            Contact mContact;
            public ContactsViewHolder(ContactRowItemBinding itemBinding) {
                super(itemBinding.getRoot());
                this.itemBinding = itemBinding;
            }

            public void setupUI(Contact contact){
                this.mContact = contact;
                itemBinding.textViewName.setText(contact.getName());
                itemBinding.textViewCell.setText(contact.getPhone() + " (" + contact.getPhoneType() + ")");
                itemBinding.textViewEmail.setText(contact.getEmail());
                itemBinding.textViewGroup.setText(contact.getGroup());

                itemBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                itemBinding.imageViewEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoEditContact(mContact);
                    }
                });

                itemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.gotoContactSummary(mContact);
                    }
                });
            }
        }
    }

    ContactsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof ContactsListener) {
            mListener = (ContactsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement ContactsListener");
        }
    }

    public interface ContactsListener{
        ArrayList<Contact> getAllContacts();
        void gotoCreateContact();
        void gotoContactSummary(Contact contact);
        void gotoEditContact(Contact contact);
        void deleteContact(Contact contact);
        void clearAllContacts();
    }
}