package edu.uncc.assignment09;

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
import android.widget.TextView;

import java.util.ArrayList;

import edu.uncc.assignment09.databinding.FragmentBooksBinding;

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
    private ArrayList<Book> bookArrayList;
    BooksAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBooksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Books");
        mBooks.clear();
        mBooks.addAll(Data.getBooksByGenre(mGenre));

        adapter = new BooksAdapter(getActivity(), mBooks);
        binding.bookListView.setAdapter(adapter);

        binding.bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Book book = bookArrayList.get(position);
                mListener.gotoBookDetails(book);
            }
        });

        binding.buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.closeBooks();
            }
        });
    }

    class BooksAdapter extends ArrayAdapter<Book>{
        public BooksAdapter(@NonNull Context context, @NonNull ArrayList<Book> objects){
            super(context, R.layout.book_row_layout, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.book_row_layout,parent, false);
            }

            TextView textViewBookName = convertView.findViewById(R.id.title_name);
            TextView textViewAuthorName = convertView.findViewById(R.id.author_name);
            TextView textViewGenreName = convertView.findViewById(R.id.genre_name);
            TextView textViewYearName = convertView.findViewById(R.id.year_name);

            Book book = getItem(position);

            textViewBookName.setText(book.getTitle());
            textViewAuthorName.setText(book.getAuthor());
            textViewGenreName.setText(book.getGenre());
            textViewYearName.setText(String.valueOf(book.getYear()));

            return convertView;
        }
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

    interface BooksListener{
        void closeBooks();
        void gotoBookDetails(Book book);
    }
}
