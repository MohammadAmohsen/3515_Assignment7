package edu.temple.assignment7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class BookListFragment extends Fragment {

    private BookList bookList;
    private static final String ARG_Book = "param1";


    public BookListFragment() {
        // Required empty public constructor
    }

    public static BookListFragment newInstance(BookList bookList) {
        BookListFragment fragment = new BookListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_Book, bookList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookList = (BookList) getArguments().getParcelableArrayList(ARG_Book);
         }
        else{
            bookList = new BookList();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         ListView listView = (ListView) inflater.inflate(R.layout.fragment_book_list, container, false);

         listView.setAdapter(new BookAdapter(getActivity(), android.R.layout.simple_list_item_1, bookList));

         listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 ((BookListFragmentInterface) getActivity()).bookClicked(position);
             }
         });
        return listView;
    }

    interface BookListFragmentInterface {
        public void bookClicked(int position);
    }
}