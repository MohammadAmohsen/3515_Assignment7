package edu.temple.assignment7;

import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class BookList extends ArrayList<Parcelable> {
    private ArrayList<Book> BookList;

    public BookList(){
        BookList = new ArrayList<Book>();
    }

    public void add(Book book)
    {
        BookList.add(book);
    }

    public void remove(Book book)
    {
        BookList.remove(book);
    }

    public Book get(int index)
    {
        return BookList.get(index);
    }

    public int size()
    {
        return BookList.size();
    }

    @NonNull
    @Override
    public Stream<Parcelable> stream() {
        return null;
    }
}
