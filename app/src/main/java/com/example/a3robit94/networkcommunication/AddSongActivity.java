package com.example.a3robit94.networkcommunication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddSongActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song);

        Button addSongButton =(Button) findViewById(R.id.btnAdd);
        addSongButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText songEditText = (EditText) findViewById(R.id.etSong);
        String title = songEditText.getText().toString();

        EditText artistEditText = (EditText) findViewById(R.id.etArtist);
        String artist = artistEditText.getText().toString();

        EditText yearEditText = (EditText) findViewById(R.id.etYear);
        int year = Integer.parseInt(yearEditText.getText().toString());

        Song song = new Song(title,artist,year);

        new addSongAsyncTask().execute(song);
    }

    class addSongAsyncTask extends AsyncTask<Song, Void, String>{
        @Override
        public String doInBackground(Song... songs){
            Song song = songs[0];
            if(song != null){
                HttpURLConnection conn = null;
                try{
                    URL urlObj = new URL("http://www.free-map.org.uk/course/mad/ws/addhit.php");
                    conn = (HttpURLConnection) urlObj.openConnection();

                    String postData = "song=" + song.getTitle() + "&"
                            + "artist=" + song.getArtist() + "&"
                            + "year=" + song.getYear();

                    conn.setDoOutput(true);
                    conn.setFixedLengthStreamingMode(postData.length());

                    OutputStream out = conn.getOutputStream();
                    out.write(postData.getBytes());

                    if (conn.getResponseCode() == 200) {
                        InputStream in = conn.getInputStream();
                        BufferedReader br = new BufferedReader(new InputStreamReader(in));
                        String result = "", line;
                        while ((line = br.readLine()) != null) {
                            result += line;
                        }
                        return result;
                    } else {
                        return "HTTP ERROR: " + conn.getResponseCode();
                    }
                }
                catch(IOException e){
                    return e.toString();
                }
            }
            return "Error: Something went wrong";
        }
    }
}
