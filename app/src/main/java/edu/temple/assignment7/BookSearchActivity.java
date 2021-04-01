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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;


public class BookSearchActivity extends AppCompatActivity {

    BookList bookList = new BookList();
    Context c;
    EditText etSearch;
    Button btnCancel;
    Button btnSearch;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try{
                JSONObject jsonObject = new JSONObject((String) msg.obj);
                int ID = jsonObject.getInt("id");
                String Title = jsonObject.getString("title");
                String Author = jsonObject.getString("author");
                String CoverURL = jsonObject.getString("cover_url");
                bookList.add(new Book(Title, Author, ID, CoverURL));

                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.mainActivityID,BookListFragment.newInstance(bookList))
                        .commit();

            }
            catch (JSONException e) {
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

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String urlString = "https://kamorris.com/lab/cis3515/search.php?term=Great%20Expectations";
                            URL url = new URL(urlString);
                            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                            Message msg = Message.obtain();
                            StringBuilder sb = new StringBuilder();
                            String tmpString;

                            while((tmpString = br.readLine()) != null){
                                sb.append(tmpString);
                            }
                            msg.obj = sb.toString();
                            handler.sendMessage(msg);
                            // msg.obj = br.readLine();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                finish();
            }
        });
        //Book book = new Book();


/*
        final EditText prompt = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search");
        builder.setView(prompt);
        //String search = prompt.getText().toString();
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                 new Thread() {
                    @Override
                    public void run() {
                        try {
                            String urlString = "https://kamorris.com/lab/cis3515/search.php?term=Great%20Expectations";
                            URL url = new URL(urlString);
                            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                            Message msg = Message.obtain();
                            StringBuilder sb = new StringBuilder();
                            String tmpString;

                            while((tmpString = br.readLine()) != null){
                                sb.append(tmpString);
                            }
                            msg.obj = sb.toString();
                            handler.sendMessage(msg);
                           // msg.obj = br.readLine();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
                finish();

               // Intent launchIntent = new Intent(BookSearchActivity.this, BookListFragment.class);
               // startActivity(launchIntent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog promptDialog = builder.create();
        promptDialog.show();

        //btnSearch = findViewById(R.id.btnSearch);
        //etSearch = findViewById(R.id.etSearch);
*/
/*
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL(btnSearch.getText().toString());
                            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));

                            Message msg = Message.obtain();
                            msg.obj = br.readLine();


                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }
*/


    }

}
