package edu.temple.assignment7;

import android.app.Notification;
import android.content.ComponentName;
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
    AudiobookService.MediaControlBinder mediaControlBinder;
    Handler mediaControlHandler;
    BookList bookList;
     BookDetailsFragment bookDetailsFragment;
    Book selectedBook;
    int duration;
    private static final Boolean PAUSED = false;

    public ControlFragment() {
    }


    ServiceConnection bookServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mediaControlBinder = (AudiobookService.MediaControlBinder) service;
            mediaControlBinder.setProgressHandler(mediaControlHandler);
            connected = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            connected = false;
        }
    };

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
                    if(mediaControlBinder.isPlaying()){
                        mediaControlBinder.pause();
                        //updatePlayStatus(bookDetailsFragment, PAUSED);
                    }
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    if(mediaControlBinder.isPlaying()){
                        mediaControlBinder.stop();
                            //updatePlayStatus(bookDetailsFragment, PAUSED);
                        }
                }
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(connected){
                    if(!mediaControlBinder.isPlaying()){
                        mediaControlBinder.play(2);
                        //updatePlayStatus(bookDetailsFragment, PAUSED);
                    }
                }
            }
        });

        if(book != null){
            changeBook(book);
        }

        return view;
    }

    void changeBook(Book book){
        textView.setText("Now Playing: " + book.getTitle());
    }

    public void updatePlayStatus(BookDetailsFragment detailsFragment, boolean playing) {
        if(playing) {
            detailsFragment.tvTitle.setText("Now playing");
            detailsFragment.tvTitle.append(selectedBook.getTitle());
        }
        else {

        }
    }

    public void bookSelected(int index) {
        selectedBook = bookList.get(index);
        duration = selectedBook.getDuration();

            bookDetailsFragment.changeBook(selectedBook);

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


    private void playButtonClicked (int id){

    }

    private void pauseButtonClicked (int id){

    }

    private void stopButtonClicked (int id){

    }
}