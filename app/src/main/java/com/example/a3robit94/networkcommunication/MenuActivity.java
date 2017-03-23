package com.example.a3robit94.networkcommunication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button downloadButton = (Button) findViewById(R.id.btnDownload);
        downloadButton.setOnClickListener(this);

        Button addButton = (Button) findViewById(R.id.btnAdd);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnDownload){
            startActivity(new Intent(this, MainActivity.class));
        }
        else if(view.getId() == R.id.btnAdd){
            startActivity(new Intent(this, AddSongActivity.class));
        }
    }
}
