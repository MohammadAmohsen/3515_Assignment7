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
        book = new Book("Song Of Ice and Fire","George R. Martin");
        book = new Book("The Lord of the Rings","J.R.R. Tolkien");
        book = new Book("War and Peace","Leo Tolstoy");
        book = new Book("Song of Solomon","Toni Morrison");
        book = new Book("Ulysses"," James Joyce");
        book = new Book("The Shadow of the Wind","Carlos Ruiz Zafon");
        book = new Book("The Satanic Verses"," Salman Rushdie");
        book = new Book("Don Quixote","Miguel de Cervantes");
        book = new Book("The Golden Compass","Philip Pullman");

        bookList.add(book);

    }
}