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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import edu.uncc.assignment12.databinding.BillsRowItemBinding;
import edu.uncc.assignment12.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    public BillsFragment() {
        // Required empty public constructor
    }

    FragmentBillsBinding binding;
    BillsAdapter adapter;

    private ArrayList<Bill> mBills = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBillsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mBills.clear();
        mBills.addAll(mListener.getAllBills());

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BillsAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.clearAllBills();
                mBills.clear();
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoCreateBill();
            }
        });
    }

    class BillsAdapter extends RecyclerView.Adapter<BillsAdapter.BillViewHolder> {

        @NonNull
        @Override
        public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BillsRowItemBinding binding = BillsRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new BillViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
            Bill bill = mBills.get(position);
            holder.setupUI(bill);
        }

        @Override
        public int getItemCount() {
            return mBills.size();
        }

        class BillViewHolder extends RecyclerView.ViewHolder{
            BillsRowItemBinding mBinding;

            public BillViewHolder(@NonNull BillsRowItemBinding binding) {
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Bill bill){
                double total = bill.amount - (bill.amount * (bill.discount / 100.0));
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

                mBinding.textViewCat.setText(bill.getCategory());
                mBinding.textViewName.setText(bill.getName());
                mBinding.textViewDate.setText(dateFormat.format(bill.getBillDate()));
                mBinding.textViewDisc.setText(String.valueOf(bill.getDiscount()));
                mBinding.textViewAmount.setText(String.valueOf(bill.getAmount()));
                mBinding.textViewTotal.setText(String.valueOf(total));

                mBinding.imageViewEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.goToEditBill(bill);
                    }
                });
                mBinding.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.removeBill(bill);
                        mBills.remove(bill);
                        adapter.notifyDataSetChanged();
                    }
                });
                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.goToBillSummary(bill);
                    }
                });
            }
        }
    }

    BillsListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BillsListener) {
            mListener = (BillsListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BillsListener");
        }
    }

    interface BillsListener {
        void goToBillSummary(Bill bill);
        void removeBill(Bill bill);
        void goToEditBill(Bill bill);
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void clearAllBills();
    }
}