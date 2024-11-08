// Assignment 13
// SelectGroupFragment.java
// Ohm Desai and Sullivan Crouse

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
import edu.uncc.assignment13.databinding.FragmentSelectGroupBinding;

public class SelectGroupFragment extends Fragment {
    public SelectGroupFragment() {
        // Required empty public constructor
    }

    String[] groups = {"Friends", "Family", "Work", "Other"};

    FragmentSelectGroupBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectGroupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
    ArrayAdapter<String> adapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Select Group");
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, groups);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onGroupSelected(groups[position]);
            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.cancelSelection();
            }
        });
    }
    SelectGroupListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectGroupListener) {
            mListener = (SelectGroupListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectGroupListener");
        }
    }

    public interface SelectGroupListener{
        void onGroupSelected(String group);
        void cancelSelection();
    }
}