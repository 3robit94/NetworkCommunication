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

    class MyTask extends AsyncTask<String,Void,String>{
        public String doInBackground(String... arguments){
            HttpURLConnection conn = null;
            try {
                URL url = new URL("http://www.free-map.org.uk/course/mad/ws/hits.php?artist=");
                conn = (HttpURLConnection) url.openConnection();
                InputStream in = conn.getInputStream();
                if(conn.getResponseCode() == 200){
                    BufferedReader br = new BufferedReader(new InputStreamReader(in));
                    String result = "", line;
                    while((line = br.readLine()) !=null){
                        result += line;
                    }
                    return result;
                }

                else{
                    return "HTTP ERROR: " + conn.getResponseCode();
                }

            }
            catch(IOException e){
                return e.toString();
            }
            finally{
                if(conn!=null)
                    conn.disconnect();
            }

        }
        public void onPostExecute(String result){
            TextView tv = (TextView)findViewById(R.id.tv1);
            tv.setText(result);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button go = (Button)findViewById(R.id.btn1);
        go.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        MyTask t = new MyTask();
        t.execute();
    }
}
