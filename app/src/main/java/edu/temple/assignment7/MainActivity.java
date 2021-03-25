package edu.temple.assignment7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookList bookList = new BookList();


        Book book = new Book();

        book = new Book("Harry Potter","J.K Rowling");
        bookList.add(book);

        book = new Book("Song Of Ice and Fire","George R. Martin");
        bookList.add(book);

        book = new Book("The Lord of the Rings","J.R.R. Tolkien");
        bookList.add(book);

        book = new Book("War and Peace","Leo Tolstoy");
        bookList.add(book);

        book = new Book("Song of Solomon","Toni Morrison");
        bookList.add(book);

        book = new Book("Ulysses"," James Joyce");
        bookList.add(book);

        book = new Book("The Shadow of the Wind","Carlos Ruiz Zafon");
        bookList.add(book);

        book = new Book("The Satanic Verses"," Salman Rushdie");
        bookList.add(book);

        book = new Book("Don Quixote","Miguel de Cervantes");
        bookList.add(book);

        book = new Book("The Golden Compass","Philip Pullman");

        bookList.add(book);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainActivityID,BookListFragment.newInstance(bookList))
                .commit();

    }
}