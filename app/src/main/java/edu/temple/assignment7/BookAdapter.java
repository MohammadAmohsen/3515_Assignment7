package edu.temple.assignment7;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class BookAdapter extends ArrayAdapter {

    Context context;

    public BookAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TextView textView;
        TextView textViewAuthor;
        if(convertView == null ){
            textView = new TextView(context);
            textView.setTextSize(22);
            textView.setPadding(15,20,0,20);


            }
        else{
            textView = (TextView) convertView;
            textViewAuthor = (TextView) convertView;

        }
       String title = ((Book)(getItem(position))).getTitle();
        String author = ((Book)(getItem(position))).getAuthor();
        String BookDetails = title + "\r\n" + author;
        textView.setText(BookDetails);
        return textView;
    }
}
