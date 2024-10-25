package edu.uncc.assignment11;

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

import edu.uncc.assignment11.databinding.BookRowItemBinding;
import edu.uncc.assignment11.databinding.FragmentBooksBinding;

public class BooksFragment extends Fragment {
    private static final String ARG_PARAM_GENRE = "ARG_PARAM_GENRE";
    private String mGenre;

    public static BooksFragment newInstance(String genre) {
        BooksFragment fragment = new BooksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_GENRE, genre);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mGenre = getArguments().getString(ARG_PARAM_GENRE);
        }
    }

    ArrayList<Book> mBooks = new ArrayList<>();

    public BooksFragment() {
        // Required empty public constructor
    }

    FragmentBooksBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    BooksAdapter adapter;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Books");
        mBooks.clear();
        mBooks.addAll(Data.getBooksByGenre(mGenre));
        binding.Genre.setText(mGenre);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BooksAdapter();
        binding.recyclerView.setAdapter(adapter);

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeBooks();
            }
        });

    }

    BooksListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof BooksListener) {
            mListener = (BooksListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement BooksListener");
        }
    }

    class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder>{

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            BookRowItemBinding binding = BookRowItemBinding.inflate(getLayoutInflater(), parent, false);
            return new BookViewHolder(binding);
        }

        @Override
        public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
            Book book = mBooks.get(position);
            holder.setupUI(book);
        }

        @Override
        public int getItemCount() {
            return mBooks.size();
        }

        class BookViewHolder extends RecyclerView.ViewHolder{
            BookRowItemBinding mBinding;
            Book mBook;
            public BookViewHolder(BookRowItemBinding binding){
                super(binding.getRoot());
                mBinding = binding;
            }

            public void setupUI(Book book){
                mBinding.bookTitle.setText(book.getTitle());
                mBinding.authorName.setText(book.getAuthor());
                mBinding.bookGenre.setText(book.getGenre());
                mBinding.bookYear.setText(String.valueOf(book.getYear()));

                mBook = book;

                mBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.gotoBookDetails(mBook);
                    }
                });
            }
        }
    }

    interface BooksListener{
        void closeBooks();
        void gotoBookDetails(Book book);
    }
}