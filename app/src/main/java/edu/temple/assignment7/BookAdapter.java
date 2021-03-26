package edu.temple.assignment7;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
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
        LinearLayout ll;
        if(convertView == null ){
            textView = new TextView(context);
            ll = new LinearLayout(context);
            textView.setTextSize(22);
            textView.setPadding(15,20,0,20);

            textViewAuthor = new TextView(context);
            textViewAuthor.setTextSize(12);
            textView.setTextColor(Color.GRAY);
            textViewAuthor.setPadding(15,20,0,20);

            ll.setOrientation(LinearLayout.VERTICAL);

            ll.addView(textView);
            ll.addView(textViewAuthor);
        }
        else{
            ll = (LinearLayout) convertView;
            textView = (TextView) ll.getChildAt(1);
            textViewAuthor = (TextView)ll.getChildAt(1);


        }
       String title = ((Book)(getItem(position))).getTitle();
        String author = ((Book)(getItem(position))).getAuthor();
        textView.setText(title);
        textViewAuthor.setText(author);
        return ll;
    }
}
