package edu.temple.assignment7;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.MalformedURLException;
import java.net.URL;


public class BookSearchActivity extends AppCompatActivity {

    EditText etSearch;
    Button btnSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_book_list);

        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                etSearch.setText(String.valueOf(msg.obj));

                return false;
            }
        });

        final EditText prompt = new EditText(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Search");
        builder.setView(prompt);
        builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
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
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog promptDialog = builder.create();
        promptDialog.show();

        //btnSearch = findViewById(R.id.btnSearch);
        //etSearch = findViewById(R.id.etSearch);

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


    /*
    public class FireMissilesDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Hi")
                    .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // FIRE ZE MISSILES!
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            AlertDialog alert11 = builder.create();
            alert11.show();

            return builder.create();
        }
    }
*/
    }


    private String sanitizeURL(String url) {
        if (url.startsWith("http")) {
            return url;
        } else {
            etSearch.setText("https://" + url);
            return "https://" + url;
        }
    }
}
