package edu.temple.assignment7;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class BookList implements Parcelable {
    private ArrayList<Book> books;

    public BookList() {
        books = new ArrayList<>();
    }

    protected BookList(Parcel in) {
        books = in.createTypedArrayList(Book.CREATOR);
    }

    public static final Parcelable.Creator<BookList> CREATOR = new Parcelable.Creator<BookList>() {
        @Override
        public BookList createFromParcel(Parcel in) {
            return new BookList(in);
        }

        @Override
        public BookList[] newArray(int size) {
            return new BookList[size];
        }
    };

    public void add(Book book) {
        books.add(book);
    }

    public Book get(int position) {
        return books.get(position);
    }

    public void remove(Book book){
        books.remove(book);
    }

    public int size() {
        return books.size();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(books);
    }
}

