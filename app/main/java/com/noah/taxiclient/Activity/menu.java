package com.noah.taxiclient.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.noah.taxiclient.R;

/**
 * Created by YH on 2017-08-18.
 */

public class menu extends AppCompatActivity {

Button main;
    Button network;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
         main = (Button) findViewById(R.id.main);
        network = (Button)findViewById(R.id.network);

        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this,Act_main.class));
            }
        });

        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(menu.this,network.class));
            }
        });

    }




}
