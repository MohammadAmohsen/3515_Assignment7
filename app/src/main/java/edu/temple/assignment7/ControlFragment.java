package edu.temple.assignment7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ControlFragment extends Fragment {

    private Book book;
    private static final String ARG_Control = "param1";
    TextView textView;
    Button btnPlay;
    Button btnPause;
    Button btnStop;
    SeekBar seekBar;

    private String mParam1;
    private String mParam2;

    public ControlFragment() {
    }


    public static ControlFragment newInstance(Book book) {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_Control, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_Control);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_details, container, false);
        textView = view.findViewById(R.id.textView);
        btnPause = view.findViewById(R.id.btnPause);
        btnStop =  view.findViewById(R.id.btnStop);
        btnPlay =  view.findViewById(R.id.btnPlay);
        seekBar = view.findViewById(R.id.seekBar2);

        if(book != null){
            changeBook(book);
        }

        return view;
    }

    void changeBook(Book book){
        textView.setText("Now Playing: " + book.getTitle());
    }
}