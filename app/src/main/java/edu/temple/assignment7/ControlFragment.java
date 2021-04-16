package edu.temple.assignment7;

import android.app.Notification;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import edu.temple.audiobookplayer.AudiobookService;

public class ControlFragment extends Fragment implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {

    private Book book;
    private static final String ARG_Control = "param1";
    TextView textView;
    Button btnPlay;
    Button btnPause;
    Button btnStop;
    SeekBar seekBar;
    boolean connected = true;
    //AudiobookService.MediaControlBinder mediaControlBinder;
    BookList bookList;
    int duration;
    private static final Boolean PAUSED = false;
    ControlFragment controlFragment;
    ControlFragmentInterface parentActivity;


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
        View view = inflater.inflate(R.layout.fragment_control, container, false);
        textView = view.findViewById(R.id.textView);
        btnPause = view.findViewById(R.id.btnPause);
        btnStop =  view.findViewById(R.id.btnStop);
        btnPlay =  view.findViewById(R.id.btnPlay);
        seekBar = view.findViewById(R.id.seekBar2);

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    //if(mediaControlBinder.isPlaying()){
                        //updatePlayStatus(bookDetailsFragment, PAUSED);
                    //}
                    parentActivity.pauseButtonClicked(book.getID());

                    if(book != null){
                        pauseeBook(book);
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //updatePlayStatus(bookDetailsFragment, PAUSED);
                parentActivity.stopButtonClicked(book.getID());

                if(book != null){
                    stopBook(book);
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    //if(!mediaControlBinder.isPlaying()){
                        parentActivity.playButtonClicked(book.getID());
                    //updatePlayStatus(cf, true);
                    }

                //}
                if(book != null){
                    changeBook(book);
                }
            }
        });



        return view;
    }

    void changeBook(Book book){
        textView.setText("Now Playing: " + book.getTitle());
    }
    void stopBook(Book book){
        textView.setText("Now Stopped: " + book.getTitle());
    }    void pauseeBook(Book book){
        textView.setText("Now Paused: " + book.getTitle());
    }
/*
    public void updatePlayStatus(ControlFragment detailsFragment, boolean playing) {
            detailsFragment.textView.setText("Now playing");
            detailsFragment.textView.append(book.getTitle());

    }

 */



    public void bookSelected(int index) {
        book = bookList.get(index);
        duration = book.getDuration();
        controlFragment.changeBook(book);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onPrepared(MediaPlayer mp) {

    }

    interface ControlFragmentInterface{
        void playButtonClicked(int id);
        void pauseButtonClicked(int id);
        void stopButtonClicked(int id);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        /*
         This fragment needs to communicate with its parent activity
         so we verify that the activity implemented our defined interface
         */
        if (context instanceof BookListFragment.BookListFragmentInterface) {
            parentActivity = (ControlFragment.ControlFragmentInterface) context;
        } else {
            throw new RuntimeException("Please implement the required interface(s)");
        }
    }

}