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

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

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
                URL urlOBJ = new URL(url + "?artist=" + artist + "&format=json");
                conn = (HttpURLConnection) urlOBJ.openConnection();
                InputStream in = conn.getInputStream();
                if (conn.getResponseCode() == 200) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while ((line = br.readLine()) != null)
                        result += line;
                    JSONArray jsonArr = new JSONArray(result);
                    String text = "";

                    for(int i=0; i<jsonArr.length(); i++) {
                        JSONObject curObj = jsonArr.getJSONObject(i);
                        String name = curObj.getString("name"), jartist = curObj.getString("artist");
                        int year = curObj.getInt("year"), quantity = curObj.getInt("quantity");
                        text += " Name=" + name + " Artist=" + jartist + " Year=" + year + " Quantity=" + quantity + "/n";
                    }
                    return text;
                }
                else {
                    return "HTTP ERROR: " + conn.getResponseCode();
                }

            }
            catch (IOException e) {
                return e.toString();
            }
            catch(JSONException e){
                return "Error";
            }
            finally {
                if (conn != null)
                    conn.disconnect();
            }

        }



        public void onPostExecute(String text) {
            TextView tv = (TextView) findViewById(R.id.tv1);
            tv.setText(text);
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
