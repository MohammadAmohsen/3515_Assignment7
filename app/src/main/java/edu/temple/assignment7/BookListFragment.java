package edu.temple.assignment7;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class BookListFragment extends Fragment {

    private BookList books;
    private static final String ARG_Book = "param1";
    BookListFragmentInterface parentActivity;


    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance(BookList books) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();

        /*
         A BookList implements the Parcelable interface
         therefore we can place a BookList inside a bundle
         by using that put() method.
         */
        args.putParcelable(ARG_Book, books);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*
         This fragment needs to communicate with its parent activity
         so we verify that the activity implemented our defined interface
         */
        if (context instanceof BookListFragmentInterface) {
            parentActivity = (BookListFragmentInterface) context;
        } else {
            throw new RuntimeException("Please implement the required interface(s)");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            books = getArguments().getParcelable(ARG_Book);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ListView listView = (ListView) inflater.inflate(R.layout.fragment_book_list, container, false);

        listView.setAdapter(new BookAdapter(getContext(), books));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parentActivity.bookClicked(position);
            }
        });

        return listView;
    }

    interface BookListFragmentInterface {
        public void bookClicked(int position);
    }
}