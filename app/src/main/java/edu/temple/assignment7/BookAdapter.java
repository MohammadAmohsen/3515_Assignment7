package edu.temple.assignment7;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends BaseAdapter {

    Context context;
    BookList books;

    public BookAdapter (Context context, BookList books) {
        this.context = context;
        this.books = books;
    }

    @Override
    public int getCount() {
        return books.size();
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView titleTextView, authorTextView;
        ImageView imageView;

        if (!(convertView instanceof LinearLayout)) {
            /*
            Inflate a predefined layout file that includes 2 text views.
            We could do this in code, but this seems a little easier
             */
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_book_details, parent, false);
        }

        titleTextView = convertView.findViewById(R.id.tvTitle);
        authorTextView = convertView.findViewById(R.id.tvAuthor);

        titleTextView.setText(((Book) getItem(position)).getTitle());
        authorTextView.setText(((Book) getItem(position)).getAuthor());

        return convertView;
    }
}
