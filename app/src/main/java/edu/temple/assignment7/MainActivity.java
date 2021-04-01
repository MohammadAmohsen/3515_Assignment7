package edu.temple.assignment7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {
    BookList bookList;
    boolean bookDetailsPresent;
    Parcelable count;
    BookDetailsFragment bookDetailsFragment;
    private final String KEY_SELECTED_BOOK = "selectedBook";
    Button search;
    FragmentManager fm;
    Book selectedBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bookList = new BookList();
        if (savedInstanceState != null) {
            count = savedInstanceState.getParcelable(KEY_SELECTED_BOOK);
        }

        bookDetailsPresent = findViewById(R.id.mainActivityID2) != null;

        fm = getSupportFragmentManager();

        Fragment fragment1;
        fragment1 = fm.findFragmentById(R.id.mainActivityID);

        // At this point, I only want to have BookListFragment be displayed in container_1
        if (fragment1 instanceof BookDetailsFragment) {
            fm.popBackStack();
        } else if (!(fragment1 instanceof BookListFragment))
            fm.beginTransaction()
                    .add(R.id.mainActivityID, BookListFragment.newInstance(bookList))
                    .commit();

        search = findViewById(R.id.btnSearch);


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = new Intent(MainActivity.this, BookSearchActivity.class);
                startActivity(launchIntent);

            }
        });

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
        else{
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



    /*
    private void fetchBooks(String searchString) {
        /*
        A Volloy JSONArrayRequest will automatically convert a JSON Array response from
        a web server to an Android JSONArray object


        JSONArray jsonArrayRequest = new JSONArray(SEARCH_API + searchString, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    //book.clear();
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject bookJSON;
                            bookJSON = response.getJSONObject(i);
                            bookList.add(new Book (bookJSON.getInt(Book.JSON_ID),
                                    bookJSON.getString(Book.JSON_TITLE),
                                    bookJSON.getString(Book.JSON_AUTHOR),
                                    bookJSON.getString(Book.JSON_COVER_URL)));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    //updateBooksDisplay();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    };


     */
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






