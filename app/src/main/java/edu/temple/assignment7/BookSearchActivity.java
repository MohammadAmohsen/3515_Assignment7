package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class BookSearchActivity extends AppCompatActivity {

    RequestQueue requestQueue;

    BookList bookList = new BookList();
    EditText etSearch;
    Button btnCancel;
    Button btnSearch;
    String Title;
    String Author;
    String CoverURL;
    int ID;
    Book b;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                    JSONArray jsonArray = new JSONArray((String) msg.obj);
                Intent launchIntent = new Intent(BookSearchActivity.this, MainActivity.class);

                for(int i = 0; i < jsonArray.length(); i++) {
                        // Intent launchIntent = new Intent(BookSearchActivity.this, MainActivity.class);
                        //startActivity(launchIntent);
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        ID = jsonObject.getInt("id");
                        Title = jsonObject.getString("title");
                        Author = jsonObject.getString("author");
                        CoverURL = jsonObject.getString("cover_url");
                        bookList.add( new Book(Title, Author, ID, CoverURL));
                    }

                launchIntent.putExtra("Books", bookList);

                 startActivity(launchIntent);
                //b.get
                /*
               // getSupportFragmentManager()
                       // .beginTransaction()
                       // .add(R.id.textView,BookListFragment.newInstance(bookList))
                      //  .commit();

               // textView.setText((String)msg.obj);
                */


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);

        etSearch = findViewById(R.id.etSearch);
        btnCancel = findViewById(R.id.btnCancel);
        btnSearch = findViewById(R.id.btnSearch);
        //String search = etSearch.getText().toString();

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {

                            String urlString = "https://kamorris.com/lab/cis3515/search.php?term=" + etSearch.getText().toString();
                            URL url = new URL(urlString);
                            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                            Message msg = Message.obtain();
                            StringBuilder sb = new StringBuilder();
                            String tmpString;

                            while ((tmpString = br.readLine()) != null) {
                                sb.append(tmpString);
                            }
                            msg.obj = sb.toString();
                            handler.sendMessage(msg);//Message(msg);
                            // msg.obj = br.readLine();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                // setContentView(R.layout.activity_main);
                //count++;
                finish();

                // Intent launchIntent = new Intent(BookSearchActivity.this, MainActivity.class);
                //launchIntent.putExtra("Title", b.getTitle());
                // launchIntent.putExtra("Author", b.getAuthor());
                // launchIntent.putExtra("ID", b.getID());
                // launchIntent.putExtra("CoverURL", b.getCoverURL());


            }
        });
    }
}
        // String urlString = "https://kamorris.com/lab/cis3515/search.php?term=" +  etSearch.getText().toString();
                    /*
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlString, null, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response.length() > 0) {
                                //bookList.clear();
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject bookJSON;
                                        bookJSON = response.getJSONObject(i);
                                        bookList.add(new Book (bookJSON.getString("title"),
                                                bookJSON.getString("author"),
                                                bookJSON.getInt("id"),
                                                 bookJSON.getString("cover_url")));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else {
                            }
                            Intent launchIntent = new Intent(BookSearchActivity.this, MainActivity.class);
                            launchIntent.putExtra("Books", bookList);
                            startActivity(launchIntent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
                    requestQueue.add(jsonArrayRequest);
                    */





