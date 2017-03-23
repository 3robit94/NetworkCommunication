package com.example.a3robit94.networkcommunication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    class MyTask extends AsyncTask<String, Void, String> {
        public String doInBackground(String... arguments) {
            String artist = arguments[0];
            String url = arguments[1];
            if (url == null || url.isEmpty() || artist == null || artist.isEmpty()) {
                return "Error: A valid artist and url are required";
            }
            HttpURLConnection conn = null;
            try {
                URL urlOBJ = new URL(url + "?artist=" + artist);
                conn = (HttpURLConnection) urlOBJ.openConnection();
                InputStream in = conn.getInputStream();
                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while ((line = br.readLine()) != null) {
                        result += line;
                    }
                    return result;
                } else {
                    return "HTTP ERROR: " + conn.getResponseCode();
                }

            } catch (IOException e) {
                return e.toString();
            } finally {
                if (conn != null)
                    conn.disconnect();
            }

        }

        public void onPostExecute(String result) {
            TextView tv = (TextView) findViewById(R.id.tv1);
            tv.setText(result);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button go = (Button) findViewById(R.id.btn1);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        EditText et = (EditText) findViewById(R.id.et1);
        String artist = et.getText().toString();
        EditText et2 = (EditText) findViewById(R.id.et2);
        String url = et2.getText().toString();

        MyTask myTask = new MyTask();
        myTask.execute(artist, url);
    }
}
