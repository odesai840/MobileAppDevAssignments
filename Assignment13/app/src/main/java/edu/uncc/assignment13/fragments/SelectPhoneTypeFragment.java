package edu.uncc.assignment13.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import edu.uncc.assignment13.R;
import edu.uncc.assignment13.databinding.FragmentSelectPhoneTypeBinding;

public class SelectPhoneTypeFragment extends Fragment {
    public SelectPhoneTypeFragment() {
        // Required empty public constructor
    }

    FragmentSelectPhoneTypeBinding binding;
    String[] types = {"Home", "Cell", "Work", "Other"};
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectPhoneTypeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Phone Type");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, types);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onPhoneTypeSelected(types[position]);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelection();
            }
        });
    }

    SelectPhoneTypeListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectPhoneTypeListener) {
            mListener = (SelectPhoneTypeListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectPhoneTypeListener");
        }
    }

    public interface SelectPhoneTypeListener{
        void onPhoneTypeSelected(String phoneType);
        void cancelSelection();
    }
}