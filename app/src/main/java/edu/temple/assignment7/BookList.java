package edu.temple.assignment7;

import java.util.ArrayList;
import java.util.List;

public class BookList {
    private List<Book> BookList;

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
}
