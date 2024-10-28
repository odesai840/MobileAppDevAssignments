package edu.uncc.assignment12;

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
import android.widget.SeekBar;
import android.widget.TextView;

import edu.uncc.assignment12.databinding.FragmentSelectDiscountBinding;


public class SelectDiscountFragment extends Fragment {
    public SelectDiscountFragment() {
        // Required empty public constructor
    }

    FragmentSelectDiscountBinding binding;
    DiscountAdapter adapter;
    String[] discountOptions = {"10%", "15%", "18%", "Custom"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSelectDiscountBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.seekBar.setMax(50);
        binding.seekBar.setProgress(25);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new DiscountAdapter(discountOptions, this::onDiscountSelected);
        binding.recyclerView.setAdapter(adapter);

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.textViewSeekBarProgress.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onCancelSelectDiscount();
            }
        });
    }

    private void onDiscountSelected(View view) {
        String discountOption = (String) view.getTag();
        double discountValue;

        if ("Custom".equals(discountOption)) {
            discountValue = binding.seekBar.getProgress();
        } else {
            discountValue = Double.parseDouble(discountOption.replace("%", ""));
        }

        mListener.onDiscountSelected(discountValue);
    }

    public class DiscountAdapter extends RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder> {
        private final String[] discountOptions;
        private final View.OnClickListener clickListener;

        public DiscountAdapter(String[] discountOptions, View.OnClickListener clickListener) {
            this.discountOptions = discountOptions;
            this.clickListener = clickListener;
        }

        @NonNull
        @Override
        public DiscountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.simple_list_item_1, parent, false);
            return new DiscountViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DiscountViewHolder holder, int position) {
            String discountOption = discountOptions[position];
            holder.discountTextView.setText(discountOption);

            holder.itemView.setTag(discountOption);
            holder.itemView.setOnClickListener(clickListener);
        }

        @Override
        public int getItemCount() {
            return discountOptions.length;
        }

        class DiscountViewHolder extends RecyclerView.ViewHolder {
            TextView discountTextView;

            public DiscountViewHolder(@NonNull View itemView) {
                super(itemView);
                discountTextView = itemView.findViewById(android.R.id.text1);
            }
        }
    }

    SelectDiscountListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof SelectDiscountListener) {
            mListener = (SelectDiscountListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement SelectDiscountListener");
        }
    }

    interface SelectDiscountListener {
        void onDiscountSelected(double discount);
        void onCancelSelectDiscount();
    }
}