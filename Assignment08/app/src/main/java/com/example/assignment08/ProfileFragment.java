// Assignment 08
// ProfileFragment.java
// Ohm Desai and Sullivan Crouse

package com.example.assignment08;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.assignment08.databinding.FragmentProfileBinding;
import com.google.android.material.tabs.TabLayoutMediator;

public class ProfileFragment extends Fragment {
    private static final String ARG_PARAM_USER = "ARG_PARAM_USER";

    private User mUser;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    FragmentProfileBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(ARG_PARAM_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity(), mUser);
        binding.viewPager.setAdapter(adapter);

        new TabLayoutMediator(binding.tabLayout, binding.viewPager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Contact Info");
            } else {
                tab.setText("Demographics");
            }
        }).attach();
    }

    private static class ViewPagerAdapter extends FragmentStateAdapter {
        private final User user;

        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, User user) {
            super(fragmentActivity);
            this.user = user;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            if (position == 0) {
                return ContactInfoFragment.newInstance(user);
            } else {
                return DemographicsFragment.newInstance(user);
            }
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
}