package edu.temple.assignment7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class BookDetailsFragment extends Fragment {

    private static final String ARG_BookDetails = "param1";

    // TODO: Rename and change types of parameters
    private Book book;
    TextView tvTitle;
    TextView tvAuthor;

    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BookDetails, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BookDetails);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_book_details, container, false);
         tvTitle = view.findViewById(R.id.tvTitle);
         tvAuthor = view.findViewById(R.id.tvAuthor);

         if(book != null){
             changeBook(book);
         }

         return view;
    }

    private void changeBook(Book book){
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
    }
}