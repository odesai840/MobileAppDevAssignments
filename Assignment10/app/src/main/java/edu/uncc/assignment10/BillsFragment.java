package edu.uncc.assignment10;

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
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import edu.uncc.assignment10.databinding.FragmentBillsBinding;

public class BillsFragment extends Fragment {
    public BillsFragment() {
        // Required empty public constructor
    }

    String sortAttribute = "Date", sortOrder = "ASC";

    public void setSortItems(String sortAttribute, String sortOrder) {
        this.sortAttribute = sortAttribute;
        this.sortOrder = sortOrder;
        sortBills();
    }

    FragmentBillsBinding binding;

    private ArrayList<Bill> mBills = new ArrayList<>();
    ArrayAdapter<Bill> adapter;

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

        adapter = new BillsAdapter(getActivity(), mBills);
        binding.listView.setAdapter(adapter);

        binding.textViewSortedBy.setText("Sorted By " + sortAttribute + " (" + sortOrder + ")");

        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.goToBillSummary(adapter.getItem(position));
            }
        });

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
                adapter.notifyDataSetChanged();
            }
        });

        binding.buttonSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.gotoSortSelection();
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void sortBills() {
        Comparator<Bill> comparator;

        switch (sortAttribute) {
            case "Date":
                comparator = Comparator.comparing(Bill::getBillDate);
                break;
            case "Discount":
                comparator = Comparator.comparingDouble(Bill::getDiscount);
                break;
            case "Category":
                comparator = Comparator.comparing(Bill::getCategory);
                break;
            default:
                return; // Invalid sort attribute
        }

        if (sortOrder.equals("DESC")) {
            comparator = comparator.reversed(); // Reverse the order for descending
        }

        Collections.sort(mBills, comparator); // Sort the bills list
        adapter.notifyDataSetChanged(); // Notify the adapter to refresh the list
        binding.textViewSortedBy.setText("Sorted By " + sortAttribute + " (" + sortOrder + ")");
    }

    class BillsAdapter extends ArrayAdapter<Bill> {

        public BillsAdapter(@NonNull Context context, @NonNull List<Bill> objects) {
            super(context, R.layout.bills_row_item, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.bills_row_item,parent, false);
            }

            TextView textViewBillCategory = convertView.findViewById(R.id.textViewCat);
            TextView textViewBillName = convertView.findViewById(R.id.textViewName);
            TextView textViewBillDate = convertView.findViewById(R.id.textViewDate);
            TextView textViewBillDiscount = convertView.findViewById(R.id.textViewDisc);
            TextView textViewBillAmount = convertView.findViewById(R.id.textViewAmount);
            TextView textViewBillTotal = convertView.findViewById(R.id.textViewTotal);

            Bill bill = getItem(position);
            double total = bill.amount - (bill.amount * (bill.discount / 100.0));

            textViewBillCategory.setText(bill.getCategory());
            textViewBillName.setText(bill.getName());
            textViewBillDate.setText(String.valueOf(bill.getBillDate()));
            textViewBillDiscount.setText(String.valueOf(bill.getDiscount()));
            textViewBillAmount.setText(String.valueOf(bill.getAmount()));
            textViewBillTotal.setText(String.valueOf(total));

            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mBills.clear();
        mBills.addAll(mListener.getAllBills());
        sortBills();
        adapter.notifyDataSetChanged();
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
        ArrayList<Bill> getAllBills();
        void gotoCreateBill();
        void gotoSortSelection();
        void clearAllBills();
    }

}