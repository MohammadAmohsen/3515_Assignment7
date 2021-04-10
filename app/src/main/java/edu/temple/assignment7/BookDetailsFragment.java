package edu.temple.assignment7;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class BookDetailsFragment extends Fragment {

    private static final String ARG_BookDetails = "book";

    // TODO: Rename and change types of parameters
    private Book book;
    TextView tvTitle;
    TextView tvAuthor;
    ImageView imageView;


    public BookDetailsFragment() {
        // Required empty public constructor
    }

    public static BookDetailsFragment newInstance(Book book) {
        BookDetailsFragment fragment = new BookDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_BookDetails, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            book = getArguments().getParcelable(ARG_BookDetails);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_book_details, container, false);
         tvTitle = view.findViewById(R.id.tvTitle);
         tvAuthor = view.findViewById(R.id.tvAuthor);
        imageView =  view.findViewById(R.id.imageView);


        if(book != null){
             changeBook(book);
         }

         return view;
    }

    void changeBook(Book book){
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
        Picasso.get().load(book.getCoverURL()).into(imageView);
      }


}