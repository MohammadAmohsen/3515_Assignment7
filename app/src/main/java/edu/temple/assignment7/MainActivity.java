package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.ArrayList;

import edu.temple.audiobookplayer.AudiobookService;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface, ControlFragment.ControlFragmentInterface  {
    private static final String BOOKS_KEY = "books";
    private static final String SELECTED_BOOK_KEY = "selectedBook";
    private static final Boolean PLAYING = true;
    private static final Boolean PAUSED = false;
    private static final Boolean NEW_FRAGMENT = false;
    private static final String PLAY_STATUS = "play_status";
    private static final String SEEK_PROGRESS = "seek_progress";
    private static final String DURATION = "duration";

    BookList bookList;
    Book book;
    boolean bookDetailsPresent;
    BookDetailsFragment bookDetailsFragment;
    ControlFragment controlFragment;
    private final String KEY_SELECTED_BOOK = "selectedBook";
    Button btnSearch;
    FragmentManager fm;
    Book selectedBook;
    Handler mediaControlHandler;
    int duration;
    AudiobookService.MediaControlBinder mediaControlBinder;
    boolean connected;
    SeekBar mediaSeekBar;
    Uri bookUri;
    Intent bindIntent;
    int bookPlayingId;
    Button btnStop;
    boolean playWasClicked;


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

    Handler progressHandler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message message) {

            AudiobookService.BookProgress currBook = (AudiobookService.BookProgress) message.obj;

            if(mediaControlBinder.isPlaying()){
                //mediaSeekBar.setProgress(currBook.getProgress());
            }

            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookList = new BookList();

        //test
        if (savedInstanceState != null) {
            selectedBook = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);
            this.bookList = bookList;
            //book = savedInstanceState.getParcelableArrayList(BOOKS_KEY);
            selectedBook = savedInstanceState.getParcelable(SELECTED_BOOK_KEY);
          //  playStatus = savedInstanceState.getBoolean(PLAY_STATUS);
            duration = savedInstanceState.getInt(DURATION);
        }

        Intent audiobookPlayer = new Intent(MainActivity.this, AudiobookService.class);
       // bindService(audiobookPlayer, conn, Context.BIND_AUTO_CREATE);

        mediaControlHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {
                final AudiobookService.BookProgress bookProgress = (AudiobookService.BookProgress) msg.obj;
                mediaSeekBar.setMax(duration);
                if(mediaControlBinder.isPlaying()){
                    mediaSeekBar.setProgress(bookProgress.getProgress());
                    bookUri = bookProgress.getBookUri();
                }
                mediaSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(fromUser){
                            mediaControlBinder.seekTo(progress);

                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                return false;
            }
        });


        bookDetailsPresent = findViewById(R.id.mainActivityID2) != null;

        fm = getSupportFragmentManager();

        Fragment fragment1;
        fragment1 = fm.findFragmentById(R.id.mainActivityID);

         if (fragment1 instanceof BookDetailsFragment) {
            fm.popBackStack();
        } else if (!(fragment1 instanceof BookListFragment))
            fm.beginTransaction()
                    .add(R.id.mainActivityID, BookListFragment.newInstance(bookList))
                    .commit();

        btnSearch = findViewById(R.id.btnSearchMain);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivity(launchIntent);
            }
        });

        bookDetailsFragment = (selectedBook == null) ? new BookDetailsFragment() : BookDetailsFragment.newInstance(selectedBook);
        controlFragment = (selectedBook == null) ? new ControlFragment() : ControlFragment.newInstance(selectedBook);


        Intent intent = getIntent();
        if (bookDetailsPresent) {

            if (intent.hasExtra("Books")) {
                Bundle extras = getIntent().getExtras();
                bookList = extras.getParcelable(("Books"));
            }

            bookDetailsFragment = new BookDetailsFragment();
            controlFragment = new ControlFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainActivityID2, bookDetailsFragment)
                     .commit();

        }
        else {

            if (intent.hasExtra("Books")) {
                Bundle extras = getIntent().getExtras();
                bookList = extras.getParcelable(("Books"));
            }

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainActivityID, BookListFragment.newInstance(bookList))
                    .replace(R.id.submainActivityID, ControlFragment.newInstance(selectedBook))
                    .commit();
        }

        bindIntent = new Intent(this, AudiobookService.class);
        bindService(bindIntent, bookServiceConnection, BIND_AUTO_CREATE);

    }

    @Override
    public void bookClicked(int position) {
        //Store the selected book to use later if activity restarts
        selectedBook = bookList.get(position);

        if (bookDetailsPresent) {
            /*
            Display selected book using previously attached fragment
             */
            bookDetailsFragment.changeBook(selectedBook);
            controlFragment.bookSelected(position);
        }
        else {
            /*
            Display book using new fragment
             */
            fm.beginTransaction()
                    .replace(R.id.mainActivityID, BookDetailsFragment.newInstance(selectedBook))
                    .replace(R.id.submainActivityID, ControlFragment.newInstance(selectedBook))
                    // Transaction is reversible
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SELECTED_BOOK, selectedBook);

    }

    @Override
    public void onBackPressed() {
        // If the user hits the back button, clear the selected book
        selectedBook = null;
        super.onBackPressed();
    }

    @Override
    public void playButtonClicked(int id) {
        if(connected){
            //if((mediaControlBinder.isPlaying() && bookPlayingId != id) || (!mediaControlBinder.isPlaying() && bookPlayingId != id)){
                //controlFragment.btnStop.callOnClick();
                startService(bindIntent);
                mediaControlBinder.setProgressHandler(progressHandler);
                mediaControlBinder.play(id);
                playWasClicked = true;
           // }
            /*
            else{
                 bookPlayingId = id;
                startService(bindIntent);
                playWasClicked = true;
                if(mediaSeekBar.getProgress() == 0){
                    mediaControlBinder.setProgressHandler(progressHandler);
                    mediaControlBinder.play(id);
                }
                /*
                else{
                    mediaControlBinder.pause();
                }

            }
                 */


        }
        /*
        if(selectedBook != null){
            controlFragment.changeBook(selectedBook);
        }

         */
    }

    @Override
    public void pauseButtonClicked(int id) {

    }

    @Override
    public void stopButtonClicked(int id) {

    }
}






