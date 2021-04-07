package edu.temple.assignment7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {
    BookList bookList;
    boolean bookDetailsPresent;
    //Parcelable count;
    BookDetailsFragment bookDetailsFragment;
    private final String KEY_SELECTED_BOOK = "selectedBook";
    Button btnSearch;
    FragmentManager fm;
    Book selectedBook;
    int LAUNCH_SECOND_ACTIVITY = 1;
    Intent testIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookList = new BookList();

        //test
        if (savedInstanceState != null) {
            selectedBook = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);
            this.bookList = bookList;
        }

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


        Intent intent = getIntent();
        if (bookDetailsPresent) {

            if (intent.hasExtra("Books")) {
                Bundle extras = getIntent().getExtras();
                bookList = extras.getParcelable(("Books"));
            }

            bookDetailsFragment = new BookDetailsFragment();
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
                    .commit();
        }

    }

    @Override
    public void bookClicked(int position) {
        //Store the selected book to use later if activity restarts
        selectedBook = bookList.get(position);

        if (bookDetailsPresent)
            /*
            Display selected book using previously attached fragment
             */
            bookDetailsFragment.changeBook(selectedBook);
        else {
            /*
            Display book using new fragment
             */
            fm.beginTransaction()
                    .replace(R.id.mainActivityID, BookDetailsFragment.newInstance(selectedBook))
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

}






