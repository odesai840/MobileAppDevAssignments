package edu.uncc.assignment11;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.GeneratedAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.uncc.assignment11.databinding.FragmentGenresBinding;
import edu.uncc.assignment11.databinding.GenreRowItemBinding;

public class GenresFragment extends Fragment {
    public GenresFragment() {
        // Required empty public constructor
    }

    FragmentGenresBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGenresBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    ArrayList<String> mGenres = Data.getAllGenres();
    GenreAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Genres");

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new GenreAdapter();
        binding.recyclerView.setAdapter(adapter);
    }

    GenresListener mListener;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof GenresListener) {
            mListener = (GenresListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement GenresListener");
        }
    }

    class GenreAdapter extends  RecyclerView.Adapter<GenreAdapter.GenreViewHolder>{


        @NonNull
        @Override
        public GenreAdapter.GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            GenreRowItemBinding binding = GenreRowItemBinding.inflate(getLayoutInflater(),parent, false);
            return new GenreViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull GenreAdapter.GenreViewHolder holder, int position) {
            String genre = mGenres.get(position);
            holder.setupUI(genre);
        }

        @Override
        public int getItemCount() {
            return mGenres.size();
        }

        class GenreViewHolder extends RecyclerView.ViewHolder {
            GenreRowItemBinding itemBinding;
            String mGenre;

            public GenreViewHolder(GenreRowItemBinding binding) {
                super(binding.getRoot());
                itemBinding = binding;
            }

            public void setupUI(String genre) {
                itemBinding.genre.setText(genre);

                mGenre = genre;

                itemBinding.genre.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.gotoBooksForGenre(mGenre);
                 }
             });
            }

         }
    }

    interface GenresListener {
        void gotoBooksForGenre(String genre);
    }
}