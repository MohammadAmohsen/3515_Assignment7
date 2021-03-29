package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BookListFragment.BookListFragmentInterface {
    BookList bookList;
    boolean bookDetailsPresent;
    Parcelable count;
    int position1;
    BookDetailsFragment bookDetailsFragment;
    Context context;
    String bookBook;

    @Override
    //Test
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookDetailsPresent = findViewById(R.id.mainActivityID2) != null;

         bookList = new BookList();

        bookList.add(new Book("Harry Potter","J.K Rowling"));
        bookList.add(new Book("The Lord of the Rings","J.R.R. Tolkien"));
        bookList.add(new Book("War and Peace","Leo Tolstoy"));
        bookList.add(new Book("Song of Solomon","Toni Morrison"));
        bookList.add(new Book("Ulysses"," James Joyce"));
        bookList.add(new Book("The Shadow of the Wind","Carlos Ruiz Zafon"));
        bookList.add(new Book("Song Of Ice and Fire","George R. Martin"));
        bookList.add( new Book("The Satanic Verses"," Salman Rushdie"));
        bookList.add(new Book("Don Quixote","Miguel de Cervantes"));
        bookList.add(new Book("The Golden Compass","Philip Pullman"));

             getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainActivityID,BookListFragment.newInstance(bookList))
                    .commit();

          if(bookDetailsPresent){
            bookDetailsFragment = new BookDetailsFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainActivityID2, bookDetailsFragment)
                    .commit();

        }

        if(savedInstanceState != null){
            String bookPosition =  String.valueOf(bookList.get(position1));
            count = savedInstanceState.getParcelable(bookBook);

        }

    }

    @Override
    public void bookClicked(int position) {
        if(!bookDetailsPresent) {
            position1 =  position;
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainActivityID, BookDetailsFragment.newInstance(bookList.get(position)))
                    .addToBackStack(null)
                    .commit();
        }
        else{
            position1 =  position;
            bookDetailsFragment.changeBook(bookList.get(position));
            bookBook = String.valueOf(bookList.get(position));
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("param1", count);
    }


}